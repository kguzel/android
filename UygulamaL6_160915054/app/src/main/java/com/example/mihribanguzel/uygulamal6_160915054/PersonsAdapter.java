

package com.example.mihribanguzel.uygulamal6_160915054;

/**
 * Created by kadirguzel on 3/21/18.
 */


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;


public class PersonsAdapter extends RecyclerView.Adapter<PersonsAdapter.MyViewHolder> {

    private List<Person> personsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, city, country;

        public MyViewHolder(View view) {
            super(view);


            //todo

            name = (TextView) view.findViewById(R.id.name);
            city = (TextView) view.findViewById(R.id.city);
            country = (TextView) view.findViewById(R.id.country);
        }
    }


    public PersonsAdapter(List<Person> personsList) {
        this.personsList = personsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //todo

        Person person = personsList.get(position);
        holder.name.setText(person.name);
        holder.city.setText(person.city);
        holder.country.setText(person.country);
    }

    @Override
    public int getItemCount() {
        return personsList.size();
    }
}
