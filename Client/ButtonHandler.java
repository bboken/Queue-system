import java.awt.event.*;
import javax.swing.*;

public class ButtonHandler implements ActionListener {

	protected CallControl client;
	private CallFrame gui;
	JFrame f;

	public ButtonHandler(CallControl client, CallFrame gui) {
		this.client = client;
		this.gui = gui;
	}
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "btn0":
				gui.setText("0");
				break;
			case "btn1":
				gui.setText("1");
				break;
			case "btn2":
				gui.setText("2");
				break;
			case "btn3":
				gui.setText("3");
				break;
			case "btn4":
				gui.setText("4");
				break;
			case "btn5":
				gui.setText("5");
				break;
			case "btn6":
				gui.setText("6");
				break;
			case "btn7":
				gui.setText("7");
				break;
			case "btn8":
				gui.setText("8");
				break;
			case "btn9":
				gui.setText("9");
				break;
			case "btnClean":
				gui.cleanText();
				break;
			case "btnEnter":
				try {

					//System.out.println("Enter");
					int Pnum = Integer.parseInt(gui.getText().replace(" ",""));
					if(Pnum>=10){
						int result=JOptionPane.showConfirmDialog(f,
							"確定輸入人數是"+gui.getText()+" 嗎?",
							"確認訊息",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE);

						if (result==JOptionPane.YES_OPTION){
							System.out.println(Pnum);
							client.sendTextToChat("pc: put:"+gui.getText());
						}
					}
					else if(Pnum>0)
					{
						//System.out.println("<10");
						client.sendTextToChat("pc: put:"+gui.getText());
					}
					gui.cleanText();
				}
				catch (NumberFormatException nfe) {
				}
				//System.out.println("Out");
				break;
		}
	}


}
