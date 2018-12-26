package Beings;
import java.net.URL;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public enum BeingsImage {
    Brother1("/Red_1.jpg"), Brother2("/Orange_2.jpg"), Brother3("/Yellow_3.jpg"),Brother4("/Green_4.jpg"),
    Brother5("/Gray_5.jpg"),Brother6("/Blue_6.jpg"),Brother7("/purple_7.jpg"),Snake_one("/snake.jpg"),
    Leader_one("/xiezijing.jpg"),Soldier_one("/xiezi.jpg"),Grandpa_one("/grandpa.jpg"),
    BrotherDie("/Brother_die.jpg"),GrandpaDie("/grandpa_die.jpg"),SoldierDie("/xiezi_die.jpg"),
    SnakeDie("/snake_die.jpg"),LeaderDie("/xiezijing_die.jpg");

    private final String imageURL;
    private final Image image;

    BeingsImage(String imageURL) {
        this.imageURL = imageURL;
        this.image = new Image(this.getClass().getResource(imageURL).toString());
    }
    public Image getImage(){
        return image;
    }
}
