package GUI;
import Beings.*;
import Battle.*;

public class BrotherThread  implements Runnable{
    BattleField battleField;
    Creature brother;
    public BrotherThread(Creature brother,BattleField battleField){
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
        if(!battleField.getIsRecord()) {
            while (!battleField.getIsEnd()) {
                try {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    boolean moveres = moveForward();
                    if (moveres) {

                    }
                } catch (Exception e) {

                }
            }
        }
    }
}
