package Paint.app;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class Client implements Runnable{

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    //Constructor
    public Client(Socket socket) throws IOException {
        //Takes a socket and creats a DataInputStream
        this.socket = socket;
        in = new DataInputStream(this.socket.getInputStream());
    }

    @Override
    public void run() {
        try{

            //Get adress fo client
           String client =  socket.getRemoteSocketAddress().toString();

            System.out.println("Client connected" + client );

            DataInputStream dataInputStream = new DataInputStream(in);

            //Read from stream and store in variable len
            int len = dataInputStream.readInt();
            //Store in ByteArray
            byte[] data = new byte[len];
            //Reads data from inout stream an allocates to buffer array
            dataInputStream.readFully(data);
            //Close stream
            dataInputStream.close();
            in.close();

            //Get data as ByteArray from stream
            InputStream uploadedImage = new ByteArrayInputStream(data);
            //Store as buffered image
            BufferedImage bufferedImage = ImageIO.read(uploadedImage);

            //Create window to display image
            JFrame frame = new JFrame("Virtual Image Display" + "Image sent from" + client);

           //Create image icon to draw image from bufferedImage
            ImageIcon icon = new ImageIcon(bufferedImage);

           //Create container to display image
            JLabel container = new JLabel();


            //Set content of container to icon
            container.setIcon(icon);
            //Add container to frame
            frame.add(container);
            //adapts the window to the size of the image sent
            frame.pack();
            //Display the window and its contents
            frame.setVisible(true);
            //paint the component
            frame.repaint();



        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}

