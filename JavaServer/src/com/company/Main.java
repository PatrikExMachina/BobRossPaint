package com.company;

import java.net.*;
import java.io.*;
import java.awt.image.*;
import java.util.Random;

import javax.imageio.*;
import javax.swing.*;

class Main {
    public static void main(String[] args) throws Exception{
        ServerSocket server;
        Socket socket;
        server = new ServerSocket(9000 );
        System.out.println("Waiting for image");

       while(true) {
           socket = server.accept();
           Client client = new Client(socket);
           client.run();

       }


    }
}