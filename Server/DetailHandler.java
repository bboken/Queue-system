
import java.util.*;
public class DetailHandler {
	List<String> arrayList ;
	static int Anum,Bnum,Cnum,Dnum;
	static ArrayList<Details> Alist;
	static ArrayList<Details> Blist;
	static ArrayList<Details> Clist;
	static ArrayList<Details> Dlist;
	static ArrayList<Details> Total;
	private static UiServer gui;
	Details temp;
	//arrayList.add("aaa");

	ArrayList<Details> getT(){
		return Total;
	}
	ArrayList<Details> getA(){
		return Alist;
	}
	ArrayList<Details> getB(){
		return Blist;
	}
	ArrayList<Details> getC(){
		return Clist;
	}
	ArrayList<Details> getD(){
		return Dlist;
	}

	public DetailHandler(UiServer gui){
		this.gui = gui;
		Alist = new ArrayList();
		Blist = new ArrayList();
		Clist = new ArrayList();
		Dlist = new ArrayList();
		Total = new ArrayList();
	}

   	public String add(int Pnum){

		if(Pnum <3){
			temp = new Details('A',++Anum,Pnum);
			Alist.add(temp);
			Total.add(temp);
			gui.setTA( temp.getName());
			return temp.getName()+": "+temp.getPnum()+": "+temp.getTime();
			//System.out.println(Alist.get(Alist.size()-1).getName());
		}
		else if (Pnum<5){
			temp = new Details('B',++Bnum,Pnum);
			Blist.add(temp);
			Total.add(temp);
			gui.setTB( temp.getName());
			return temp.getName()+": "+temp.getPnum()+": "+temp.getTime();
		}
		else if ( Pnum<7){
			temp = new Details('C',++Cnum,Pnum);
			Clist.add(temp);
			Total.add(temp);
			gui.setTC( temp.getName());
			return temp.getName()+": "+temp.getPnum()+": "+temp.getTime();
		}else if ( Pnum>=7){
			temp = new Details('D',++Dnum,Pnum);
			Dlist.add(temp);
			Total.add(temp);
			gui.setTD( temp.getName());
			return temp.getName()+": "+temp.getPnum()+": "+temp.getTime();
		}
		return null;
	}

	void removeA(int no){
		if(!Alist.isEmpty()){
			gui.setHA(Alist.get(no).getName());
			removeT(Alist.get(no));
			Alist.remove(no);
		}
	}
	void removeB(int no){
		if(!Blist.isEmpty()){
			gui.setHB(Blist.get(no).getName());
			removeT(Blist.get(no));
			Blist.remove(no);
		}
	}
	void removeC(int no){
		if(!Clist.isEmpty()){
			gui.setHC(Clist.get(no).getName());
			removeT(Clist.get(no));
			Clist.remove(no);
		}
	}
	void removeD(int no){
		if(!Dlist.isEmpty()){
			gui.setHD(Dlist.get(no).getName());
			removeT(Dlist.get(no));
			Dlist.remove(no);
		}
	}

	void cleanAll(){
		cleanA();
		cleanB();
		cleanC();
		cleanD();
		gui.Valinit();

		System.out.println("cleanAll");
	}

	void cleanA(){
		Anum = 0;
		if(!Alist.isEmpty()){
			for (Details d : Alist)
				removeT(d);
			Alist.clear();
		}
	}
	void cleanB(){
		Bnum = 0;
		if(!Blist.isEmpty()){
			for (Details d : Blist)
				removeT(d);
			Blist.clear();
		}
	}
	void cleanC(){
		Cnum = 0;
		if(!Clist.isEmpty()){
			for (Details d : Clist)
				removeT(d);
			Clist.clear();
		}
	}
	void cleanD(){
			Dnum = 0;
		if(!Dlist.isEmpty()){
			for (Details d : Dlist)
				removeT(d);
			Dlist.clear();
		}
	}

	void removeT(Details D){
		Total.remove(D);
	}

	public static  void printList(ArrayList<Details> list) {
		for (int i = 0;i<list.size();i++)
            System.out.println(list.get(i).getName() + " ");
		System.out.println();
	}

	public static void print(){
		printList(Total);
	}

	public String getFirstdetail(String type){
		//System.out.println("getfd"+type);
		if(type.startsWith("A"))
			return gui.getHA();
		else if(type.startsWith("B"))
			return gui.getHB();
		else if(type.startsWith("C"))
			return gui.getHC();
		else if(type.startsWith("D"))
			return gui.getHD();
		else
			return null;
	}

	public String getNumdetail(String type,int num){
			if(type.equals("A"))
				temp = Alist.get(num);
			else if(type.equals("B"))
				temp = Blist.get(num);
			else if(type.equals("C"))
				temp = Clist.get(num);
			else if(type.equals("D"))
				temp = Dlist.get(num);
			return temp.getName()+": "+temp.getPnum()+": "+temp.getTime();
	}

	public String getLastdetail(String type){
		if(type.equals("A"))
			temp = Alist.get(Alist.size()-1);
		else if(type.equals("B"))
			temp = Blist.get(Blist.size()-1);
		else if(type.equals("C"))
			temp = Clist.get(Clist.size()-1);
		else if(type.equals("D"))
			temp = Dlist.get(Dlist.size()-1);
		return temp.getName()+": "+temp.getPnum()+": "+temp.getTime();
	}

	public int getlistNum(String type){
		if(type.equals("A"))
			return Alist.size();
		else if(type.equals("B"))
			return Blist.size();
		else if(type.equals("C"))
			return Clist.size();
		else if(type.equals("D"))
			return Dlist.size();
		return 0;
	}

	public void checkName(String Dname){
		if(Dname.charAt(0)=='A'){
			for(int x = 0;x<Alist.size();x++)
				if(((Details)Alist.get(x)).getName().equals(Dname)){
					removeA(x);
					break;
				}
		}
		else if(Dname.charAt(0)=='B'){
			for(int x = 0;x<Blist.size();x++)
				if(((Details)Blist.get(x)).getName().equals(Dname)){
					removeB(x);
					break;
				}
		}
		else if(Dname.charAt(0)=='C'){
			for(int x = 0;x<Clist.size();x++)
				if(((Details)Clist.get(x)).getName().equals(Dname)){
					removeC(x);
					break;
				}
		}
		else if(Dname.charAt(0)=='D'){
			for(int x = 0;x<Dlist.size();x++)
				if(((Details)Dlist.get(x)).getName().equals(Dname)){
					removeD(x);
					break;
				}
		}
	}

}



