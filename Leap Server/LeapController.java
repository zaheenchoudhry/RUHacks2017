import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.Math;
import java.net.ServerSocket;
import java.net.Socket;

import com.leapmotion.leap.*;

class SampleListener extends Listener {
	private DataOutputStream output;
	
	SampleListener(DataOutputStream out) {
		this.output = out;
	}
	
    public void onInit(Controller controller) {
        System.out.println("Initialized");
    }

    public void onConnect(Controller controller) {
        System.out.println("Connected");
    }

    public void onDisconnect(Controller controller) {
        //Note: not dispatched when running in a debugger.
        System.out.println("Disconnected");
    }

    public void onExit(Controller controller) {
        System.out.println("Exited");
    }

    public void onFrame(Controller controller) {
        // Get the most recent frame and report some basic information
        Frame frame = controller.frame();            
     	Hand hand = frame.hands().get(0);       
     	
     	float posX = hand.palmPosition().get(2);
        float posY = hand.palmPosition().get(1);
        		
        System.out.printf("X: %f, Y: %f\n", posX, posY);
        try {
			output.writeUTF(String.valueOf(posX) + "," + String.valueOf(posY));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if (!frame.hands().isEmpty()) {
            System.out.println("");
        }
    }
}

class LeapController {
    public static void main(String[] args) throws IOException {
        // Create a sample listener and controller
    	
        ServerSocket sock = new ServerSocket(Integer.parseInt(args[0]));
        sock.setSoTimeout(50000);
        System.out.println("Awaiting connection...");
        Socket server = sock.accept();
        System.out.println("connected");
        DataOutputStream out = new DataOutputStream(server.getOutputStream());
    	
        SampleListener listener = new SampleListener(out);
        Controller controller = new Controller();

        // Have the sample listener receive events from the controller
        controller.addListener(listener);


        // Keep this process running until Enter is pressed
        System.out.println("Press Enter to quit...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Remove the sample listener when done
        controller.removeListener(listener);
        sock.close();
    }
}
