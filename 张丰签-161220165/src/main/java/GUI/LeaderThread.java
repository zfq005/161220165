package GUI;
import Beings.*;
import Battle.*;
public class LeaderThread implements Runnable{
    BattleField battleField;
    Creature leader;
    public LeaderThread(Creature leader,BattleField battleField){
        System.out.println("Init LeaderThread:"+leader.get_name());
        this.leader=leader;
        this.battleField=battleField;
    }
    private boolean moveForward(){
        // System.out.println("move");
        if(leader.get_life()==0)
            return false;
        boolean a=this.battleField.move(leader.get_x(),leader.get_y(),leader.get_x()-1,leader.get_y());
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
