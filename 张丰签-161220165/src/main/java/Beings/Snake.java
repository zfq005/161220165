package Beings;
public class Snake extends Creature {
    public Snake(){
        this.name="蛇精";
        this.x=this.y=0;
        this.number=10;
        this.party=false;
    }
    public Snake(int get_x,int get_y){
        this.name="蛇精";
        this.x=get_x;this.y=get_y;
        this.number=10;
        this.party=false;
    }
    public void Cheer(){
        System.out.println("Cheer! (from 蛇精)");
    }
}
