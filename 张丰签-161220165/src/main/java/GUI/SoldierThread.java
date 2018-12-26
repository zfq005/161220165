package GUI;
import Beings.*;
import Battle.*;
public class SoldierThread implements Runnable{
    BattleField battleField;
    Creature soldier;
    public SoldierThread(Creature soldier,BattleField battleField){
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

                    if (moveres) {
                    }
                } catch (Exception e) {

                }
            }
        }
    }
}
