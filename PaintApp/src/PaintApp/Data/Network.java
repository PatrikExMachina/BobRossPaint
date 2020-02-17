package PaintApp.Data;

import java.net.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Network{
    public static void uploader() throws IOException {

        //initilize variables, create socket and BufferedImage
        Socket mySocket;
        BufferedImage image = null;
        //Setup Socket to connect to host on port 9210
        mySocket = new Socket("localhost",9210);
        //Print a message to console to show that a connection has established
        System.out.println("Network connection established.");



        try {
            //Print uploading image
            System.out.println("Uploading Image");
            //Filepath to file to upload
            image = ImageIO.read(new File("./server.png"));
            //Make an output stream
            ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();

            //Write the loaded image to the output stream
            ImageIO.write(image, "png", outPutStream);
            //Flushes the outputstream
            outPutStream.flush();

            //Makes an array bytes from the content of the outputstream
            byte[] bytes = outPutStream.toByteArray();
            //Close the stream
            outPutStream.close();

            //Print that the image is sending
            System.out.println("Sending image to server...");

            //Send outputstream to socket
            OutputStream outputStream = mySocket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            dataOutputStream.writeInt(bytes.length);
            dataOutputStream.write(bytes, 0, bytes.length);

            //Print message
            System.out.println("Image successfully sent to server. ");

            //close the streams
            dataOutputStream.close();
            outputStream.close();
            mySocket.close();

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            mySocket.close();
        }
        mySocket.close();
    }
}