package Beings;

public class Soldier extends Creature{
    public Soldier(int number){
        this.number=number;
        this.x=this.y=0;
        this.name="士兵";
        this.party=false;
    }
    public Soldier(int x,int y,int number){
        this.number=number;
        this.x=x;
        this.y=y;
        this.name="士兵";
        this.party=false;
    }
}
