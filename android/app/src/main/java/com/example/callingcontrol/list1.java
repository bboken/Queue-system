package com.example.callingcontrol;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class list1  extends Activity {



    private ListView listView;

    List<Details> detail_list = new ArrayList<Details>();
    private DetailAdapter adapterD;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tabview);

        detail_list.add(new Details('A',1,1));
        detail_list.add(new Details('B',1,3));
        detail_list.add(new Details('A',2,2));
        detail_list.add(new Details('C',1,5));

        detail_list.add(new Details('B',2,3));
        detail_list.add(new Details('D',1,8));
        detail_list.add(new Details('C',2,6));
        detail_list.add(new Details('A',3,2));

        listView=(ListView)findViewById(R.id.lv);
        //实例化ArrayAdapter
        adapterD = new DetailAdapter(list1.this , detail_list);
        listView.setAdapter(adapterD);//设置适配器
        listView.setOnItemClickListener(onClickListView);
    }


    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Toast 快顯功能 第三個參數 Toast.LENGTH_SHORT 2秒  LENGTH_LONG 5秒
            Toast.makeText(list1.this,"點選第 "+(position +1) +" 個 \n內容："+detail_list.get(position).getName(), Toast.LENGTH_SHORT).show();
        }
    };
}
