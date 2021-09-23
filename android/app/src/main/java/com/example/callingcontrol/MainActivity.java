package com.example.callingcontrol;

import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class MainActivity extends TabActivity  implements View.OnClickListener {
    TextView lblA,lblB,lblC,lblD,lblAll,lblType;
    ScrollView svA, svB,svC,svD, svTemp;
    char type ;
    private final String shop = "PHS";


    //net
    Socket socket = null;
    DataOutputStream out = null;
    DataInputStream in = null;
    ObjectInputStream ojin = null;
    ReceivingThread rt;
    String msg = null;
    String ip = "10.0.2.2";  // "192.168.1.133" "10.0.2.2"
    int port = 9999;

    Object object;
    ArrayList<Details> Alist;
    ArrayList<Details> Blist;
    ArrayList<Details> Clist;
    ArrayList<Details> Dlist;
    ArrayList<Details> Total;

    private TabHost tabHost =null;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //tab

        tabHost = getTabHost();

        TabHost.TabSpec spec = tabHost.newTabSpec("0");
        Intent it1 = new Intent(this,listA.class);

        spec.setContent(it1); //Setting the activity of t1
        spec.setIndicator("list A");  // Naming the name of Tab
        tabHost.addTab(spec);
/*
        TabHost.TabSpec spec2 = tabHost.newTabSpec("listB");
        Intent it2 = new Intent(this,listB.class);

        spec2.setContent(it2);
        spec2.setIndicator("list B");
        tabHost.addTab(spec2);
*/


        lblAll = (TextView)findViewById(R.id.listT);
        lblType = (TextView)findViewById(R.id.lblType);

        findViewById(R.id.btnA).setOnClickListener(this);
        findViewById(R.id.btnB).setOnClickListener(this);
        findViewById(R.id.btnC).setOnClickListener(this);
        findViewById(R.id.btnD).setOnClickListener(this);
        findViewById(R.id.btnCall).setOnClickListener(this);
        findViewById(R.id.btnConnect).setOnClickListener(this);
        type = 'A';
        settext(type);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnA:
                svTemp.setVisibility(View.GONE);
                svA.setVisibility(View.VISIBLE);
                svB.setVisibility(View.GONE);
                svC.setVisibility(View.GONE);
                svD.setVisibility(View.GONE);
                type = 'A';
                break;
            case  R.id.btnB:
                svTemp.setVisibility(View.GONE);
                svA.setVisibility(View.GONE);
                svB.setVisibility(View.VISIBLE);
                svC.setVisibility(View.GONE);
                svD.setVisibility(View.GONE);
                type = 'B';
                break;
            case  R.id.btnC:
                svTemp.setVisibility(View.GONE);
                svA.setVisibility(View.GONE);
                svB.setVisibility(View.GONE);
                svC.setVisibility(View.VISIBLE);
                svD.setVisibility(View.GONE);
                type = 'C';
                break;
            case  R.id.btnD:
                svTemp.setVisibility(View.GONE);
                svA.setVisibility(View.GONE);
                svB.setVisibility(View.GONE);
                svC.setVisibility(View.GONE);
                svD.setVisibility(View.VISIBLE);
                type = 'D';
                break;
            case R.id.btnCall:
                send("in: "+type+"call");
                break;
            case R.id.btnConnect:
                Toast.makeText(this, "Connect to server", Toast.LENGTH_LONG).show();
                connect(ip, port,shop);

                break;
        }
        settext(type);

    }

    void settext(char c){
        lblType.setText("Type : "+c);
    }

    private void connect(String ip, int port, String name) {
        String output = null;
        try {
            System.out.println("connecting");
            socket = new Socket(ip, port);

            System.out.println("socket");
            out = new DataOutputStream(socket.getOutputStream());

            System.out.println("out");
            //in = new DataInputStream(socket.getInputStream());
            ojin = new ObjectInputStream(socket.getInputStream());
            System.out.println("in");

            out.writeUTF(name);
           // init();
            rt = new ReceivingThread();
           // rt.start();
            //Toast.makeText(this, "Enter", Toast.LENGTH_LONG).show();
            lblAll.append( " entered\n");
        } catch(IOException e) {
        }
    }
/*
    private void init(){
        getT();
        getA();
        getB();
        getC();
        getD();
    }

    private void getT(){
        try {
            out.writeUTF("out: get: T");
            object = ojIn.readObject();
            Total = (ArrayList<Details>) object;
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void getA(){
        try {
            out.writeUTF("out: get:A");
            object = ojIn.readObject();
            Alist = (ArrayList<Details>) object;
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getB(){
        try {
            out.writeUTF("out: get:B");
            object = ojIn.readObject();
            Blist = (ArrayList<Details>) object;
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getC(){
        try {
            out.writeUTF("out: get:C");
            object = ojIn.readObject();
            Clist = (ArrayList<Details>) object;
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void getD(){
        try {
            out.writeUTF("out: get:D");
            object = ojIn.readObject();
            Dlist = (ArrayList<Details>) object;
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
*/
    private void send(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        ConnectivityManager cMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // NetworkInfo netInfo = cMgr.getActiveNetworkInfo();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try { if(out!=null) out.close(); } catch (IOException e) {}
        try { if(in!=null) in.close(); } catch (IOException e) {}
        try { if(socket!=null) socket.close(); socket=null; } catch (IOException e) {}
    }

    private class ReceivingThread extends Thread {
        public void run() {

            System.out.println("Thread start");
            while(true) {
                try {
                    msg =(String) ojin.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Append message to tvMessage
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(msg!= null)
                            lblAll.append(msg+"\n"  );
                        msg =null;
                    }
                });

            }
        }
    }
}
