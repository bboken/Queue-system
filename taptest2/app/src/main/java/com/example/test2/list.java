package com.example.test2;

import java.util.ArrayList;

public class list {
   public static ArrayList<Details> Alist= new  ArrayList<Details>();
    public static ArrayList<Details> Blist= new  ArrayList<Details>();
    public static ArrayList<Details> Clist= new  ArrayList<Details>();
    public  static ArrayList<Details> Dlist= new  ArrayList<Details>();
    public  static ArrayList<Details> Total= new  ArrayList<Details>();

    public  static void addItem(Details D){
        if (D.getName().startsWith("A"))
            Alist.add(D);
        else if (D.getName().startsWith("B"))
            Blist.add(D);
        else if (D.getName().startsWith("C"))
            Clist.add(D);
        else if (D.getName().startsWith("D"))
            Dlist.add(D);

        Total.add(D);
    }


    public static void clean(){
        if(!Alist.isEmpty())
            Alist.clear();
        if(!Blist.isEmpty())
            Blist.clear();
        if(!Clist.isEmpty())
            Clist.clear();
        if(!Dlist.isEmpty())
            Dlist.clear();
        if(!Total.isEmpty())
            Total.clear();
        MainActivity.updataT();
    }

    public static void removeT(Details D){
        boolean isval = true;
        System.out.println("removeT");

        if (D.getName().startsWith("A"))
            Alist.remove(D);
        else if (D.getName().startsWith("B"))
            Blist.remove(D);
        else if (D.getName().startsWith("C"))
            Clist.remove(D);
        else if (D.getName().startsWith("D"))
            Dlist.remove(D);
        else
            isval = false;

        if(isval)
            Total.remove(D);
        MainActivity.updataT();
    }


    public static Details getD (String str){
        Details _d = new Details();
        int i;
        if (str.startsWith("A")){
            System.out.println("name1 "+str+"  "+str.startsWith("A"));
            for(i = 0;i<Alist.size();i++)
                if (str.equals(Alist.get(i).getName())) {
                    _d = Alist.get(i);
                    System.out.println(str + "  :A:  " + Alist.get(i).getName());
                }
            }
        else if (str.startsWith("B")) {
            System.out.println("name1 "+str+"  "+str.startsWith("B"));
            for (i = 0; i < Blist.size(); i++)
                if (str.equals(Blist.get(i).getName())) {
                    _d = Blist.get(i);
                }
        }
        else if (str.startsWith("C")) {
            System.out.println("name1 "+str+"  "+str.startsWith("C"));
            for (i = 0; i < Clist.size(); i++)
                if (str.equals(Clist.get(i).getName())) {
                    _d = Clist.get(i);
                }
        }
        else if (str.startsWith("D")) {
            System.out.println("name1 "+str+"  "+str.startsWith("D"));
            for (i = 0; i < Dlist.size(); i++)
                if (str.equals(Dlist.get(i).getName())) {
                    _d = Dlist.get(i);
                }
        }
     //   System.out.println("name"+_d.getName());
        return _d;
    }



}
