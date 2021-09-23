package com.example.test2;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
public class DetailAdapter extends BaseAdapter implements View.OnClickListener {
    private LayoutInflater myInflater;
    private List<Details> details;
    int mod = 0;
    boolean isbtnShow = false;

    public DetailAdapter(Context context,List<Details> detail){
        myInflater = LayoutInflater.from(context);
        this.details = detail;
    }

    @Override
    public void onClick(View view) {
        System.out.println("click :"+details.get(mod).getName());
        Details _d;

        if (MainActivity.type.equals("A")) {
            _d = list.Alist.get(mod);
            if (_d != null) {
                MainActivity.send("sf: remove: A: "+mod);
                list.removeT(_d);
            }
        } else if (MainActivity.type.equals("B")){
            _d = list.Blist.get(mod);
            if (_d != null) {
                MainActivity.send("sf: remove: B: "+mod);
                list.removeT(_d);
            }
        } else if (MainActivity.type.equals("C")){
            _d = list.Clist.get(mod);
            if (_d != null) {
                MainActivity.send("sf: remove: C: "+mod);
                list.removeT(_d);
            }
        } else if (MainActivity.type.equals("D")){
            _d = list.Dlist.get(mod);
            if (_d != null) {
                MainActivity.send("sf: remove: D: "+mod);
                list.removeT(_d);
            }
        }

        isbtnShow = false;
        notifyDataSetChanged();
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView lblName;
        TextView lblTime;
        TextView lblNum;
        Button btndCall;
        public ViewHolder(TextView lblName, TextView lblTime,TextView lblNum, Button btndCall){
            this.lblName = lblName;
            this.lblTime = lblTime;
            this.lblNum = lblNum;
            this.btndCall = btndCall;
        }
    }

    @Override
    public int getCount() {
        return details.size();
    }

    @Override
    public Object getItem(int arg0) {
        return details.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return details.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView = myInflater.inflate(R.layout.listitemdetail, null);
            holder = new ViewHolder(
                    (TextView) convertView.findViewById(R.id.lblName),
                    (TextView) convertView.findViewById(R.id.lblTime),
                    (TextView) convertView.findViewById(R.id.lblNum),
                    (Button) convertView.findViewById(R.id.btndCall)
            );
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Details detail = (Details)getItem(position);

        holder.lblName.setText(detail.getName());
        //holder.lblName.setTextColor(Color.BLACK);
        //holder.lblName.setBackgroundColor(Color.BLACK);

        holder.lblTime.setText(detail.getTime());
        //holder.lblTime.setTextColor(Color.BLACK);
        //holder.lblTime.setVisibility(time_vis[type_num]);

        holder.lblNum.setText(detail.getPnum());
        //holder.lblNum.setTextColor(Color.BLACK);
        //holder.lblNum.setVisibility(time_vis[type_num]);

        holder.btndCall.setOnClickListener(this);
        if(isbtnShow)
            if(position == mod)
                holder.btndCall.setVisibility(View.VISIBLE);
            else
                holder.btndCall.setVisibility(View.INVISIBLE);


        return convertView;
    }



}
