import java.net.*;
import java.io.*;
import java.util.*;

public class CallHandler extends Thread {

	Random rand = new Random();
	private DetailHandler dh;
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private ObjectOutputStream  ojOut;
	private String name;
	protected static Vector handlers = new Vector( );

	private UiServer gui;
	String[] msgparts;

	public void init(){
		//broadcast("start");
	}

	public CallHandler (String name, Socket socket,UiServer gui,DetailHandler dh) throws IOException {
		this.dh = dh;
		this.name = name;
		this.socket = socket;
		this.gui = gui;

		in = new DataInputStream (
		  new BufferedInputStream(socket.getInputStream()));
		out = new DataOutputStream (
		  new BufferedOutputStream (socket.getOutputStream()));
		ojOut = new ObjectOutputStream(
		  new BufferedOutputStream (socket.getOutputStream()));
	}

   public void run() {
	  try {

		 handlers.addElement(this);
		 //broadcast(name+" entered");
		 init();

		 while (true) {

			String message = in.readUTF();
			//System.out.println(message);
             checkMsg(message);

             //broadcast()
		 }
	  } catch (IOException ex) {
		 System.out.println("-- Connection to user " + name + " lost.");
	  } finally {
		 handlers.removeElement (this);
		 broadcast(name+" left");
		 try {
			socket.close ();
		 } catch (IOException ex) {
			System.out.println("-- Socket to user already closed ?");
		 }
	  }
   }

	void checkMsg(String str){
		msgparts =  str.split(": ");
		for(int i = 0;i<msgparts.length;i++)
			System.out.println(msgparts[i]);
		String _Rmsg;
			try {

				if(msgparts[0].equals("ct")){
					if(msgparts[1].equals("put")){
						_Rmsg = dh.add(Integer.parseInt(msgparts[2]));
						//Sys : ct: Tput: (details)
						//ct: put: (P num)
						//System.out.println(_Rmsg);
						//System.out.println(dh.getLastdetail(_Rmsg)+": "+dh.getFirstdetail(_Rmsg));
						broadcast ("sys: ct: Tput: "+dh.getLastdetail(_Rmsg)+": "+dh.getFirstdetail(_Rmsg));
					}
					else if(msgparts[1].equals("get")){
						//Sys : ct : Tget : (details)
						//ct: get : (type)
						broadcast ("sys: ct: Tget: "+dh.getFirstdetail(msgparts[2]));
					}
				}
				else if(msgparts[0].equals("sf")){
					if(msgparts[1].equals("getSize")){
						//Sys : sf:Tgetsize: (int)
						//sf : getSize: (type)
						_Rmsg =""+ dh.getlistNum(msgparts[2]);
						broadcast ("sys: sf: TgSize: "+msgparts[2]+": "+_Rmsg);
					}
					else if(msgparts[1].equals("getDetail")){
						//Sys: sf: TgetDetails: (details)
						//sf : getDetail : (type,int)
						broadcast ("sys: sf: TgDetail: "+dh.getNumdetail(msgparts[2],Integer.parseInt(msgparts[3])));
					}else if(msgparts[1].equals("getDetail2")){
						//Sys: sf: TgetDetails: (details)
						//sf : getDetail : (type,int)
						broadcast ("sys: sf: TgDetail2: "+dh.getNumdetail(msgparts[2],Integer.parseInt(msgparts[3])));
					}
					else if(msgparts[1].equals("remove")){
						//sf : remove : (type, int)

						if(msgparts[2].equals("A"))
							dh.removeA(Integer.parseInt(msgparts[3]));
						else if(msgparts[2].equals("B"))
							dh.removeB(Integer.parseInt(msgparts[3]));
						else if(msgparts[2].equals("C"))
							dh.removeC(Integer.parseInt(msgparts[3]));
						else if(msgparts[2].equals("D"))
							dh.removeD(Integer.parseInt(msgparts[3]));
					}
					else if(msgparts[1].equals("call")){
						//sf: call: (Dname)
						dh.checkName(msgparts[2]);
					}
					else if(msgparts[1].equals("clear")){
						//sf: clear
						System.out.println("clearing");
						dh.cleanAll();
					}
				}
				else if(msgparts[0].equals("pc")){
					if(msgparts[1].equals("put")){
						//Sys : pc: Tput: (details)
						//Pc: put:(Pnum)
						_Rmsg = dh.add(Integer.parseInt(msgparts[2]));
						System.out.println(dh.getLastdetail(_Rmsg));
						broadcast ("sys: pc: Tput: "+dh.getLastdetail(_Rmsg));
					}
				}

			}
			catch (NumberFormatException nfe) {

			}

			try {
				Thread.sleep(10);
			}
			catch (Exception e){

			}


	}

   protected static void broadcast (String message) {
	  synchronized (handlers) {
		 Enumeration e = handlers.elements ();
         while (e.hasMoreElements ()) {
            CallHandler handler = (CallHandler) e.nextElement ();
            try {
               handler.out.writeUTF (message);
               handler.out.flush ();
            } catch (IOException ex) {
               handler.stop();
            }
         }
      }
   }
}
