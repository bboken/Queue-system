package com.example.callingcontrol;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Details{
    private int Pnum;
    private Date curDate;
    private String name;
    private SimpleDateFormat formatter;

    public Details(char c, int num, int Pnum){
        if(num<10)
            name = c+"000"+num;
        else if (num <100)
            name = c+"00"+num;
        else if (num <1000)
            name = c+"0"+num;
        else
            name = ""+c+num;
        this.Pnum = Pnum;
        //time = LocalDateTime.now();

        formatter = new SimpleDateFormat("HH:mm:ss");

        curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間

        String str = formatter.format(curDate);
    }

    public Details(Details d){
        this.formatter = new SimpleDateFormat("HH:mm:ss");
        this.name = d.getName();
        this.Pnum = Integer.parseInt(d.getPnum());
        this.curDate = getTime2();
    }

    public String getName(){
        return name;
    }

    public String getTime(){return  formatter.format(curDate);	}
    public Date getTime2(){return	 curDate;	}

    public String getPnum(){
        return Integer.toString(Pnum);
    }


}
