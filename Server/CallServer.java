import java.net.*;
import java.io.*;

public class CallServer {

	private UiServer gui;
	private DetailHandler dh;
	private ButtonHandler btn;

   public CallServer (int port) throws IOException {
      ServerSocket server = new ServerSocket (port);
      System.out.println("Server is running.");

		gui = new UiServer("Calling");
		dh = new DetailHandler(gui);
      btn = new ButtonHandler(this, dh);
      gui.addWindowListener(new ExitListener(this));

		gui.aBtn.addActionListener(btn);
		gui.bBtn.addActionListener(btn);
		gui.cBtn.addActionListener(btn);
		gui.dBtn.addActionListener(btn);
		gui.eBtn.addActionListener(btn);

      while (true) {
         Socket client = server.accept();
         DataInputStream in = new DataInputStream(client.getInputStream());
         String name = in.readUTF();
         System.out.println ("New client "+name+" from " +
                                    client.getInetAddress());
         CallHandler c = new CallHandler (name, client, gui,dh);
         c.start ();
      }
   }

   public static void main (String args[]) throws IOException {

      new CallServer (9999);
   }
}

