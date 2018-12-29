import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import org.junit.Test;
import Battle.*;
import GUI.*;

import java.io.*;
import java.net.URL;

public class Test1 {
    @Test
    public void test1(){

        for(int i=0;i<4;i++) {
            BattleField x=new BattleField();
            x.getFormation().change_formation(i+1);
            x.initFormation();
            x.show_all();
        }
    }

    public void readRecord(){
        BattleField x=new BattleField();
        Controller controller=new Controller();
        try {
            controller.loadRecord(new File("record.txt"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Test
    public void writeRecord() {
        BattleField x=new BattleField();
        x.setIsEnd(true);
        try {
            x.writerecord();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
