import java.net.*;
import java.io.*;
import java.util.Arrays;

public class LeapClient extends Thread {
	
	private static String serverName = "127.0.0.1";
	private static int port = 6666;
	private static Socket client;
	
	LeapClient() throws UnknownHostException, IOException {
		client = new Socket(serverName, port);	
	}
	
	public static float[] getPosition() throws IOException {
		InputStream inFromServer = client.getInputStream();
		DataInputStream in = new DataInputStream(inFromServer);
		String[] vals = (in.readUTF()).split(",");
		float[] position = new float[2];
		position[0] = Float.parseFloat(vals[0]);
		position[1] = Float.parseFloat(vals[1]);		
		
		return position;	
	}

}
