package Battle;
import Beings.*;

import javafx.application.Platform;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class BattleField {
    private int x_size;
    private int y_size;
    private int[][] battlefield;

    private Creature[] creatures;
    private Brother[] brothers;
    private Soldier[] soldiers;
    private Snake snake;
    private Grandpa grandpa;
    private Leader leader;

    private boolean isEnd=true;
    private boolean writeReady=false;
    private boolean isRecord=false;

    private Formation formation;
    private int formationType=1;

    private BattleRecord[] records=new BattleRecord[1000];
    private int recordLength=0;

    public BattleField(){
        System.out.println("\n初始化战场信息以及生物对象...");
        battlefield=new int[20][12];
        x_size=20;
        y_size=12;
        creatures=new Creature[17];
        brothers=new Brother[7];
        String[] setBrotherName={"老大","老二","老三","老四","老五","老六","老七"};
        soldiers=new Soldier[7];

        grandpa=new Grandpa();
        grandpa.setLife(1);
        creatures[7]=grandpa;

        leader=new Leader();
        leader.setLife(1);
        creatures[8]=leader;

        snake=new Snake();
        snake.setLife(1);
        creatures[9]=snake;
        for(int i = 0; i < 7; i++){
            soldiers[i] = new Soldier(i+11);
            brothers[i]=new Brother(setBrotherName[i],i+1);
            soldiers[i].setLife(1);
            brothers[i].setLife(1);
            creatures[i]=brothers[i];
            creatures[i+10]=soldiers[i];
        }

        for(int i = 0; i < 1000; i++){
            records[i] = new BattleRecord();
        }
        formation=new Formation(20,12,this);
        System.out.println("初始化完成");
    }
    public Creature getCreature(int num){
        try {
            return creatures[num - 1];
        }catch (IndexOutOfBoundsException e){
            System.out.println("数组越界:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" 下标："+num);
            return creatures[0];
        }
    }
    public Formation getFormation(){
        return formation;
    }
    public void setFormation(int num){
        formationType=num;
    }
    public int getFormationType(){
        return formationType;
    }
    public BattleRecord[] getRecord(){
        return records;
    }
    public int getRecordLength(){
        return recordLength;
    }
    public void setRecordLength(int num){
        recordLength=num;
    }
    public boolean getIsEnd(){
        return this.isEnd;
    }
    public boolean getIsRecord(){
        return this.isRecord;
    }
    public void setIsRecord(boolean value){
        this.isRecord=value;
    }
    public void setIsEnd(boolean value){
        this.isEnd=value;
    }
    public void show_all(){
        System.out.println("打印战场信息");
        for(int i=0;i<x_size;i++) {
            System.out.println();
            for (int j = 0; j < y_size; j++) {
                if (battlefield[i][j] == 0) {
                    System.out.print("0     ");
                } else {
                        System.out.print(creatures[battlefield[i][j] - 1].get_name() + "   ");
                }
            }
        }
    }
    public boolean set_one(int x,int y,int num){
            if (x < x_size && y < y_size) {
                if (battlefield[x][y] == 0) {
                    battlefield[x][y] = num;
                    System.out.println("初始化移动:");
                    creatures[num-1].move(x,y);
                    return true;
                } else
                    return false;
            }
            else
                return false;
    }
    private boolean BrotherMove(int num,int x,int y){

        int dst=battlefield[x][y];
        int life=0;
        int next_life=0;
        try {
            life = creatures[num - 1].get_life();
            if(dst>=9) {
                next_life = creatures[dst - 1].get_life();
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println("数组越界:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" 下标："+num+" "+dst);
        }


        //修改--Brother移动方式
        if(life>0){
            if(x>=x_size-4){
                int randnum = RandomAttack(num);
                if(randnum!=0) {
                    GoodBattle(num, randnum);
                }
                return false;
            }
            else if(dst==0){
                battlefield[creatures[num-1].get_x()][creatures[num-1].get_y()]=0;
                battlefield[x][y]=num;

                records[recordLength].type=1;
                records[recordLength].src_num=num;
                records[recordLength].src_x=creatures[num-1].get_x();
                records[recordLength].src_y=creatures[num-1].get_y();
                records[recordLength].dst_x=x;
                records[recordLength].dst_y=y;
                recordLength++;

                Platform.runLater(() -> {
                    creatures[num-1].getImageView().setX(60*x);
                    creatures[num-1].getImageView().setY(60*y);
                    creatures[num-1].move(x,y);
                });

                return true;
            }
            else if(dst<=7&&next_life!=0){
                System.out.println(creatures[num-1].get_name()+"等待前方队友移动");
                return false;
            }
            else if(dst==8&&next_life==0){
                return BrotherMove(num,x,y+1);
            }
            else if(next_life==0){
                if(x>=x_size/2) {
                    int randnum = RandomAttack(num);
                    if(randnum!=0) {
                        GoodBattle(num, randnum);
                    }
                }
                return false;
            }
            else{
                GoodBattle(num,dst);
                return false;
            }

        }
        else
            return false;
    }
    private boolean GrandpaMove(int x,int y){
        int dst=battlefield[x][y];
        int life=creatures[7].get_life();
        if(x>x_size/3&&life!=0) {
            Random random = new Random();
            int randnum = random.nextInt(9) + 9;
            GoodBattle(8, randnum);
            return false;
        }

        //修改--爷爷移动方式
        if(life>0){
            if(dst==0){
                battlefield[creatures[7].get_x()][creatures[7].get_y()]=0;
                battlefield[x][y]=8;
                records[recordLength].type=1;
                records[recordLength].src_num=8;
                records[recordLength].src_x=creatures[7].get_x();
                records[recordLength].src_y=creatures[7].get_y();
                records[recordLength].dst_x=x;
                records[recordLength].dst_y=y;
                recordLength++;
                Platform.runLater(() -> {
                    creatures[7].getImageView().setX(60*x);
                    creatures[7].getImageView().setY(60*y);
                    creatures[7].move(x,y);
                });
                return true;
            }
            else if(dst>=9){
                GoodBattle(8,dst);
                return false;
            }
            else
                return false;
        }
        else
            return false;
    }
    private boolean SoldierMove(int num,int x,int y){
        int dst=battlefield[x][y];
        int life=0;
        int next_life=0;
        try {
            life = creatures[num-1].get_life();
            if(dst>0&&dst<=8) {
                next_life = creatures[dst - 1].get_life();
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println("数组越界:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" 下标："+num+" "+dst);
        }


        //修改--士兵移动方式
        if(life>0){
            if(x<x_size/4){
                int randnum = RandomAttack(num);
                if(randnum!=0) {
                    BadBattle(num, randnum);
                }
                return false;
            }
            if(dst==0){
                Random random=new Random();
                int randnum=random.nextInt(100);
                if(randnum%2==0) {
                    battlefield[creatures[num - 1].get_x()][creatures[num - 1].get_y()] = 0;
                    battlefield[x][y] = num;

                    records[recordLength].type = 1;
                    records[recordLength].src_num = num;
                    records[recordLength].src_x = creatures[num - 1].get_x();
                    records[recordLength].src_y = creatures[num - 1].get_y();
                    records[recordLength].dst_x = x;
                    records[recordLength].dst_y = y;
                    recordLength++;

                    Platform.runLater(() -> {
                        creatures[num - 1].getImageView().setX(60 * x);
                        creatures[num - 1].getImageView().setY(60 * y);
                        creatures[num - 1].move(x, y);
                    });
                    return true;
                }
                else
                    return false;
            }
            else if(dst>8&&next_life!=0){
                System.out.println(creatures[num-1].get_name()+"等待前方队友移动");
                return false;
            }
            else if(next_life==0){
                int randnum=RandomAttack(num);
                if(randnum!=0) {
                    BadBattle(num, randnum);
                }
                return false;
            }
            else if(dst<9){
                BadBattle(num,dst);
                return false;
            }
            else
                return false;
        }
        else
            return false;
    }
    public boolean move(int x,int y,int to_x,int to_y){
         synchronized (this) {
             if(to_x>=20||to_y>=12)
                 isEnd=true;
             int num = battlefield[x][y];
             //System.out.println(num);
             if(num!=0) {
                 if (num <= 7) {
                     return BrotherMove(num, to_x, to_y);
                 } else if (num == 8) {
                     return GrandpaMove(to_x, to_y);
                 } else if (num >= 11) {
                     return SoldierMove(num, to_x, to_y);
                 } else {
                     Random random = new Random();
                     int randnum = random.nextInt(8);
                     BadBattle(num, randnum%8+1);
                     return false;
                 }
             }
             else
                 return false;
         }
    }
    private int RandomAttack(int num){
        Random rand = new Random();
        int randnum=0;
        if(num<=8){
            randnum=rand.nextInt(100)%9+9;
        }
        else
            randnum=rand.nextInt(100)%8+1;
        int life=0;
        try{
            life=creatures[randnum-1].get_life();
        }catch(IndexOutOfBoundsException e){
            System.out.println("数组越界:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" 下标："+randnum);
        }
        if(life!=0)
            return randnum;
        else
            return 0;
    }
    private void BadBattle(int src,int dst){
        System.out.println("战斗！ 来自"+creatures[src-1].get_name()+"对"+creatures[dst-1].get_name()+"发起的进攻");
        Random rand = new Random();
        int randnum = rand.nextInt(100) %3;
        if(src==9||src==10){
            randnum=rand.nextInt(100)%2;
        }
        int life=0;
        try {
            life=creatures[dst - 1].get_life();
        }catch (IndexOutOfBoundsException e){
            System.out.println("数组越界:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" 下标："+dst);
        }
        if(life>0) {
            if (randnum!= 0) {
                creatures[src-1].setLife(0);
                creatures[src-1].setDieView();;
                records[recordLength].type=2;
                records[recordLength].src_num=src;
            } else {
                creatures[dst-1].setLife(0);
                creatures[dst-1].setDieView();;
                records[recordLength].type=2;
                records[recordLength].src_num=dst;
            }
        }
       set_end();
    }
    private void GoodBattle(int src,int dst){
        System.out.println("战斗！ 来自"+creatures[src-1].get_name()+"对"+creatures[dst-1].get_name()+"发起的进攻");
        Random rand = new Random();
        int randnum = rand.nextInt(100) % 3;
        int life=0;
        try {
            life=creatures[dst - 1].get_life();
        }catch (IndexOutOfBoundsException e){
            System.out.println("数组越界:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" 下标："+dst);
        }
        if(life>0){
            if(randnum==0){
                creatures[src-1].setLife(0);
                creatures[src-1].setDieView();;
                records[recordLength].type=2;
                records[recordLength].src_num=src;
            }
            else {
                creatures[dst-1].setLife(0);
                creatures[dst-1].setDieView();
                records[recordLength].type=2;
                records[recordLength].src_num=dst;
            }
        }
       set_end();
    }
    private void set_end(){
        synchronized (this) {
            //System.out.println("判断");
            boolean badWin=true;
            boolean goodWin=true;
            for(int i=0;i<8;i++){
                if(creatures[i].get_life()!=0)
                    badWin=false;
            }
            for(int i=8;i<17;i++){
                if(creatures[i].get_life()!=0)
                    goodWin=false;
            }
            if(goodWin) {
                //System.out.println("Good Win");
                isEnd = true;
            }
            if(badWin){
              //  System.out.println("Bad Win");
                isEnd = true;
            }
            try {
                writerecord();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }
    public void initFormation(){
        for(int i=0;i<x_size;i++){
            for(int j=0;j<y_size;j++) {
                if(battlefield[i][j]==0){
                    ;
                }
                else {
                    try {
                        creatures[battlefield[i][j] - 1].move(i, j);
                    }catch (IndexOutOfBoundsException e){
                        System.out.println("数组越界:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" 下标："+battlefield[i][j]);
                    }
                }
            }
        }
    }
    public void setImage(){
        for(int i=0;i<17;i++){
            try {
                creatures[i].setImageView();
            }catch (IndexOutOfBoundsException e){
                System.out.println("数组越界:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" 下标："+i);
            }
        }
    }
    public void writerecord()throws IOException {
        if(isEnd) {
            if(!writeReady){
                System.out.println("write begin");
                    String file = "record.txt";
                    PrintWriter out = new PrintWriter(file);
                    out.println(formationType);
                    for (int i = 0; i < recordLength; i++) {
                        out.println(records[i].type + " " + records[i].src_num + " " + records[i].src_x + " " + records[i].src_y + " " + records[i].dst_x + " " + records[i].src_y);
                    }
                    out.close();
            }
            writeReady=true;
        }
    }
    public void RecordPlay(int i){
        if(i>=recordLength){
            isEnd=true;
            return;
        }
        else {
            if (records[i].type == 1) {
                creatures[records[i].src_num-1].move(records[i].dst_x,records[i].dst_y);
                creatures[records[i].src_num-1].getImageView().setX(60*records[i].dst_x);
                creatures[records[i].src_num-1].getImageView().setY(60*records[i].dst_y);
            } else if (records[i].type == 2) {
                if (records[i].src_num <= 7) {
                    brothers[records[i].src_num - 1].getImageView().setImage(BeingsImage.BrotherDie.getImage());
                } else if (records[i].src_num == 8) {
                    grandpa.getImageView().setImage(BeingsImage.GrandpaDie.getImage());
                } else if (records[i].src_num == 9) {
                    leader.getImageView().setImage(BeingsImage.LeaderDie.getImage());
                } else if (records[i].src_num == 10) {
                    snake.getImageView().setImage(BeingsImage.SnakeDie.getImage());
                } else {
                    soldiers[records[i].src_num - 11].getImageView().setImage(BeingsImage.SoldierDie.getImage());
                }
            }
        }
    }
    public static void main(String[] args) {
       BattleField x=new BattleField();
       /*x.formation=new Formation(20,12,x);
       x.formation.change_formation(4);*/
    }

}
