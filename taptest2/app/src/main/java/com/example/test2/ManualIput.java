package com.example.test2;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

public class ManualIput extends Activity implements View.OnClickListener {
    EditText txtNum;
    private static String num = "";
    private static StringBuffer _num = new StringBuffer();
    private static  char type = 'A';
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_iput);
        txtNum = (EditText)findViewById(R.id.txtNum);
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btnA).setOnClickListener(this);
        findViewById(R.id.btnB).setOnClickListener(this);
        findViewById(R.id.btnC).setOnClickListener(this);
        findViewById(R.id.btnD).setOnClickListener(this);
        findViewById(R.id.btnClear).setOnClickListener(this);
        findViewById(R.id.btnRemove).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(_num.length()<4) {
            switch (view.getId()) {
                case R.id.btn0:
                    _num.append("0");
                    break;
                case R.id.btn1:
                    _num.append("1");
                    break;
                case R.id.btn2:
                    _num.append("2");
                    break;
                case R.id.btn3:
                    _num.append("3");
                    break;
                case R.id.btn4:
                    _num.append("4");
                    break;
                case R.id.btn5:
                    _num.append("5");
                    break;
                case R.id.btn6:
                    _num.append("6");
                    break;
                case R.id.btn7:
                    _num.append("7");
                    break;
                case R.id.btn8:
                    _num.append("8");
                    break;
                case R.id.btn9:
                    _num.append("9");
                    break;
            }
        }

            switch (view.getId()){
                case R.id.btnA:
                    type = 'A';
                    break;
                case R.id.btnB:
                    type = 'B';
                    break;
                case R.id.btnC:
                    type = 'C';
                    break;
                case R.id.btnD:
                    type = 'D';
                    break;
            }

        if(view.getId()==R.id.btnClear)
            _num.delete(0,_num.length());
        if(view.getId()==R.id.btnRemove)
            if(_num.length()>0)
            _num.delete(_num.length()-1,_num.length());
        Word();
    }

    private void Word(){
        System.out.println(_num);
        if (_num.length()==0)
            num = type+"0000";
        else if (_num.length()==1)
            num = type+"000"+_num;
        else if (_num.length()==2)
            num = type+"00"+_num;
        else if (_num.length()==3)
            num = type+"0"+_num;
        else if (_num.length()==4)
            num = type+""+_num;
        txtNum.setText(num);
    }

    public static String getNumber(){
        return num;
    }
}
