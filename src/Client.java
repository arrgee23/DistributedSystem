

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread{
	static final String serverName = "localhost";
	private static final int BASE_SOCKET = 20000;
	
	public static void putUtil(int v,int port){
		try
	      {
	         Socket client = new Socket(serverName, port);
	         
	         OutputStream outToServer = client.getOutputStream();
	         DataOutputStream out = new DataOutputStream(outToServer);
	         out.writeUTF("put"+v);
	         
	         
	         System.out.println("written(in DHT): " + v);
	         
	         out.close();
	         client.close();
	      }catch(IOException e)
	      {
	    	  System.out.println("Connection to port "+port+" was unsuccessful.Make sure the node is UP."+"\n");
	    	  //e.printStackTrace();
	      }
		
	   }

	public static int getUtil(int v,int port){
		int ret=Integer.MIN_VALUE;
		try
	      {
	        
	         Socket client = new Socket(serverName, port);
	         
	         OutputStream outToServer = client.getOutputStream();
	         DataOutputStream out = new DataOutputStream(outToServer);
	         out.writeUTF("get"+v);
	        
	         
	         InputStream inFromServer = client.getInputStream();
	         DataInputStream in = new DataInputStream(inFromServer);
	         ret = Integer.parseInt(in.readUTF());
	         
	         out.close();
	         in.close();
	         
	         
	         client.close();
	      }catch(IOException e)
	      {
	    	  System.out.println("Connection to port "+port+" was unsuccessful.Make sure the node is UP."+"\n");
	    	  //e.printStackTrace();
	    	  return -1;
	      }
		return ret;
	}
	public static void put(int v){
		putUtil(v,BASE_SOCKET+ v%4);
	}
	
	public static int get(int k){
		return getUtil(k,BASE_SOCKET+k%4);
	}
	
	
	public void run(){
		Scanner s = new Scanner(System.in);
		while(true){
			//System.out.println("looping");
			String str = s.next();
			String method ;	//= str.substring(0,3);			
			int n;
			
			try{
				method = str.substring(0,3); 
				n= Integer.parseInt(str.substring(3,str.length()));
			}
			catch(Exception e){
				System.out.println("Wrong input format."+"Usage: put/get <number>");
				continue;
			}
			

			if(method.equals("put"))
				put(n);
			else{
				int k = get(n);
				if(k!=-1)
				System.out.println("got :"+k);
				else
					System.out.println("NOT FOUND");

			}
		}
	}
	      
}
