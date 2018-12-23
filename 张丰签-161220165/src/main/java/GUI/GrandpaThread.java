package GUI;
import Beings.*;
import Battle.*;
import java.util.concurrent.*;
public class GrandpaThread implements Runnable{
    BattleField battleField;
    Grandpa grandpa;
    public GrandpaThread(Grandpa grandpa,BattleField battleField){
        System.out.println("Init GrandpaThread:"+grandpa.get_name());
        this.grandpa=grandpa;
        this.battleField=battleField;
    }
    private boolean moveForward(){
        // System.out.println("move");
        if(grandpa.get_life()==0)
            return false;
        boolean a=this.battleField.move(grandpa.get_x(),grandpa.get_y(),grandpa.get_x()+1,grandpa.get_y());
        return a;

    }
    public void run(){
        if(!battleField.isRecord) {
            while (!battleField.isEnd) {
                try {
                    try {
                        //battleField.set_end();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    boolean moveres = moveForward();
                    //
                    if (moveres) {
                        System.out.println("Grandpa:" + grandpa.get_name() + "move:" + grandpa.get_x() + "," + grandpa.get_y());

                        //grandpa.move(grandpa.get_x()+1,grandpa.get_y());
                    }
                } catch (Exception e) {

                }
            }
        }
    }
}
