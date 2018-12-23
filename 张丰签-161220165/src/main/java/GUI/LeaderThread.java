package GUI;
import Beings.*;
import Battle.*;
import java.util.concurrent.*;
public class LeaderThread implements Runnable{
    BattleField battleField;
    Leader leader;
    public LeaderThread(Leader leader,BattleField battleField){
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
                        System.out.println("Leader:" + leader.get_name() + "move:" + leader.get_x() + "," + leader.get_y());

                        //leader.move(leader.get_x()-1,leader.get_y());
                    }
                } catch (Exception e) {

                }
            }
        }
    }
}
