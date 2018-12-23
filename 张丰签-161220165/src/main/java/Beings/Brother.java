package Beings;
enum Color{
    RED, ORANGE, YELLOW, GREEN, CYAN, BLUE, PURPLE
}
public class Brother extends Creature{
    private int rank;
    private Color color;
    public Brother(){
        this.x = this.y = 0;
        this.party=true;
    }
    public Brother(String name,Color color,int rank,int number) {
        this.name = name;
        this.color = color;
        this.rank = rank;
        this.number = number;
        this.x = this.y = 0;
        this.party=true;
    }
    public int get_rank(){
        return rank;
    }
    public void report_color(){
        System.out.println(color);
    }
}
