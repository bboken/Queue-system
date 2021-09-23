import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Day {
    public static void main (String args[]) {
		Date date = new Date();
		//ALL
       System.out.printf("%tc%n",date);
       //¤é´Á
       System.out.printf("%tF%n",date);
       //12
       System.out.printf("%tr%n",date);
       //24
       System.out.printf("%tT%n",date);
       System.out.printf("%tR",date);
     }
}


