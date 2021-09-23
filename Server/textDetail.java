import java.text.SimpleDateFormat;
import java.util.Date;

public class textDetail{
	public static void main (String args[])throws Exception {
		Date curDate;
		SimpleDateFormat formatter= new SimpleDateFormat("HH:mm");

		Details d1 = new Details('A',1,2);
		Details d2 = new Details('B',3,4);
		Details d3 = new Details(d2);
		System.out.println(d1.getName());
		System.out.println(d1.getPnum());
		System.out.println(d1.getTime2());
		System.out.println(d1.getTime());
		 curDate = d1.getTime2();
		 String str = d1.getTime();

		 System.out.println("test2");
		System.out.println(curDate);
		System.out.println(formatter.parse(str));

		System.out.println(d3.getName());
		System.out.println(d3.getPnum());
		System.out.println(d3.getTime2());
		System.out.println(d3.getTime());



   }

}