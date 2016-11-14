import java.net.*;
import java.io.*;
import java.util.*;

public class Client
{
   private ObjectInputStream inputServer;
   private ObjectOutputStream outputClient;
   private String serverName, clientName;
   //private String message;
   private Socket client;
   private int port;

   Client(String serverName, int port, String clientName){
      this.serverName = serverName; //get IP address of server from first param
      this.port = port; //get port from second param
      this.clientName = clientName;
   }

   public void start(){
      //Connection
      try
      {
         /* Open a ClientSocket and connect to ServerSocket */
         System.out.println("Connecting to " + serverName + " on port " + port);
         //insert missing line here for creating a new socket for client and binding it to a port
         client = new Socket(serverName, port);
         
         System.out.println("Connected to " + client.getRemoteSocketAddress());

      }catch(IOException e)
      {
         //e.printStackTrace();
         System.out.println("Cannot find Server");
      }catch(ArrayIndexOutOfBoundsException e)
      {
         System.out.println("Usage: java Client <server ip> <port no.> <username>");
      }

      //setting up the input and output stream
      System.out.println("working???");

      try{

         outputClient = new ObjectOutputStream(client.getOutputStream());
         inputServer = new ObjectInputStream(client.getInputStream());

      }catch(Exception e){
         System.out.println("Error on stream.");
      }
       
      new Listener().start();

      try{
         outputClient.writeObject(clientName);
      }catch(Exception e){

      }

      System.out.print("ME: ");

      String message;
      while(true){
         Scanner scan = new Scanner(System.in);
         message = scan.nextLine();
         try{
            outputClient.writeObject(message);
         }catch(Exception e){
            System.out.println("error occured.");
            break;
         }
      }

   }

   public static void main(String [] args)
   {
      Client newCli = new Client(args[0],Integer.parseInt(args[1]),args[2]);
      newCli.start();
      try{
         newCli.outputClient.close();
         newCli.inputServer.close();
         newCli.client.close();
      }catch(Exception e){
         System.out.println("error occured.");
      }
   }

   class Listener extends Thread{
      public void run(){
         String fromServer;
         while(true){
            try{
               fromServer = (String) inputServer.readObject();
               System.out.println("\n"+fromServer);
               System.out.print("ME: ");
            }
            catch(Exception e){
               System.out.println("error occured.");
               break;
            }
         }
      }
   }
}

/**
* a) Socket client = new Socket(serverName, port);
* b) client.close();
* Reference: http://stackoverflow.com/questions/13115784/sending-a-message-to-all-clients-client-server-communication
**/