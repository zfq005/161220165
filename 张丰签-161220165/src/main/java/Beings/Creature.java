package Beings;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class Creature {
    protected int life=0;
    protected Boolean party=true;
    protected int number;
    protected String name;
    protected int x;
    protected int y;
    public ImageView imageView;
    public Creature(int start_x,int start_y,int num,String name){
        this.x=start_x;
        this.y=start_y;
        this.number=num;
        this.name=name;
    }
    public Creature(int num,String name){
        this.x=0;
        this.y=0;
        this.number=num;
        this.name=name;
    }
    public Creature(){
        this.x=this.y=0;
        this.number=0;
    }
    public void setImageView(){
       // System.out.println("set image view:"+number);
        switch(number){
            case 1: this.imageView = new ImageView(BeingsImage.Brother1.getImage());break;
            case 2: this.imageView = new ImageView(BeingsImage.Brother2.getImage());break;
            case 3: this.imageView = new ImageView(BeingsImage.Brother3.getImage());break;
            case 4: this.imageView = new ImageView(BeingsImage.Brother4.getImage());break;
            case 5: this.imageView = new ImageView(BeingsImage.Brother5.getImage());break;
            case 6: this.imageView = new ImageView(BeingsImage.Brother6.getImage());break;
            case 7: this.imageView = new ImageView(BeingsImage.Brother7.getImage());break;
            case 8: this.imageView = new ImageView(BeingsImage.Grandpa_one.getImage());break;
            case 9: this.imageView = new ImageView(BeingsImage.Leader_one.getImage());break;
            case 10: this.imageView = new ImageView(BeingsImage.Snake_one.getImage());break;
            default:this.imageView = new ImageView(BeingsImage.Soldier_one.getImage());break;
        }
        imageView.setFitHeight(59);
        imageView.setFitWidth(59);
    }
    public void setDieView(){
        if(this.life==0){
            switch(number){
                case 1: this.imageView.setImage(BeingsImage.BrotherDie.getImage());break;
                case 2: this.imageView.setImage(BeingsImage.BrotherDie.getImage());break;
                case 3: this.imageView.setImage(BeingsImage.BrotherDie.getImage());break;
                case 4: this.imageView.setImage(BeingsImage.BrotherDie.getImage());break;
                case 5: this.imageView.setImage(BeingsImage.BrotherDie.getImage());break;
                case 6: this.imageView.setImage(BeingsImage.BrotherDie.getImage());break;
                case 7: this.imageView.setImage(BeingsImage.BrotherDie.getImage());break;
                case 8: this.imageView.setImage(BeingsImage.GrandpaDie.getImage());break;
                case 9: this.imageView.setImage(BeingsImage.LeaderDie.getImage());break;
                case 10: this.imageView.setImage(BeingsImage.SnakeDie.getImage());break;
                default:this.imageView.setImage(BeingsImage.SoldierDie.getImage());break;
            }
            imageView.setFitHeight(59);
            imageView.setFitWidth(59);
        }
    }
    public ImageView getImageView(){
        return imageView;
    }
    public void give_life(int n){
        this.life=n;
    }
    public int get_x(){
        return this.x;
    }
    public int get_y(){
        return this.y;
    }
    public String get_name(){
        return this.name;
    }
    public int get_number(){
        return this.number;
    }
    public void show_pos(){
        System.out.println("("+x+","+y+")");
    }
    public void move(int to_x,int to_y){//生物移动
        //System.out.println("("+x+","+y+") -> ("+to_x+","+to_y+")");
        if(to_x<20&&to_y<12) {
            x = to_x;
            y = to_y;
        }
        //System.out.println(this.name+":"+this.x+","+this.y);
    }
    public int get_life(){ return life;}
    public Boolean get_party(){return party;}
}
