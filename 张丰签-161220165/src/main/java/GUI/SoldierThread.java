package GUI;
import Beings.*;
import Battle.*;
import java.util.concurrent.*;
public class SoldierThread implements Runnable{
    BattleField battleField;
    Soldier soldier;
    public SoldierThread(Soldier soldier,BattleField battleField){
        System.out.println("Init SoldierThread:"+soldier.get_name());
        this.soldier=soldier;
        this.battleField=battleField;
    }
    private boolean moveForward(){
        // System.out.println("move");
        if(soldier.get_life()==0)
            return false;
        boolean a=this.battleField.move(soldier.get_x(),soldier.get_y(),soldier.get_x()-1,soldier.get_y());
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

                    if (moveres) {
                        System.out.println("Soldier:" + soldier.get_name() + "move:" + soldier.get_x() + "," + soldier.get_y());
                        //soldier.move(soldier.get_x()-1,soldier.get_y());
                    }
                } catch (Exception e) {

                }
            }
        }
    }
}
