import java.net.*;
import java.io.*;

public class CallControl {

   protected CallFrame gui;
   private Socket socket;
   private DataInputStream in;
   private DataOutputStream out;
   ButtonHandler btnhandler;
	String[] msgparts;

   public CallControl(String name, String server, int port) {

      gui = new CallFrame("Chat with Sockets - "+ name);
      gui.input.addKeyListener (new EnterListener(this,gui));
      gui.addWindowListener(new ExitListener(this));
      btnhandler = new ButtonHandler(this,gui);
      for(int i = 0; i < gui.btn.length;i++)
      	gui.btn[i].addActionListener(btnhandler);

      try {
         socket = new Socket(server,port);
         in = new DataInputStream(socket.getInputStream());
         out = new DataOutputStream(socket.getOutputStream());
         out.writeUTF(name);
         while (true) {
            //gui.output.append("\n"+in.readUTF());
            checkmsg(in.readUTF());
         }
      }	catch (Exception e)	{ e.printStackTrace(); }
   }

   protected void printBill(String Dname,String Pnum,String time){
	  System.out.print(Dname+":"+Pnum+":"+time);

	}

	private void checkmsg(String str){
		msgparts =  str.split(": ");
		try {
			if(msgparts[0].equals("sys")){
				if(msgparts[1].equals("pc")){
					//Sys : pc: Tput: (details)
					//Pc: put:(Pnum)
					if(msgparts[2].equals("Tput")){
						printBill(msgparts[3],msgparts[4],msgparts[5]);
					}
				}
			}

		}
		catch (NumberFormatException nfe) {
		}
	}

   protected void sendTextToChat(String str)  {
      try {
         out.writeUTF(str);
      } catch (IOException e) { e.printStackTrace(); }
   }

   protected void disconnect() {
      try {
         socket.close();
      } catch (IOException e) { e.printStackTrace(); }
   }

   public static void main (String args[])throws IOException {

      CallControl c=new CallControl("", "localhost", 9999);
   }
}



