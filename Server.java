   // File Name Server.java

import java.io.*;
import java.net.*;
import java.util.*;

public class Server
{
   private ServerSocket serverSocket;
   private ArrayList<ClientThread> clientList;
   
   public Server(int port) throws IOException
   {
      //insert missing line here for binding a port to a socket
      serverSocket = new ServerSocket(port);
      clientList = new ArrayList<ClientThread>();
      //serverSocket.setSoTimeout(10000);
   }

   public void start()
   {
      boolean connected = true;
      String tempMsg;
      while(connected)
      {
         try
         {
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
           
            Socket server = serverSocket.accept();
            //System.out.println("Just connected to " + server.getRemoteSocketAddress());
            ClientThread newClient = new ClientThread(server);
            clientList.add(newClient);
            newClient.start();
            
         }catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e)
         {
            //e.printStackTrace();
            System.out.println("Usage: java Server <port no.>");
            break;
         }
      } 
   }

   class ClientThread extends Thread{
      Socket socket;
      ObjectInputStream inputClient;
      ObjectOutputStream outputServer;
      String message;
      String clientName;

      //setting up the input  and output streams 
      ClientThread(Socket socket){
         this.socket = socket;
         try{
            inputClient = new ObjectInputStream(socket.getInputStream());
            outputServer = new ObjectOutputStream(socket.getOutputStream());
            clientName = (String) inputClient.readObject();
            System.out.println(clientName + " Connected.");
         }
         catch (Exception e){
            System.out.println("error occured!");
         }
      }

      public void run(){
         while(true){
            //printing of message in the server
            try{
               message = (String) inputClient.readObject();
               System.out.println(clientName+": "+message);
            }
            catch (Exception e) {
               System.out.println("error occured.");
               break;
            }
            //printing of message for all clients.
            for(int i=0; i<clientList.size();i+=1){
               ClientThread tempClient = clientList.get(i);
               try{
                  tempClient.outputServer.writeObject(clientName+": "+message);
               }
               catch(Exception e){
                  System.out.println("error occured.");
                  break;
               }
            }
         }
      }
   }

   public static void main(String [] args)
   {
      try
      {
         int port = Integer.parseInt(args[0]);
         Server t = new Server(port);
         t.start();
      }catch(IOException e)
      {
         //e.printStackTrace();
         System.out.println("Usage: java Server <port no.>");
      }catch(ArrayIndexOutOfBoundsException e)
      {
         System.out.println("Usage: java Server <port no.> ");
      }
   }
}

/**
*a) Socket server = serverSocket.accept();
*b) serverSocket = new ServerSocket(port);
**/
