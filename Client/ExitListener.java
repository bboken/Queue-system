import java.awt.event.*;

public class ExitListener extends WindowAdapter {
   protected CallControl client;

   public ExitListener(CallControl client) {
      this.client = client;
   }

   public void windowClosing(WindowEvent e) {
      client.disconnect();
      System.exit(0);
   }
}





