package assignment;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Hashtable;

public class Server extends Thread
{
	private static final int BASE_SOCKET = 10000;
    private ServerSocket serverSocket;
    Hashtable<Integer,Integer> table;
   public Server(int port) throws IOException
   {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(1000000);
      table = new Hashtable<Integer,Integer>();
   }

   public void run()
   {
      while(true)
      {
         try
         { 
            /*System.out.println("Waiting for client on port " +
            serverSocket.getLocalPort() + "...");*/

             /*System.out.println("Just connected to "
                   + server.getRemoteSocketAddress());*/
        	Socket server = serverSocket.accept();
            DataInputStream in =
                  new DataInputStream(server.getInputStream());
            String incoming = in.readUTF();
            
            String method = incoming.substring(0,3);
            int value = Integer.parseInt(incoming.substring(3,incoming.length()));
            System.out.println("Just received "+incoming);
            
            if(method.equals("put")){
            	table.put(value, value);
            	DataOutputStream out =
            	 new DataOutputStream(server.getOutputStream());
             	out.writeUTF(table.get(value).toString());
            }else if(method.equals("get")){
            	DataOutputStream out =
                new DataOutputStream(server.getOutputStream());
            	out.writeUTF(table.get(value).toString());
            }
            
         }catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e)
         {
            e.printStackTrace();
            break;
         }
      }
   }
   public static void main(String [] args)
   {
      int port = BASE_SOCKET;
      try
      {
         Thread t = new Server(port);
         t.start();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}