package GUI;
import Beings.*;
import Battle.*;
public class RecordThread implements Runnable {
    BattleField battleField;
    private int i=0;
    public RecordThread(BattleField x){
        this.battleField=x;
    }
    public void run(){
        while(!battleField.isEnd){
            try{
                battleField.RecordPlay(i);
                i++;
                Thread.sleep(250);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
