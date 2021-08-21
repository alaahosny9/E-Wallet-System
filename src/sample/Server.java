package sample;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
public class Server {
    static ServerSocket server;
    public static void main(String[] args) throws IOException {
        new Server();
    }

    public Server(){
        int port = 4444;
         try {
             server = new ServerSocket(port);
             System.out.println("Server started... ");

                while (true){
                    Socket socket = server.accept();
                    System.out.println("IP address: " + socket.getInetAddress());
                    Thread thread = new Thread(new MultiClients(socket));
                    thread.start();
//                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
////                    Customer object = (Customer) in.readObject();
//                    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
//                    String name = inputStream.readUTF();
//                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
//                    fileWriter.write(name);
//                    fileWriter.close();
////                    outputStream.writeUTF(object.getName());
                    System.out.println("object added");
                }
            }
            catch (IOException e){
                e.getMessage();
            }
        }
    }