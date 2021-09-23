package com.boby.publiccall;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private final String[] shop = {"Shop List","PHS","TYE","FOC","WPG"} ;
    private int position = -1;
    Spinner shopSpn;
    final int REQUEST_CODE = 4701;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnEnter).setOnClickListener(this);
        shopSpn =(Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> shoplist = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,shop);
        shopSpn.setAdapter(shoplist);
        shopSpn.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnEnter){
            if(position>0&&position<shop.length){
                Intent intent = new Intent(this, Main2Activity.class);
                intent.putExtra("Shop",shop[position]);
                startActivity(intent);
                MainActivity.this.finish();
            }
            else
                Toast.makeText(this,"請選擇其中一間分店",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        position = i;
       // Toast.makeText(this,shop[position],Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
