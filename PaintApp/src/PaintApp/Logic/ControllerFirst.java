package PaintApp.Logic;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;


public class ControllerFirst {

    //Create variables and link to FXML document
    @FXML
    private javafx.scene.text.Text Text;

    @FXML
    private Button New;

    @FXML
    private Button Exit;

    @FXML
    private ImageView Bob;


    //To exit app from main menu
    public void exitApp(){
        Platform.exit();
    }

    //Function to make a new image from start menu
    public void newImage() throws IOException {
        //Make new stage
        Stage stage = new Stage();
       //Make it not resizable
        stage.setResizable(false);
        //Set scene properties by linkning to FXML
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../GUI/Paint.fxml"))));
       //Set screen location to center
        stage.centerOnScreen();
       //Make Main menu window not interacatble while the drawing wiondow is active
        stage.initModality(Modality.APPLICATION_MODAL);
       //show winddoq
        stage.show();


        }
    }






