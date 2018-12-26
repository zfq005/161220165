package Beings;
public class Snake extends Creature {
    public Snake(){
        super(0,0,10,"蛇精");
        this.party=2;
    }
    public void Cheer(){
        System.out.println("Cheer! (from 蛇精)");
    }
}
