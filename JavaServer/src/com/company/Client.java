package com.company;

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

            int len = dataInputStream.readInt();
            byte[] data = new byte[len];
            dataInputStream.readFully(data);
            dataInputStream.close();
            in.close();

            InputStream uploadedImage = new ByteArrayInputStream(data);
            BufferedImage bufferedImage = ImageIO.read(uploadedImage);


            //Create windo wto display image
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

