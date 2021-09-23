import java.awt.event.*;

public class ExitListener extends WindowAdapter {
   protected CallServer client;

   public ExitListener(CallServer client) {
      this.client = client;
   }

   public void windowClosing(WindowEvent e) {
      //client.disconnect();
      System.exit(0);
   }
}





