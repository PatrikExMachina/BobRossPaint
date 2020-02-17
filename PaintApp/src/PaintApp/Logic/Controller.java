package PaintApp.Logic;


import PaintApp.Data.Network;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class Controller {


    //Initialize and link variables to FMXL doc

    @FXML
    public Label Text;

    @FXML
    private Canvas canvas;

    @FXML
    private Slider Slider;

    @FXML
    private ToggleButton Drawing;

    @FXML
    private ToggleButton Eraser;

    @FXML
    private ColorPicker colorPicker;


    @FXML
    private ComboBox brushType;

    GraphicsContext draw;
    Alert alert = new Alert(Alert.AlertType.NONE);


    //Function to open a existing image
    public void openImage(){
        FileChooser openFile = new FileChooser();
        openFile.setTitle("Open Image");
        File file = openFile.showOpenDialog(null);
        if (file != null) {
            try {
                InputStream inputStream = new FileInputStream(file);
                Image image = new Image(inputStream);
                draw.drawImage(image, 0, 0);
            } catch (IOException ex) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Could not open image");
                alert.show();
                System.out.println("Error when trying to open image.Try again!");
            }
        }
    }

    //Function to save images to HD linked to save option in menu via FXML
    public void saveImage() throws IOException {
        FileChooser fileChooser= new FileChooser();
        fileChooser.setTitle("Save picture as..");
        //Specifies which formats are available to save image
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png","*.png"));
        //Opens a save window
        File file = fileChooser.showSaveDialog(null);

        //If file null
        if (file != null) {
            try {
                //Create a writeable image with width and hegight set to that of the canvas
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
               //Takes a snapsnot of the canvas and returns a rendered image, writableImage holds the rendered node
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                //Write the image to disk as png
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                //show error message if save is unsuccsesful
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Could not save image");
                alert.show();

            }

        }
    }


    //Function to send/save image to a server
    public void sendImageCloud() throws IOException {
        Image image = canvas.snapshot(null,null);
        ImageIO.write(SwingFXUtils.fromFXImage(image,null),"png",new File("server.png"));
       //Call static function uploader from Networks class
        Network.uploader();
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("Image Succesfully sent to server");
        alert.show();

    }

    public void exitApplication() {
        Platform.exit();
    }

    public void initialize() {
        draw = canvas.getGraphicsContext2D();
        colorPicker.setValue(Color.RED);

        //Set cursor to paint brush
        Image image = new Image("brush.png");  //pass in the image path
        canvas.setCursor(new ImageCursor(image));



        //What to do if mousebutton is pressed and dragged
        canvas.setOnMouseDragged(e->{
            double b = Double.parseDouble(String.valueOf(Slider.getValue()));
            double x = e.getX() -2;
            double y = e.getY() -2;

            //If drawing is selected
            if(Drawing.isSelected()) {
                //If rpund is selected in menu then do this
                if (brushType.getSelectionModel().getSelectedItem().toString().equals("Round")) {
                    draw.setFill(colorPicker.getValue());
                    draw.fillOval(x, y, b, b);

                }
                //If rectangular...
                if (brushType.getSelectionModel().getSelectedItem().toString().equals("Rectangular")) {
                    draw.setFill(colorPicker.getValue());
                    draw.fillRect(x, y, b, b);

                }

                if (brushType.getSelectionModel().getSelectedItem().toString().equals("Circle")) {
                    draw.setStroke(colorPicker.getValue());
                    draw.strokeOval(x, y, b, b);
                }

                if (brushType.getSelectionModel().getSelectedItem().toString().equals("Square")) {
                    draw.setStroke(colorPicker.getValue());
                    draw.strokeRect(x, y, b, b);
                }

                if (brushType.getSelectionModel().getSelectedItem().toString().equals("Rounded")) {
                    draw.setStroke(colorPicker.getValue());
                    draw.strokeRoundRect(x, y, b, b, 40, 50);
                }

                if (brushType.getSelectionModel().getSelectedItem().toString().equals("Feather")) {
                    draw.setFill(colorPicker.getValue());
                    draw.fillArc(x, y, b, b, 30, 70, ArcType.CHORD);
                }

                if (brushType.getSelectionModel().getSelectedItem().toString().equals("Coil")) {
                    draw.setStroke(colorPicker.getValue());
                    draw.strokeArc(x, y, b, b, 10, 90, ArcType.OPEN);
                }

                if (brushType.getSelectionModel().getSelectedItem().toString().equals("Pie")) {
                    draw.setStroke(colorPicker.getValue());
                    draw.strokeArc(x, y, b, b, 10, 90, ArcType.ROUND);
                }



            }
            else if(Eraser.isSelected()) {

                draw.clearRect(x, y, b, b);
            }

        });

        //What to do if mouse is clicked
        canvas.setOnMouseClicked(e->{
            double b = Double.parseDouble(String.valueOf(Slider.getValue()));
            double x = e.getX();
            double y = e.getY();
            double x1 = e.getX();
            double y1 = e.getY();

            if(Drawing.isSelected()) {
                //If brush = round shape
                if (brushType.getSelectionModel().getSelectedItem().toString().equals("Round")) {
                    draw.setFill(colorPicker.getValue());
                    draw.fillOval(x, y, b, b);
                }
                // = Rectangular shape
                if (brushType.getSelectionModel().getSelectedItem().toString().equals("Rectangular")) {
                    draw.setFill(colorPicker.getValue());
                    draw.fillRect(x, y, b, b);
                }

                if (brushType.getSelectionModel().getSelectedItem().toString().equals("Circle")) {
                    draw.setStroke(colorPicker.getValue());
                    draw.strokeOval(x, y, b, b);
                }

                if (brushType.getSelectionModel().getSelectedItem().toString().equals("Square")) {
                    draw.setStroke(colorPicker.getValue());
                    draw.strokeRect(x, y, b, b);
                }

                if (brushType.getSelectionModel().getSelectedItem().toString().equals("Rounded")) {
                    draw.setStroke(colorPicker.getValue());
                    draw.strokeRoundRect(x, y, b, b, 40, 50);
                }

                if (brushType.getSelectionModel().getSelectedItem().toString().equals("Feather")) {
                    draw.setFill(colorPicker.getValue());
                    draw.fillArc(x, y, b, b, 30, 70, ArcType.CHORD);
                }

                if (brushType.getSelectionModel().getSelectedItem().toString().equals("Coil")) {
                    draw.setStroke(colorPicker.getValue());
                    draw.strokeArc(x, y, b, b, 10, 90, ArcType.OPEN);
                }

                if (brushType.getSelectionModel().getSelectedItem().toString().equals("Pie")) {
                    draw.setStroke(colorPicker.getValue());
                    draw.strokeArc(x, y, b, b, 10, 90, ArcType.ROUND);
                }



            }
            else if(Eraser.isSelected()) {

                draw.clearRect(x, y, b, b);
            }
        });


    }

}
