import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.scene.*;

import javax.imageio.IIOException;

public class Main extends Application {
    private Controller controller;
    @Override
    public void start(final Stage primaryStage) throws Exception {
            try {
                URL fxmlurl = getClass().getResource("sample.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(fxmlurl);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                Parent root = fxmlLoader.load();
                controller = fxmlLoader.getController();
                controller.InitBattleField();
                Scene scene = new Scene(root);
                scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent event) {
                            if (event.getCode() == KeyCode.SPACE) {
                                System.out.println("    You pressed SPACE!");
                                if (controller.isEnd) {
                                    controller.getBorderPane().getChildren().clear();
                                    controller.isStart = true;
                                    controller.isEnd = false;
                                    controller.startGame();
                                } else {
                                    System.out.println("未结束");
                                }
                                controller.getEnd();
                            } else if (event.getCode() == KeyCode.L) {
                                controller.getEnd();
                                if (controller.isEnd) {
                                    controller.getBorderPane().getChildren().clear();
                                    FileChooser fileChooser = new FileChooser();
                                    fileChooser.setTitle("Open Resource File");
                                    File recordFile = fileChooser.showOpenDialog(primaryStage);
                                    try {
                                        controller.loadRecord(recordFile);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    }
                });
                //Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
                primaryStage.setTitle("My Application");
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                primaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
