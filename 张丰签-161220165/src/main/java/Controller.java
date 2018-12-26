import Battle.*;
import GUI.*;
import javafx.fxml.FXML;
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
        x=new BattleField();

    }
    public boolean getEnd(){
        this.isEnd=x.getIsEnd();
        return this.isEnd;
    }
    public BorderPane getBorderPane(){
        return borderPane;
    }
    public void startGame(){

        x=new BattleField();
        x.setImage();
        x.setIsEnd(false);
        x.setIsRecord(false);
        initBorder();
        Thread[] brotherThread=new Thread[7];
        Thread[] soldierThread=new Thread[7];
        Thread grandpaThread=new Thread(new GrandpaThread(x.getCreature(8),x));
        Thread leaderThread=new Thread(new LeaderThread(x.getCreature(9),x));
        Thread snakeThread=new Thread(new SnakeThread(x.getCreature(10),x));
        for(int i=0;i<7;i++){
            BrotherThread temp=new BrotherThread(x.getCreature(i+1),x);
            brotherThread[i]=new Thread(temp);
            SoldierThread temp1=new SoldierThread(x.getCreature(i+11),x);
            soldierThread[i]=new Thread(temp1);
        }
        for(int i=0;i<7;i++){
            brotherThread[i].start();
            soldierThread[i].start();
        }
        grandpaThread.start();
        snakeThread.start();
        leaderThread.start();
    }

    public void initBorder(){
        if(!x.getIsRecord()) {
            Random rand = new Random();
            int randnum = rand.nextInt(100) % 4;
            x.getFormation().change_formation(randnum + 1);
            x.setFormation(randnum+1);
        }else{
            x.getFormation().change_formation(x.getFormationType());
        }

        x.initFormation();
        for(int i=0;i<7;i++){
            ImageView temp=x.getCreature(i+11).getImageView();
            temp.setX(60*x.getCreature(i+11).get_x());
            temp.setY(60*(x.getCreature(i+11).get_y()));
            borderPane.getChildren().add(temp);
            temp=x.getCreature(i+1).getImageView();
            temp.setX(60*x.getCreature(i+1).get_x());
            temp.setY(60*(x.getCreature(i+1).get_y()));
            borderPane.getChildren().add(temp);

        }
        ImageView temp=x.getCreature(10).getImageView();
        temp.setX(60*x.getCreature(10).get_x());
        temp.setY(60*(x.getCreature(10).get_y()));
        borderPane.getChildren().add(temp);
        temp=x.getCreature(9).getImageView();
        temp.setX(60*x.getCreature(9).get_x());
        temp.setY(60*(x.getCreature(9).get_y()));
        borderPane.getChildren().add(temp);
        temp=x.getCreature(8).getImageView();
        temp.setX(60*x.getCreature(8).get_x());
        temp.setY(60*(x.getCreature(8).get_y()));
        borderPane.getChildren().add(temp);
    }

    public void loadRecord(File recordFile) throws IOException{
        x=new BattleField();
        System.out.println("loadRecord start");
        if (recordFile == null || !recordFile.exists()) {
            System.out.println("read record fail");
            return;
        }
        System.out.println("loadInit");

        InputStreamReader reader = new InputStreamReader(
                new FileInputStream(recordFile));
        BufferedReader br = new BufferedReader(reader);
        String line="";
        line=br.readLine();
        x.setFormation(Integer.valueOf(line).intValue());
        line=br.readLine();
        while(line!=null){
            System.out.println(line);
            String[] temp=line.split("\\ ");
            try {
                int pos=x.getRecordLength();
                x.getRecord()[pos].type = Integer.valueOf(temp[0]).intValue();
                x.getRecord()[pos].src_num=Integer.valueOf(temp[1]).intValue();
                x.getRecord()[pos].src_x=Integer.valueOf(temp[2]).intValue();
                x.getRecord()[pos].src_y=Integer.valueOf(temp[3]).intValue();
                x.getRecord()[pos].dst_x=Integer.valueOf(temp[4]).intValue();
                x.getRecord()[pos].dst_y=Integer.valueOf(temp[5]).intValue();
                x.setRecordLength(pos+1);
               // System.out.println(x.records[x.recordLength].type);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
            line=br.readLine();
        }
        x.setIsRecord(true);
        x.setIsEnd(false);
        x.setImage();
        initBorder();
        Thread recordThread=new Thread(new RecordThread(x));
        recordThread.start();
    }
}
