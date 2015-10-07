

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
	private static final int NO_OF_TERMINALS = 4;
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
	    	  return ret;
	      }
		return ret;
	}
	public static void put(int v){
		putUtil(v,BASE_SOCKET + Math.abs(v)%NO_OF_TERMINALS);
	}
	
	public static int get(int k){
		return getUtil(k,BASE_SOCKET + Math.abs(k)%NO_OF_TERMINALS);
	}
	
	
	public void run(){
		Scanner s = new Scanner(System.in);
		while(true){
			//System.out.println("looping");
			String str = s.nextLine();
			String method ;
			
			int n;
			
			try{
				method = str.substring(0,3); 
				n= Integer.parseInt(str.substring(4,str.length()));
			}
			catch(Exception e){
				System.out.println("Wrong input format\n"+"Usage: put/get <integer_number>");
				continue;
			}
			

			if(method.equals("put"))
				put(n);
			else if(method.equals("get")){
				int k = get(n);
				if(k!= Integer.MIN_VALUE)
					System.out.println("got: "+k);
				else
					System.out.println("NOT FOUND");

			}
			else if(method.equals("see")){
				
			}
			else{
				System.out.println("Wrong input format\n"+"Usage: put/get <integer_number>");
			}
		}
	}
	      
}
