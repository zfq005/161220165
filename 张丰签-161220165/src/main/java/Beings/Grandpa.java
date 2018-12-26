package Beings;

public class Grandpa extends Creature {
    public Grandpa() {
        super(0,0,8,"爷爷");
        this.party=1;
    }

    public void Cheer() {
        System.out.println("Cheer! (from 爷爷)");
    }
}
