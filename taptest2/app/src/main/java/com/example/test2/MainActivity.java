package com.example.test2;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.telecom.Call;
import android.view.View;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends TabActivity implements View.OnClickListener, TabHost.OnTabChangeListener, Runnable {
    private ListView listT;
    private static DetailAdapter adapterD;
    private TabHost tabHost;
    private static final String TabA = "A", TabB = "B", TabC = "C",TabD = "D",TabM = "M";

    //net
    static Socket socket = null;
    static  DataOutputStream out = null;
    static DataInputStream in = null;
    ReceivingThread rt;
    String msg = null;
    String ip = "10.0.2.2";  // "192.168.1.133" "10.0.2.2"
    int port = 9999;
    private final String shop = "PHS";
    static String type ;

    TextView lblType;
    String[] msgparts;


    //state
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
        savePruvateExterbakFile();
        try { if(out!=null) out.close(); } catch (IOException e) {}
        try { if(in!=null) in.close(); } catch (IOException e) {}
        try { if(socket!=null) socket.close(); socket=null; } catch (IOException e) {}
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readPruvateExterbakFile();
        //detailCre();
        argCre();
        tabCre();
    }

//Details _details = null;

//listener
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnCall:
                    Details _d = null;
                    //Toast.makeText(this, "call", Toast.LENGTH_LONG).show();
                    if (type.equals(TabA) && !list.Alist.isEmpty()) {
                        _d = list.Alist.get(0);
                        if (_d != null) {
                            send("sf: remove: A: 0");
                            list.removeT(_d);
                        }
                    } else if (type.equals(TabB) && !list.Blist.isEmpty()) {
                        _d = list.Blist.get(0);
                        if (_d != null) {
                            send("sf: remove: B: 0");
                            list.removeT(_d);
                        }
                    } else if (type.equals(TabC) && !list.Clist.isEmpty()) {
                        _d = list.Clist.get(0);
                        if (_d != null) {
                            send("sf: remove: C: 0");
                            list.removeT(_d);
                        }
                    } else if (type.equals(TabD) && !list.Dlist.isEmpty()) {
                        _d = list.Dlist.get(0);
                        if (_d != null) {
                            send("sf: remove: D: 0");
                            list.removeT(_d);
                        }
                    } else if (type.equals(TabM)) {
                        String str = ManualIput.getNumber();
                        send("sf: call: " + str);
                        _d = list.getD(str);
                        if (_d != null)
                            list.removeT(_d);
                        //System.out.println(str);
                    }
                    //send("in: "+type+"call");
                    break;

                case R.id.btnConnect:
                    Toast.makeText(this, "Connect to server", Toast.LENGTH_LONG).show();
                    connect(ip, port, shop);
                    findViewById(R.id.btnConnect).setVisibility(View.GONE);
                    break;

                case R.id.btnClean:
                    send("sf: clear");
                    list.clean();
                    break;

                case R.id.btnRefresh:
                    System.out.println("Refresh");
                    send("sf: getSize: A");
                    list.clean();
                    break;
            }
        }
        catch (NullPointerException npe){

            Toast.makeText(this, "please connect the server", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTabChanged(String s) {
        //lblType.setText(tabHost.getCurrentTabTag());
        type = tabHost.getCurrentTabTag();
    }


//onCreate
    void detailCre(){
        Details d = new Details('A',1,1);
        list.addItem(d);

         d = new Details('A',2,1);
         list.addItem(d);

        d = new Details('B',1,1);
        list.addItem(d);

        d = new Details('C',1,1);
        list.addItem(d);

        d = new Details('D',1,1);
        list.addItem(d);

        d = new Details('A',1,1);
        list.addItem(d);

        d = new Details('B',2,1);
        list.addItem(d);

        d = new Details('C',2,1);
        list.addItem(d);

        d = new Details('D',2,1);
        list.addItem(d);
    }

    void argCre(){
        findViewById(R.id.btnCall).setOnClickListener(this);
        findViewById(R.id.btnConnect).setOnClickListener(this);
        findViewById(R.id.btnClean).setOnClickListener(this);
        findViewById(R.id.btnRefresh).setOnClickListener(this);
        rt = new ReceivingThread(this);

        //list view
        listT=(ListView)findViewById(R.id.allList);
        adapterD = new DetailAdapter(this , list.Total);
        listT.setAdapter(adapterD);
        listT.invalidate();

    }

    void tabCre(){
        tabHost = getTabHost();

        tabHost.setOnTabChangedListener(this);

        TabSpec spec = tabHost.newTabSpec(TabA);
        Intent it1 = new Intent(this,listA.class);

        spec.setContent(it1); //Setting the activity of t1
        spec.setIndicator(TabA);  // Naming the name of Tab
        tabHost.addTab(spec);

        TabSpec spec2 = tabHost.newTabSpec(TabB);
        Intent it2 = new Intent(this,listB.class);

        spec2.setContent(it2);
        spec2.setIndicator(TabB);
        tabHost.addTab(spec2);

        TabSpec spec3 = tabHost.newTabSpec(TabC);
        Intent it3 = new Intent(this,listC.class);

        spec3.setContent(it3);
        spec3.setIndicator(TabC);
        tabHost.addTab(spec3);

        TabSpec spec4 = tabHost.newTabSpec(TabD);
        Intent it4 = new Intent(this,listD.class);

        spec4.setContent(it4);
        spec4.setIndicator(TabD);
        tabHost.addTab(spec4);

        TabSpec spec5 = tabHost.newTabSpec(TabM);
        Intent it5 = new Intent(this,ManualIput.class);

        spec5.setContent(it5);
        spec5.setIndicator(TabM);
        tabHost.addTab(spec5);


        tabHost.setCurrentTab(0); // Setting the default Tab
        type = tabHost.getCurrentTabTag();
    }


    public static void updata(){
        adapterD.notifyDataSetChanged();
    }

    public static void updataT(){
        if (type.equals(TabA)) {
            listA.updata();
        } else if (type.equals(TabB)){
            listB.updata();
        } else if (type.equals(TabC)){
            listC.updata();
        } else if (type.equals(TabD)){
            listD.updata();
        }
        updata();
    }

    //net

    public static void send(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connect(String ip, int port, String name) {
        findViewById(R.id.btnConnect).setVisibility(View.GONE);
        String output = null;
        try {
            System.out.println("connecting");
            socket = new Socket(ip, port);

            System.out.println("socket");
            out = new DataOutputStream(socket.getOutputStream());

            System.out.println("out");
            in = new DataInputStream(socket.getInputStream());
           // ojin = new ObjectInputStream(socket.getInputStream());
            System.out.println("in");

            out.writeUTF(name);
            // init();
             rt.start();
            Toast.makeText(this, "Enter", Toast.LENGTH_LONG).show();
        } catch(IOException E) {
            try { if(out!=null) out.close(); } catch (IOException e) {}
            try { if(in!=null) in.close(); } catch (IOException e) {}
            try { if(socket!=null) socket.close(); socket=null; } catch (IOException e) {}
        }
    }


@Override
    public void run(){
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            updataT();
        }
    });

    }

    //read and write
    private void savePruvateExterbakFile() {
        File folder = getExternalFilesDir("MyApp");
        File myFile = new File(folder,"mydata.txt");
        FileOutputStream fos = null;
        System.out.println("start");
        StringBuffer sb = new StringBuffer();
        try{
            Details _d;
            if(myFile.createNewFile()){
                //Toast.makeText(this,"file created!",Toast.LENGTH_LONG).show();
                fos = new FileOutputStream(myFile);
                for(int i = 0; i < list.Total.size();i++) {
                    _d = list.Total.get(i);
                    sb.append(" " + _d.getName()+" "+_d.getPnum() +" "+_d.getTime());
                }
                fos.write(sb.toString().getBytes());
            }
            else{
               // Toast.makeText(this,"file exsits!",Toast.LENGTH_LONG).show();
                fos = new FileOutputStream(myFile);
                for(int i = 0; i < list.Total.size();i++) {
                    _d = list.Total.get(i);
                    sb.append(" " + _d.getName()+" "+_d.getPnum() +" "+_d.getTime());
                }
                fos.write(sb.toString().getBytes());
            }


        }
        catch(IOException e){
            // e.printStackTrace();
        }
        finally {
            System.out.println("finally");
            try{
                fos.flush();
                fos.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readPruvateExterbakFile() {
        File folder = getExternalFilesDir("MyApp");
        File myFile = new File(folder,"mydata.txt");
        Scanner fscan = null;
        String name = null;
        String number = null;
        System.out.println("readPruvateExterbakFile");
        try{
            if(myFile.exists()) {
                fscan = new Scanner(myFile);
                System.out.println("while");
                int x = 0;
                Details temp;
                while(true){
                    if (fscan.hasNext()) {
                        //System.out.println("whiling"+ (x++)+" : "+fscan.next());
                        temp = new Details(fscan.next(),fscan.next(),fscan.next());
                        list.addItem(temp);
                    }
                    else
                        break;
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }catch (NumberFormatException nfe){
            nfe.printStackTrace();
        }
    }

    private  class  ReceivingThread extends Thread {
        Details _d;
        String[] msgparts;
        MainActivity main;
        public ReceivingThread(MainActivity main){
            this.main = main;

        }
        public synchronized void run() {

            System.out.println("Thread start");
            while (true) {
                try {
                    msg = in.readUTF();
                    checkMsg(msg);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }

        void checkMsg(String str) {
            msgparts = str.split(": ");

            for (int i = 0; i < msgparts.length; i++)
                System.out.println(msgparts[i]);
            //System.out.println("TgDetail "+msgparts[2]+" "+msgparts[2].equals("TgDetail"));

            String _Rmsg;
            try {

                if (msgparts[0].equals("sys")) {
                    System.out.println("sys "+msgparts[0].equals("sys"));
                    if (msgparts[1].equals("pc")) {
                        if (msgparts[2].equals("Tput")) {
                            _d = new Details(msgparts[3],msgparts[4],msgparts[5]);
                            list.addItem(_d);
                            main.run();
                        }

                    }
                    if (msgparts[1].equals("ct")) {
                        if (msgparts[2].equals("Tput")) {
                            _d = new Details(msgparts[3],msgparts[4],msgparts[5]);
                            list.addItem(_d);
                            main.run();
                        }

                    }
                    if(msgparts[1].equals("sf")){
                        System.out.println("sf "+msgparts[1].equals("sf"));
                        if (msgparts[2].equals("TgSize")) {
                            String _str = null ;
                            System.out.println("TgSize start "+msgparts[4]);
                            int _x = Integer.parseInt(msgparts[4]);
                            String _T = msgparts[3];

                            for (int x = 0; x < _x; x++) {
                                System.out.println("st " + (x+1) + " / " + _x);
                                send("sf: getDetail2: " + _T + ": " + x);
                                try { msg = in.readUTF(); } catch (IOException e) {  }

                                System.out.println(msg);
                                msgparts = msg.split(": ");
                                if (msgparts[0].equals("sys"))
                                    if (msgparts[1].equals("sf"))
                                        if (msgparts[2].equals("TgDetail2")) {
                                            System.out.println("TgDetail " + msgparts[2].equals("TgDetail"));
                                            _d = new Details(msgparts[3], msgparts[4], msgparts[5]);
                                            list.addItem(_d);
                                        }

                                System.out.println("end " + (x+1) + " / " + _x);
                            }
                            if(_T.equals("A"))
                                send("sf: getSize: B");
                            else if(_T.equals("B"))
                                 send("sf: getSize: C");
                            else if(_T.equals("C"))
                                send("sf: getSize: D");
                            else if(_T.equals("D"))
                                main.run();
                        }
                        if(msgparts[2].equals("TgDetail")){
                            System.out.println("TgDetail "+msgparts[2].equals("TgDetail"));
                            _d = new Details(msgparts[3], msgparts[4], msgparts[5]);
                            list.addItem(_d);
                            main.run();
                        }
                    }
                }

            } catch (NumberFormatException nfe) {

            }
        }

    }
}
