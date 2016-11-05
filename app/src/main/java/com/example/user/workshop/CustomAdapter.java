package com.example.user.workshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by USER on 11/4/2016.
 */
public class CustomAdapter extends BaseAdapter {

    Context mContext;

    public CustomAdapter(Context applicationContext) {
        this.mContext = applicationContext;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //เลขIndex เช่น แถวแรกก็เป็น0 แถวสองเป็น1

        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder myHolder =null;
        if(convertView == null){
            //Start first row -> convertView is null
            //เตรียม Layout ที่ต้องการให้กับ convertView
            convertView = mInflater.inflate(R.layout.activity_row_list, null);
            myHolder = new ViewHolder();
            myHolder.imgAndriod = (ImageView) convertView.findViewById(R.id.imgAndroid);
            myHolder.tvTopivNews = (TextView) convertView.findViewById(R.id.tvTopicNews);
            myHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);

            convertView.setTag(myHolder);

        }else{
            //convertView ผ่านการ Scroll มาแล้ว
            myHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    public class ViewHolder{
        //ประกาศว่าภายใน View จะมี widget ชื่ออะไรบ้าง
        ImageView imgAndriod;
        TextView tvTopivNews;
        TextView tvDate;
    }
}
