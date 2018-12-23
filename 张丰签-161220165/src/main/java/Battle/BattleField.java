package Battle;
import Beings.*;
import GUI.*;
import javafx.application.Platform;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class BattleField {
    private int x_size;
    private int y_size;
    int[][] battlefield;//战场
    public BrotherAct brother_act;
    public Soldier[] soldiers;
    public Snake snake;
    public Grandpa grandpa;
    public Leader leader;
    public Formation formation;
    public boolean isEnd=false;
    private boolean writeReady=false;
    public boolean isRecord=false;
    public int formationType=1;
    public BattleRecord[] records=new BattleRecord[1000];
    public int recordLength=0;

    public BattleField(){
        battlefield=new int[20][12];
        x_size=20;
        y_size=12;
        brother_act=new BrotherAct();
        soldiers=new Soldier[7];
        snake=new Snake();
        snake.give_life(1);
        grandpa=new Grandpa();
        grandpa.give_life(1);
        leader=new Leader();
        leader.give_life(1);
        for(int i = 0; i < 7; i++){
            soldiers[i] = new Soldier(i+11);
            brother_act.brother[i].give_life(1);
            soldiers[i].give_life(1);
        }
        for(int i = 0; i < 1000; i++){
            records[i] = new BattleRecord();
        }
    }
    public void show_all(){
        for(int i=0;i<x_size;i++){
            System.out.println();
            for(int j=0;j<y_size;j++) {
                if(battlefield[i][j]==0){
                    System.out.print("0     ");
                }
                else if(battlefield[i][j]<=7)
                    System.out.print(brother_act.brother[battlefield[i][j]-1].get_name()+"   ");
                else if(battlefield[i][j]<=8)
                    System.out.print(grandpa.get_name()+"   ");
                else if(battlefield[i][j]<=9)
                    System.out.print(leader.get_name()+"   ");
                else if(battlefield[i][j]<=10)
                    System.out.print(snake.get_name()+"   ");
                else if(battlefield[i][j]<=17)
                    System.out.print(soldiers[battlefield[i][j]-11].get_name()+"   ");
            }
        }
    }
    public boolean set_one(int x,int y,int num){//设置一个位置给某生物，如果成功返回true
            if (x < x_size && y < y_size) {
                if (battlefield[x][y] == 0) {
                    battlefield[x][y] = num;
                    return true;
                } else
                    return false;
            }
            else
                return false;
    }
    public boolean BrotherMove(int num,int x,int y){
        int next_life=0;
        int dst=battlefield[x][y];
        int life=brother_act.brother[num-1].get_life();
        //System.out.println(dst+"life:"+next_life);
        if(dst==9)
            next_life=leader.get_life();
        else if(dst==10)
            next_life=snake.get_life();
        else if(dst>10)
            next_life=soldiers[dst-11].get_life();
        else
            next_life=0;
        if(life>0){
            if(dst==0){
                battlefield[x][y]=num;

                records[recordLength].type=1;
                records[recordLength].src_num=num;
                records[recordLength].src_x=brother_act.brother[num-1].get_x();
                records[recordLength].src_y=brother_act.brother[num-1].get_y();
                records[recordLength].dst_x=x;
                records[recordLength].dst_y=y;
                recordLength++;

                Platform.runLater(() -> {
                    brother_act.brother[num-1].getImageView().setX(60*x);
                    brother_act.brother[num-1].getImageView().setY(60*y);
                    brother_act.brother[num-1].move(x,y);
                });

                return true;
            }
            else if(next_life==0){
                Random random=new Random();
                int randnum=random.nextInt(9)+9;
                GoodBattle(num,randnum);
                return false;
            }
            else if(dst>=9){
                GoodBattle(num,dst);
                return false;
            }
            else
                return false;

        }
        else
            return false;
    }
    public boolean GrandpaMove(int x,int y){
        int dst=battlefield[x][y];
        int life=grandpa.get_life();
        if(x>x_size/3&&life!=0) {
            Random random = new Random();
            int randnum = random.nextInt(9) + 9;
            GoodBattle(8, randnum);
            return false;
        }

        if(life>0){
            if(dst==0){
                battlefield[x][y]=8;
                records[recordLength].type=1;
                records[recordLength].src_num=8;
                records[recordLength].src_x=grandpa.get_x();
                records[recordLength].src_y=grandpa.get_y();
                records[recordLength].dst_x=x;
                records[recordLength].dst_y=y;
                recordLength++;
                grandpa.getImageView().setX(60*x);
                grandpa.getImageView().setY(60*y);
                grandpa.move(x,y);
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
    public boolean SoldierMove(int num,int x,int y){
        int next_life=0;
        int dst=battlefield[x][y];
        if(dst==8){
            next_life=grandpa.get_life();
        }
        else if(dst<=7&&dst>0){
            next_life=brother_act.brother[dst-1].get_life();
        }
        int life=soldiers[num].get_life();
        if(life>0){
            if(dst==0){
                battlefield[x][y]=num+11;

                records[recordLength].type=1;
                records[recordLength].src_num=num+11;
                records[recordLength].src_x=soldiers[num].get_x();
                records[recordLength].src_y=soldiers[num].get_y();
                records[recordLength].dst_x=x;
                records[recordLength].dst_y=y;
                recordLength++;
                soldiers[num].getImageView().setX(60*x);
                soldiers[num].getImageView().setY(60*y);
                soldiers[num].move(x,y);
                return true;
            }
            else if(next_life==0){
                Random random=new Random();
                int randnum=random.nextInt(9)+9;
                BadBattle(num,randnum);
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
             if(to_x>20)
                 isEnd=true;
             int num = battlefield[x][y];
             //System.out.println(num);
             if(num!=0) {
                 if (num <= 7) {
                     return BrotherMove(num, to_x, to_y);
                 } else if (num == 8) {
                     return GrandpaMove(to_x, to_y);
                 } else if (num >= 11) {
                     return SoldierMove(num - 11, to_x, to_y);
                 } else {
                     Random random = new Random();
                     int randnum = random.nextInt(8);
                     BadBattle(num, randnum);
                     return false;
                 }
             }
             else
                 return false;
         }
    }
    private void BadBattle(int src,int dst){
        Random rand = new Random();
        int randnum = rand.nextInt() % 2;
        int life=0;
        if(dst<=7){
            life= brother_act.brother[dst].get_life();
        }
        else{
            life=grandpa.get_life();
        }
        if(life>0) {
            if (randnum == 0) {
                if (src == 9) {
                    leader.give_life(0);
                    System.out.println(leader.get_name() + "死亡");
                    leader.setDieView();
                    records[recordLength].type=2;
                    records[recordLength].src_num=9;
                    recordLength++;
                } else if (src == 10) {
                    snake.give_life(0);
                    System.out.println(snake.get_name() + "死亡");
                    snake.setDieView();
                    records[recordLength].type=2;
                    records[recordLength].src_num=10;
                    recordLength++;
                } else {
                    soldiers[src].give_life(0);
                    System.out.println(soldiers[src].get_name() + "死亡");
                    soldiers[src].setDieView();
                    records[recordLength].type=2;
                    records[recordLength].src_num=src+11;
                    recordLength++;
                }
            } else {
                if (dst <= 7&&dst>0) {
                    brother_act.brother[dst-1].give_life(0);
                    System.out.println(brother_act.brother[dst-1].get_name() + "死亡");
                    brother_act.brother[dst-1].setDieView();
                    records[recordLength].type=2;
                    records[recordLength].src_num=dst;
                    recordLength++;

                } else {
                    grandpa.give_life(0);
                    System.out.println(grandpa.get_name() + "死亡");
                    grandpa.setDieView();
                    records[recordLength].type=2;
                    records[recordLength].src_num=8;
                    recordLength++;
                }
            }
        }
       set_end();
    }
    private void GoodBattle(int src,int dst){
        Random rand = new Random();
        int randnum = rand.nextInt(100) % 2;
        int life=0;
        if(dst==9)
            life=leader.get_life();
        else if(dst==10)
            life=snake.get_life();
        else
            life=soldiers[dst-11].get_life();
        if(life>0){
            if(randnum==0){
                if(src==8){
                    grandpa.give_life(0);
                    System.out.println(grandpa.get_name() + "死亡");
                    grandpa.setDieView();
                    records[recordLength].type=2;
                    records[recordLength].src_num=8;
                    recordLength++;
                }
                else{
                    brother_act.brother[src-1].give_life(0);
                    System.out.println(brother_act.brother[src-1].get_name() + "死亡");
                    brother_act.brother[src-1].setDieView();
                    records[recordLength].type=2;
                    records[recordLength].src_num=src;
                    recordLength++;
                }
            }
            else {
                if (dst == 9) {
                    leader.give_life(0);
                    System.out.println(leader.get_name() + "死亡");
                    leader.setDieView();
                    records[recordLength].type=2;
                    records[recordLength].src_num=9;
                    recordLength++;
                }
                else if (dst == 10) {
                    snake.give_life(0);
                    System.out.println(snake.get_name() + "死亡");
                    snake.setDieView();
                    records[recordLength].type=2;
                    records[recordLength].src_num=10;
                    recordLength++;
                 }
                 else {
                    soldiers[dst - 11].give_life(0);
                    System.out.println(soldiers[dst - 11].get_name() + "死亡");
                    soldiers[dst-11].setDieView();
                    records[recordLength].type=2;
                    records[recordLength].src_num=dst;
                    recordLength++;
                }
            }
        }
       set_end();
    }
    public void set_end(){
        synchronized (this) {
            if (snake.get_life() == 0 && leader.get_life() == 0) {
                boolean temp_flag = true;
                for (int i = 0; i < 7; i++) {
                    if (soldiers[i].get_life() > 0)
                        temp_flag = false;
                }
                if (temp_flag) {
                    isEnd = true;
                    System.out.println("end with good win");
                }
            }
            if (grandpa.get_life() == 0) {
                boolean temp_flag = true;
                for (int i = 0; i < 7; i++) {
                    if (brother_act.brother[i].get_life()>0)
                        temp_flag = false;
                }
                if (temp_flag) {
                    isEnd = true;
                    System.out.println("end with bad win");
                }
            }
            try {
                writerecord();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }
    public void tellAll(){
        for(int i=0;i<x_size;i++){
            for(int j=0;j<y_size;j++) {
                if(battlefield[i][j]==0){
                    ;
                }
                else if(battlefield[i][j]<=7)
                    brother_act.brother[battlefield[i][j]-1].move(i,j);
                else if(battlefield[i][j]<=8)
                    grandpa.move(i,j);
                else if(battlefield[i][j]<=9)
                    leader.move(i,j);
                else if(battlefield[i][j]<=10)
                    snake.move(i,j);
                else if(battlefield[i][j]<=17)
                    soldiers[battlefield[i][j]-11].move(i,j);
            }
        }
    }
    public void setImage(){
        for(int i=0;i<7;i++){
            brother_act.brother[i].setImageView();
            System.out.println("brother and soldiers index:"+i);
            soldiers[i].setImageView();
        }
        grandpa.setImageView();
        snake.setImageView();
        leader.setImageView();
    }
    public void writerecord()throws IOException {
        if(isEnd) {
            /*System.out.println("Record:");
            for (int i = 0; i < recordLength; i++) {
                System.out.println(records[i].type + " " + records[i].src_num + " ");
            }*/
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
                if (records[i].src_num <= 7) {
                    brother_act.brother[records[i].src_num-1].move(records[i].dst_x,records[i].dst_y);
                    brother_act.brother[records[i].src_num - 1].getImageView().setX(60 * records[i].dst_x);
                    brother_act.brother[records[i].src_num - 1].getImageView().setY(60 * records[i].dst_y);
                    System.out.println("Brother:" + brother_act.brother[records[i].src_num - 1].get_name() + "move:"
                            + brother_act.brother[records[i].src_num - 1].get_x() + "," + brother_act.brother[records[i].src_num - 1].get_y());
                } else if (records[i].src_num == 8) {
                    grandpa.move(records[i].dst_x,records[i].dst_y);
                    grandpa.getImageView().setX(60 * records[i].dst_x);
                    grandpa.getImageView().setY(60 * records[i].dst_y);
                    System.out.println("Grandpa:" + grandpa.get_name() + "move:" + grandpa.get_x() + "," + grandpa.get_y());
                } else if (records[i].src_num <= 10) {
                    ;
                } else {
                    soldiers[records[i].src_num-11].move(records[i].dst_x,records[i].dst_y);
                    soldiers[records[i].src_num - 11].getImageView().setX(60 * records[i].dst_x);
                    soldiers[records[i].src_num - 11].getImageView().setY(60 * records[i].dst_y);
                    System.out.println("Soldier:" + soldiers[records[i].src_num - 11].get_name() + "move:"
                            + soldiers[records[i].src_num - 11].get_x() + "," + soldiers[records[i].src_num - 11].get_y());
                }
            } else if (records[i].type == 2) {
                if (records[i].src_num <= 7) {
                    brother_act.brother[records[i].src_num - 1].imageView.setImage(BeingsImage.BrotherDie.getImage());
                    System.out.println(brother_act.brother[records[i].src_num - 1].get_name() + "死亡");
                } else if (records[i].src_num == 8) {
                    grandpa.imageView.setImage(BeingsImage.GrandpaDie.getImage());
                    System.out.println(grandpa.get_name() + "死亡");
                } else if (records[i].src_num == 9) {
                    leader.imageView.setImage(BeingsImage.LeaderDie.getImage());
                    System.out.println(leader.get_name() + "死亡");
                } else if (records[i].src_num == 10) {
                    snake.imageView.setImage(BeingsImage.SnakeDie.getImage());
                    System.out.println(snake.get_name() + "死亡");
                } else {
                    soldiers[records[i].src_num - 11].imageView.setImage(BeingsImage.SoldierDie.getImage());
                    System.out.println(soldiers[records[i].src_num - 11].get_name() + "死亡");
                }
            /*
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            */
            }
        }
    }
    public static void main(String[] args) {
       BattleField x=new BattleField();
       x.formation=new Formation(20,12,x);
       x.formation.change_formation(4);
    }

}
