package Beings;

public class Grandpa extends Creature {
    public Grandpa() {
        this.name = "爷爷";
        this.x = this.y = 0;
        this.number = 8;
        this.party=true;
    }

    public Grandpa(int get_x, int get_y) {
        this.name = "爷爷";
        this.x = get_x;
        this.y = get_y;
        this.number = 8;
        this.party=true;
    }

    public void Cheer() {
        System.out.println("Cheer! (from 爷爷)");
    }
}
