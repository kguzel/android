package com.example.mihribanguzel.devamsizliktakip;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kadirguzel on 5/13/18.
 */

public class ResultAdapter extends ArrayAdapter<Lesson> {

    private List<Lesson> lessonsList;
    private final Activity context;


    static class ViewHolder {
        public TextView dersText;
        public TextView devamsizlikTexView;
        public TextView tarihTexView;
        public TextView resultTexView;
        public LinearLayout back;

    }

    public ResultAdapter(Activity context, List<Lesson> lessonsList) {
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
            ResultAdapter.ViewHolder viewHolder = new ResultAdapter.ViewHolder();
            viewHolder.dersText = (TextView) rowView.findViewById(R.id.dersTexView);
            viewHolder.devamsizlikTexView = (TextView) rowView.findViewById(R.id.devamsizlikTexView);
            viewHolder.tarihTexView = (TextView) rowView.findViewById(R.id.tarihTexView);
            viewHolder.back=(LinearLayout) rowView.findViewById(R.id.back);
            viewHolder.resultTexView=(TextView) rowView.findViewById(R.id.resultTexView);
            rowView.setTag(viewHolder);
        }


        // fill data
        ResultAdapter.ViewHolder holder = (ResultAdapter.ViewHolder) rowView.getTag();
        Lesson lesson = lessonsList.get(position);
        holder.dersText.setText(lesson.getDers_adi());
        holder.devamsizlikTexView.setText("Devamsizlik: "+lesson.getY_Devamsiz() +" / "+ lesson.getDevamsiz_H());
        holder.tarihTexView.setText(lesson.getGun() +" "+lesson.getDerssaati()+":"+lesson.getDersdakika() );


        if(lesson.getY_Devamsiz()>= lesson.getDevamsiz_H()){
            holder.resultTexView.setText("Kaldınız");
        }

        if (position%2==0) {
            holder.back.setBackgroundColor(0x80585858);
        } else {
            holder.back.setBackgroundColor(0x0F00FF00);
        }


        return rowView;
    }
}
