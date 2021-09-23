import java.awt.event.*;
import javax.swing.*;

public class ButtonHandler implements ActionListener {

	protected CallServer server;
	private DetailHandler dh;
	private int number;

	public ButtonHandler(CallServer server, DetailHandler dh) {
		this.server = server;
		this.dh = dh;
	}
	public void actionPerformed(ActionEvent e) {
		//server.sendTextToChat("-1");
		//System.out.println("OnClick");
		number = (int)(Math.random()*10 + 1);

		switch(e.getActionCommand()){
			case "btnA":
				dh.add(7);
				System.out.println("A");
				break;
			case "btnB":
				dh.removeD();
				System.out.println("B");
				break;
			case "btnC":
				System.out.println("C");
				break;
			case "btnD":
				System.out.println("D");
				break;
			case "btnE":
			//	dh.removeB();
			//	dh.removeC();
			//	dh.removeD();
				dh.cleanAll();
				System.out.println("E");
				break;

		}
	}
}
