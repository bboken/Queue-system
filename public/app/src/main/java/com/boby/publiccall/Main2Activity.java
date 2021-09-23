package com.boby.publiccall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main2Activity extends Activity implements View.OnClickListener,Runnable {
    EditText txtNum;
    TextView lblDetailName, lblTime,lblShopName,lblNowDetailName;
    private final String[][] shop = {{"PHS","10.0.2.2"},{"TYE","10.0.2.2"},{"FOC",""},{"WPG",""}} ;
    Details _d = null;
    //net
    static Socket socket = null;
    static  DataOutputStream out = null;
    static DataInputStream in = null;
    ReceivingThread rt;
    String msg = null;
    String ip ;  // "192.168.1.133" "10.0.2.2"
    String name;
    int port = 9999;
    private Date curDate;

    private SimpleDateFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txtNum = (EditText)findViewById(R.id.txtNum);
        lblDetailName = (TextView)findViewById(R.id.lblDetailName);
        lblTime = (TextView)findViewById(R.id.lblTime);
        lblShopName = (TextView)findViewById(R.id.lblShopName);
        lblNowDetailName = (TextView)findViewById(R.id.lblNowDetailName);
        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.btnDelect).setOnClickListener(this);
        findViewById(R.id.btnGet).setOnClickListener(this);
        findViewById(R.id.btnUpdata).setOnClickListener(this);
        formatter = new SimpleDateFormat("MM-dd HH:mm");

        if(getIntent().hasExtra("Shop")) {
            name = getIntent().getStringExtra("Shop");
            lblShopName.setText(name);
        }
        int i ;
        for(i = 0;i<shop.length;i++){
            if(name.equals(shop[i][0])) {
                ip = shop[i][1];
                break;
            }
        }
        readPruvateExterbakFile();
       // Toast.makeText(this, "ip : "+ip, Toast.LENGTH_SHORT).show();
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
        savePruvateExterbakFile();
        closeSocket();
    }

    private void closeSocket(){
        System.out.println("close");
        try { if(out!=null) out.close(); } catch (IOException e) {}
        try { if(in!=null) in.close(); } catch (IOException e) {}
        try { if(socket!=null) socket.close(); socket=null; } catch (IOException e) {}
        out = null;
        in = null;
        socket = null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Main2Activity.this.finish();
                break;
            case R.id.btnDelect:
                txtNum.setVisibility(View.VISIBLE);
                findViewById(R.id.btnGet).setClickable(true);
                lblDetailName.setText("-----");
                lblNowDetailName.setText("目前號碼 : ");
                lblTime.setText("領取時間 : ");
                _d = null;
                break;
            case R.id.btnGet:
                try {
                    int i = Integer.parseInt(txtNum.getText().toString());
                    if (i > 0) {
                        // if (Integer.parseInt(txtNum.getText().toString()) < 10) {
                        connect(ip, port, "public");
                        curDate = new Date(System.currentTimeMillis());
                        send("ct: put: " + txtNum.getText().toString());
                        txtNum.setText("");

                        txtNum.setVisibility(View.INVISIBLE);
                        findViewById(R.id.btnGet).setClickable(false);
                        //  }else{

                        //  }
                    } else
                        Toast.makeText(this, "請輸入正確人數", Toast.LENGTH_SHORT).show();
                }catch (NumberFormatException nfe) {
                    Toast.makeText(this, "請輸入人數", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnUpdata:
//                System.out.println(_d.getName());
                if(_d!=null) {
                    connect(ip, port, "public");
                    send("ct: get: " + _d.getName());
                }
                break;
        }
    }


    public static void send(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connect(String ip, int port, String name)  {
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

            rt = new ReceivingThread(this);
            rt.start();
            //Toast.makeText(this, "Enter", Toast.LENGTH_LONG).show();
        } catch(IOException E) {
            Toast.makeText(this, "請稍後再嘗試", Toast.LENGTH_SHORT).show();
            try { if(out!=null) out.close(); } catch (IOException e) {}
            try { if(in!=null) in.close(); } catch (IOException e) {}
            try { if(socket!=null) socket.close(); socket=null; } catch (IOException e) {}
        }
    }

    String _Dname, _NowName,_data;
    @Override
    public void run(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(_Dname != null)       lblDetailName.setText(_Dname);
                if(_NowName != null)     lblNowDetailName.setText("目前號碼 : "+_NowName);
                if(_data != null)        lblTime.setText("領取時間 : "+ _data);
            }
        });

    }


    private  class  ReceivingThread extends Thread {
        String[] msgparts;
        Main2Activity main;
        public ReceivingThread(Main2Activity main){
            this.main = main;
        }
        public  void run()  {
            System.out.println("Thread start");
                while (true) {
                    try {
                        msg = in.readUTF();
                        //System.out.println(msg);
                        checkMsg(msg);
                    }catch (Exception ioe) {
                        break;
                    }
                }
            System.out.println("end run");
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
                    if (msgparts[1].equals("ct")) {
                        if (msgparts[2].equals("Tput")) {
                            _d = new Details(msgparts[3], msgparts[4], msgparts[5]);
                            _Dname = msgparts[3];
                            _NowName = msgparts[6];
                            _data = formatter.format(curDate);
                            main.run();
                            closeSocket();
                        }

                        if (msgparts[2].equals("Tget")) {
                           // settxt(msgparts[3],msgparts[6],formatter.format(curDate));
                            _Dname = null;
                            _NowName = msgparts[3];
                            _data = null;
                            main.run();
                            closeSocket();
                        }
                    }
                }
            } catch (NumberFormatException nfe) {

            }
        }
    }


    //read and write
    private void savePruvateExterbakFile() {
        File folder = getExternalFilesDir("MyApp");
        File myFile = new File(folder,"mydata.txt");
        FileOutputStream fos = null;
        System.out.println("start");
        StringBuffer sb = new StringBuffer();

        try{
            if(myFile.createNewFile()) {
                //Toast.makeText(this,"file created!",Toast.LENGTH_LONG).show();
                fos = new FileOutputStream(myFile);
                if (_d != null)
                    sb.append(" " + lblShopName.getText().toString() + " " + _d.getName() + " " + _d.getPnum() + " " + _d.getTime() + " " + _NowName + " " + _data);

                fos.write(sb.toString().getBytes());
            }
            else {
                // Toast.makeText(this,"file exsits!",Toast.LENGTH_LONG).show();
                fos = new FileOutputStream(myFile);
                if (_d != null)
                    sb.append(" " + lblShopName.getText().toString() + " " + _d.getName() + " " + _d.getPnum() + " " + _d.getTime() + " " + _NowName + " " + _data);
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
                String _shop;
                //                sb.append(" "+lblShopName+" " + _d.getName()+" "+_d.getPnum() +" "+_d.getTime()+" "+lblNowDetailName.getText().toString()+" "+lblTime.getText().toString());
                while(true){
                    if (fscan.hasNext()) {
                        _shop = fscan.next();

                        System.out.println(_shop +" : "+lblShopName.getText().toString());
                        if(_shop.equals(lblShopName.getText().toString())){
                            _d = new Details(fscan.next(),fscan.next(),fscan.next());
                            _Dname = _d.getName();
                            _NowName = fscan.next();
                           // curDate = formatter.parse(fscan.next()+" "+fscan.next(),new ParsePosition(0));
                            _data = fscan.next()+" "+fscan.next();
                            run();

                            txtNum.setVisibility(View.INVISIBLE);
                            findViewById(R.id.btnGet).setClickable(false);
                        }
                    }
                    else
                        break;
                }
            }

        }catch (IOException e){
           // e.printStackTrace();
        }catch (NumberFormatException nfe){
            //nfe.printStackTrace();
        }
    }

}
