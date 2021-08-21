package sample;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class MultiClients implements Runnable{
    Socket socket;
    File file = new File("cu.txt");
    public MultiClients(Socket s){
        socket = s;
    }

    @Override
    public void run() {

        try {
            FileWriter fileWriter = new FileWriter(file , true);

            Scanner input = new Scanner(file);

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            String name = inputStream.readUTF();
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            ArrayList<String > list = new ArrayList<>();
            fileWriter.write(" " + name + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
