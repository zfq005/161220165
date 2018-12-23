package GUI;
import Beings.*;
import Battle.*;
import java.util.concurrent.*;
public class SnakeThread implements Runnable{
    BattleField battleField;
    Snake snake;
    public SnakeThread(Snake snake,BattleField battleField){
        System.out.println("Init SnakeThread:"+snake.get_name());
        this.snake=snake;
        this.battleField=battleField;
    }
    private boolean moveForward(){
        // System.out.println("move");
        if(snake.get_life()==0)
            return false;
        boolean a=this.battleField.move(snake.get_x(),snake.get_y(),snake.get_x()-1,snake.get_y());
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
                        System.out.println("Snake:" + snake.get_name() + "move:" + snake.get_x() + "," + snake.get_y());

                        //snake.move(snake.get_x()-1,snake.get_y());
                    }
                } catch (Exception e) {

                }
            }
        }
    }
}
