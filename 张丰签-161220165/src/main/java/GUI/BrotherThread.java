package GUI;
import Beings.*;
import Battle.*;
import java.util.concurrent.*;

public class BrotherThread  implements Runnable{
    BattleField battleField;
    Brother brother;
    public BrotherThread(Brother brother,BattleField battleField){
        System.out.println("Init BrotherThread:"+brother.get_name());
        this.brother=brother;
        this.battleField=battleField;
    }
    private boolean moveForward(){
       // System.out.println("move");
        if(brother.get_life()==0)
            return false;
        boolean a=this.battleField.move(brother.get_x(),brother.get_y(),brother.get_x()+1,brother.get_y());
        return a;

    }
    public void run(){
        if(!battleField.isRecord) {
            while (!battleField.isEnd) {
                try {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    boolean moveres = moveForward();
                    if (moveres) {
                        System.out.println("Brother:" + brother.get_name() + "move:" + brother.get_x() + "," + brother.get_y());

                        // brother.move(brother.get_x()+1,brother.get_y());

                    }
                } catch (Exception e) {

                }
            }
        }
    }
}
