import java.awt.event.*;

public class EnterListener extends KeyAdapter {
   CallControl client;
   CallFrame gui;

   public EnterListener (CallControl client, CallFrame gui) {
      this.client = client;
      this.gui = gui;
   }

   public void keyPressed(KeyEvent e) {
      if (e.getKeyCode()==KeyEvent.VK_ENTER) {
		 try{
			 int temp = Integer.parseInt(gui.input.getText());
			 if(temp>0 && temp <1000)
				client.sendTextToChat(Integer.toString(temp));
		}
		catch (NumberFormatException nfe) {
			client.sendTextToChat("-2");
		}

          gui.input.setText("");
      }
   }
}



