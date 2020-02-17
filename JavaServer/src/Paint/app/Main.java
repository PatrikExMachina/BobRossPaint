package Paint.app;

import java.net.*;

class Main {
    public static void main(String[] args) throws Exception{
        ServerSocket server;
        Socket socket;
        //Create new ServerSocket that on port 9210
        server = new ServerSocket(9210 );
        System.out.println("Waiting for image");

       while(true) {
           //While loop that adds new clients to interact with server and run them in sperate threads
           socket = server.accept();
           Client client = new Client(socket);
           client.run();

       }


    }
}