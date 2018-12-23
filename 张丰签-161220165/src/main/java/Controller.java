import Battle.*;
import Beings.*;
import GUI.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import java.io.*;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controller {
    @FXML
    private BorderPane borderPane;
    BattleField x;
    public void initialize(URL location, ResourceBundle resources){

    }
    boolean isStart=false;
    boolean isEnd=true;
    public void InitBattleField(){

        Image background=new Image(getClass().getResource("/Battle.jpg").toString());
        BackgroundSize backgroundSize=new BackgroundSize(60*20,60*10,true,true,true, false);
        borderPane.setBackground(new Background(new BackgroundImage(background,BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, backgroundSize)));

        //startGame();

    }
    public void startGame(){
        x=new BattleField();
        x.setImage();
        showBorder();
        Thread[] brotherThread=new Thread[7];
        Thread[] soldierThread=new Thread[7];
        Thread grandpaThread=new Thread(new GrandpaThread(x.grandpa,x));
        Thread leaderThread=new Thread(new LeaderThread(x.leader,x));
        Thread snakeThread=new Thread(new SnakeThread(x.snake,x));
        for(int i=0;i<7;i++){
            BrotherThread temp=new BrotherThread(x.brother_act.brother[i],x);
            brotherThread[i]=new Thread(temp);
            SoldierThread temp1=new SoldierThread(x.soldiers[i],x);
            soldierThread[i]=new Thread(temp1);
        }
        for(int i=0;i<7;i++){
            brotherThread[i].start();
            soldierThread[i].start();
        }
        grandpaThread.start();
        snakeThread.start();
        leaderThread.start();
      /*  while(isStart){
            try{
                if(x.isEnd==true){
                    isStart=false;
                }
                Thread.sleep(5000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        */
    }
    public boolean getEnd(){
        this.isEnd=x.isEnd;
        return this.isEnd;
    }
    public void showBorder(){
        x.formation=new Formation(20,12,x);
        if(!x.isRecord) {
            Random rand = new Random();
            int randnum = rand.nextInt(100) % 4;
            x.formation.change_formation(randnum + 1);
            x.formationType=randnum+1;
        }else{
            x.formation.change_formation(x.formationType);
        }

        x.tellAll();
        for(int i=0;i<7;i++){
            ImageView temp=x.soldiers[i].getImageView();
            temp.setX(60*x.soldiers[i].get_x());
            temp.setY(60*(x.soldiers[i].get_y()));
            borderPane.getChildren().add(temp);
            temp=x.brother_act.brother[i].getImageView();
            temp.setX(60*x.brother_act.brother[i].get_x());
            temp.setY(60*(x.brother_act.brother[i].get_y()));
            borderPane.getChildren().add(temp);

        }
        ImageView temp=x.snake.getImageView();
        temp.setX(60*x.snake.get_x());
        temp.setY(60*(x.snake.get_y()));
        borderPane.getChildren().add(temp);
        temp=x.leader.getImageView();
        temp.setX(60*x.leader.get_x());
        temp.setY(60*(x.leader.get_y()));
        borderPane.getChildren().add(temp);
        temp=x.grandpa.getImageView();
        temp.setX(60*x.grandpa.get_x());
        temp.setY(60*(x.grandpa.get_y()));
        borderPane.getChildren().add(temp);
    }
    public BorderPane getBorderPane(){
        return borderPane;
    }
    public void loadRecord(File recordFile) throws IOException{
        System.out.println("loadRecord start");
        if (recordFile == null || !recordFile.exists()) {
            System.out.println("read record fail");
            return;
        }
        System.out.println("loadInit");
        x=new BattleField();
        InputStreamReader reader = new InputStreamReader(
                new FileInputStream(recordFile));
        BufferedReader br = new BufferedReader(reader);
        String line="";
        line=br.readLine();
        x.formationType=Integer.valueOf(line).intValue();
        line=br.readLine();
        while(line!=null){
            System.out.println(line);
            String[] temp=line.split("\\ ");
            try {
                x.records[x.recordLength].type = Integer.valueOf(temp[0]).intValue();
                x.records[x.recordLength].src_num=Integer.valueOf(temp[1]).intValue();
                x.records[x.recordLength].src_x=Integer.valueOf(temp[2]).intValue();
                x.records[x.recordLength].src_y=Integer.valueOf(temp[3]).intValue();
                x.records[x.recordLength].dst_x=Integer.valueOf(temp[4]).intValue();
                x.records[x.recordLength].dst_y=Integer.valueOf(temp[5]).intValue();
                x.recordLength++;
               // System.out.println(x.records[x.recordLength].type);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
            line=br.readLine();
        }
        x.isRecord=true;
        x.setImage();
        showBorder();
        Thread recordThread=new Thread(new RecordThread(x));
        recordThread.start();
       // x.RecordPlay();
    }
}
