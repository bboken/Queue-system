package com.example.test2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class listB extends Activity {

    private ListView listView;
    private static DetailAdapter adapterD;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a1);

        listView=(ListView)findViewById(R.id.lv);
        //实例化ArrayAdapter
        adapterD = new DetailAdapter(this , list.Blist);
        listView.setAdapter(adapterD);//设置适配器
        listView.setOnItemClickListener(onClickListView);

    }


    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Toast 快顯功能 第三個參數 Toast.LENGTH_SHORT 2秒  LENGTH_LONG 5秒
            Toast.makeText(listB.this,"點選第 "+(position +1) +" 個 \n內容："+list.Blist.get(position).getName(), Toast.LENGTH_SHORT).show();
            adapterD.isbtnShow = true;
            adapterD.mod=position;
            updata();
        }
    };


    public static void updata(){
        adapterD.notifyDataSetChanged();
    }

}
