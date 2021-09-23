import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class UiServer extends JFrame {

	//private JScrollPane scrollPane;
	private Container c ;
	private GridBagConstraints g;
	private JPanel[] body = new JPanel[5];
	private JLabel [] Tlbl = new JLabel[4];
	private JLabel [] Hlbl = new JLabel[4];
	private static String[] Tstr = { "A0000", "B0000","C0000","D0000"};
	private static String[] Hstr = { "A0000", "B0000","C0000","D0000"};
	//private JComboBox[] StrList = new JComboBox[2];
	protected JButton aBtn;
	protected JButton bBtn;
	protected JButton cBtn;
	protected JButton dBtn;
	protected JButton eBtn;

    public static void main (String args[]) {
        new UiServer ("test");

     }

	public UiServer (String title){
		super(title);
		UIsetting();


		setSize(400,600);
		setVisible(true);
		Valinit();
	}

	protected void Valinit(){


		//Tstr = { "A0000", "B0000","C0000","D0000"};
		int temp = 65;
		for(int i = 0; i<Hlbl.length;i++){
			Tstr[i] = (char)(temp + i)+"0000";
			Hstr[i] = (char)(temp + i)+"0000";
		}
		update();
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

	private void UIsetting(){

		g = new GridBagConstraints();

		c = getContentPane();
		c.setLayout(new GridBagLayout());

		aBtn = new JButton("A");
		bBtn = new JButton("B");
		cBtn = new JButton("C");
		dBtn = new JButton("D");
		eBtn= new JButton("E");

		aBtn.setActionCommand("btnA");
		bBtn.setActionCommand("btnB");
		cBtn.setActionCommand("btnC");
		dBtn.setActionCommand("btnD");
		eBtn.setActionCommand("btnE");


		for(int i = 0; i<Hlbl.length;i++){
			Hlbl[i] = new JLabel(Hstr[i],JLabel.CENTER);
			Tlbl[i] = new JLabel(Tstr[i],JLabel.CENTER);
		}

		for(int i = 0 ;i< body.length;i++)
		{
			body[i] = new JPanel();
			body[i].setLayout(new GridBagLayout());
		}

		body[0].add(aBtn,setGridConstraints(0,0,1,1,1,1));
		body[0].add(Hlbl[0],setGridConstraints(0,1,1,1,1,3));
		body[0].add(Tlbl[0],setGridConstraints(0,2,1,1,1,1));

		body[1].add(bBtn,setGridConstraints(0,0,1,1,1,1));
		body[1].add(Hlbl[1],setGridConstraints(0,1,1,1,1,3));
		body[1].add(Tlbl[1],setGridConstraints(0,2,1,1,1,1));

		body[2].add(cBtn,setGridConstraints(0,0,1,1,1,1));
		body[2].add(Hlbl[2],setGridConstraints(0,1,1,1,1,3));
		body[2].add(Tlbl[2],setGridConstraints(0,2,1,1,1,1));

		body[3].add(dBtn,setGridConstraints(0,0,1,1,1,1));
		body[3].add(Hlbl[3],setGridConstraints(0,1,1,1,1,3));
		body[3].add(Tlbl[3],setGridConstraints(0,2,1,1,1,1));


		c.add(body[0],setGridConstraints(0,0,1,1,1,1));
		c.add(body[1],setGridConstraints(1,0,1,1,1,2));

		c.add(body[2],setGridConstraints(0,1,1,1,1,1));
		c.add(body[3],setGridConstraints(1,1,1,1,1,2));

		//c.add(eBtn,setGridConstraints(0,2,1,2,1,3));

	}

	public void update(){
		for(int i = 0;i< Hlbl.length;i++){
			Hlbl[i].setText("Current Num:  "+Hstr[i]);
			Tlbl[i].setText("Last Num:  "+Tstr[i]);
		}
	}public String getHA(){
		return Hstr[0];
	}

	public String getHB(){
		return Hstr[1];
	}

	public String getHC(){
		return Hstr[2];
	}

	public String getHD(){
		return Hstr[3];
	}

	public void setHA(String s){
		Hstr[0] = s;
		update();
	}

	public void setHB(String s){
		Hstr[1] = s;
		update();
	}

	public void setHC(String s){
		Hstr[2] = s;
		update();
	}

	public void setHD(String s){
		Hstr[3] = s;
		update();
	}

	public void setTA(String s){
		Tstr[0] = s;
		update();
	}

	public void setTB(String s){
		Tstr[1] = s;
		update();
	}

	public void setTC(String s){
		Tstr[2] = s;
		update();
	}

	public void setTD(String s){
		Tstr[3] = s;
		update();
	}

}


