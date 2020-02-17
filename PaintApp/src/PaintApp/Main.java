package PaintApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("GUI/First.fxml"))));
        //primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("GUI/sample.fxml"))));
        primaryStage.setTitle("Bob Ross Paint");
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
