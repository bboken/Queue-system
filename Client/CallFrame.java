import java.awt.*;
import javax.swing.*;
public class CallFrame extends JFrame {
   //protected JTextArea output;
   protected JLabel input;
   //private JScrollPane scrollPane;
    protected JButton[] btn = new JButton[12];
	private GridBagConstraints g;



	public static void main (String args[]) {
		new CallFrame ("test");
	}

	public CallFrame (String title){
		super (title);
		Container c = getContentPane();
		c.setLayout (new GridBagLayout ());
		g = new GridBagConstraints();

		input = new JLabel (" ",JLabel.CENTER);
		//input.setEnabled(false);

		input.setFont(new Font("Serif", Font.PLAIN, 50));
		c.add(input,setGridConstraints(0,0,1,3,1,1));

		for(int i = 0; i<9;i++){
			btn[i] = new JButton(""+(i+1));
			btn[i].setActionCommand("btn"+(i+1));
			btn[i].setFont(new Font("Serif", Font.PLAIN, 50));
		}
		for(int i = 0; i<9;i ++)
			c.add(btn[i],setGridConstraints(i,1,1,1,1,1));
		for(int i = 3; i<9;i ++)
			c.add(btn[i],setGridConstraints(i-3,2,1,1,1,1));
		for(int i = 6; i<9;i ++)
			c.add(btn[i],setGridConstraints(i-6,3,1,1,1,1));
		btn[9] = new JButton("C");
		btn[9].setActionCommand("btnClean");
		btn[9].setFont(new Font("Serif", Font.PLAIN, 50));
		btn[10] = new JButton("0");
		btn[10].setActionCommand("btn0");
		btn[10].setFont(new Font("Serif", Font.PLAIN, 50));
		btn[11] = new JButton("Enter");
		btn[11].setActionCommand("btnEnter");
		btn[11].setFont(new Font("Serif", Font.PLAIN, 50));
		for(int i = 9; i<btn.length;i ++)
			c.add(btn[i],setGridConstraints(i-9,4,1,1,1,1));

	  setSize(400,300);
	  setVisible(true);
      //input.requestFocus ();
   }

	public void setText(String str){
		input.setText(input.getText() + str);
	}

	public String getText(){
		return input.getText();
	}

	public void cleanText(){
			input.setText(" ");
	}

   private GridBagConstraints setGridConstraints(int x, int y, int height, int width, double weightx, double weighty){
   		g.gridx = x;
   		g.gridy = y;
   		g.gridheight = height;
   		g.gridwidth = width;
   		g.weightx = weightx;
   		g.weighty = weighty;
   		g.fill = GridBagConstraints.BOTH;
   		g.anchor = GridBagConstraints.CENTER;
   		return g;
	}
}


