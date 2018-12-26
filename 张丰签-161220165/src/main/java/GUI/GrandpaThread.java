package GUI;
import Beings.*;
import Battle.*;
public class GrandpaThread implements Runnable{
    BattleField battleField;
    Creature grandpa;
    public GrandpaThread(Creature grandpa,BattleField battleField){
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
        if(!battleField.getIsRecord()) {
            while (!battleField.getIsEnd()) {
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

                    }
                } catch (Exception e) {

                }
            }
        }
    }
}
