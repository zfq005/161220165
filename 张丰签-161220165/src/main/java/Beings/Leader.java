package Beings;

public class Leader extends Creature{
    public Leader(){
        this.number=9;
        this.x=this.y=0;
        this.name="蝎精";
        this.party=false;
    }
    public Leader(int x,int y){
        this.number=9;
        this.x=x;
        this.y=y;
        this.name="蝎精";
        this.party=false;
    }
}
