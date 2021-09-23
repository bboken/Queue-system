package com.example.callingcontrol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DetailAdapter extends BaseAdapter{
    private LayoutInflater myInflater;
    private List<Details> details;

    public DetailAdapter(Context context, List<Details> detail){
        myInflater = LayoutInflater.from(context);
        this.details = detail;
    }
    /*private view holder class*/
    private class ViewHolder {
        TextView lblName;
        TextView lblTime;
        TextView lblNum;
        public ViewHolder(TextView lblName, TextView lblTime,TextView lblNum){
            this.lblName = lblName;
            this.lblTime = lblTime;
            this.lblNum = lblNum;
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
                    (TextView) convertView.findViewById(R.id.lblNum)
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

        return convertView;
    }
}
