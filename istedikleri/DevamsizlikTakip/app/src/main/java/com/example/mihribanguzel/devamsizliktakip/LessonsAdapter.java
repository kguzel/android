package com.example.mihribanguzel.devamsizliktakip;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


public class LessonsAdapter extends ArrayAdapter<Lesson> {

    private List<Lesson> lessonsList;
    private final Activity context;


    static class ViewHolder {
        public TextView dersText;
        public TextView devamsizlikTexView;
        public TextView tarihTexView;
        public LinearLayout back;

    }

    public LessonsAdapter(Activity context, List<Lesson> lessonsList) {
        super(context, R.layout.lesson_item, lessonsList);
        this.context = context;
        this.lessonsList = lessonsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.lesson_item, null);

            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.dersText = (TextView) rowView.findViewById(R.id.dersTexView);
            viewHolder.devamsizlikTexView = (TextView) rowView.findViewById(R.id.devamsizlikTexView);
            viewHolder.tarihTexView = (TextView) rowView.findViewById(R.id.tarihTexView);
            viewHolder.back=(LinearLayout) rowView.findViewById(R.id.back);
            rowView.setTag(viewHolder);
        }


        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        Lesson lesson = lessonsList.get(position);
        holder.dersText.setText(lesson.getDers_adi());
        holder.devamsizlikTexView.setText(lesson.getGun() +" "+lesson.getDerssaati()+":"+lesson.getDersdakika() );


        if (position%2==0) {
            holder.back.setBackgroundColor(0x80585858);
        } else {
            holder.back.setBackgroundColor(0x0F00FF00);
        }


        return rowView;
    }
}
