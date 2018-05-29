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


public class CoinsAdapter extends RecyclerView.Adapter<CoinsAdapter.MyViewHolder> {

    private List<Coins> coinsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id, name, symbol, rank, price_usd, price_btc, vol_usd, market_cap_usd,available_supply,total_supply,max_supply,percent_change_1h,percent_change_24h,
                percent_change_7d,last_updated;

        public MyViewHolder(View view) {
            super(view);


            //todo


            name = (TextView) view.findViewById(R.id.name);
            symbol = (TextView) view.findViewById(R.id.symbol);
            rank = (TextView) view.findViewById(R.id.rank);

            price_usd = (TextView) view.findViewById(R.id.price_usd);
            price_btc = (TextView) view.findViewById(R.id.price_btc);
            vol_usd = (TextView) view.findViewById(R.id.vol_usd);

            market_cap_usd = (TextView) view.findViewById(R.id.market_cap_usd);
            available_supply = (TextView) view.findViewById(R.id.available_supply);
            total_supply = (TextView) view.findViewById(R.id.total_supply);

            max_supply = (TextView) view.findViewById(R.id.max_supply);
            percent_change_1h = (TextView) view.findViewById(R.id.percent_change_1h);
            percent_change_24h = (TextView) view.findViewById(R.id.percent_change_24h);
            percent_change_7d = (TextView) view.findViewById(R.id.percent_change_7d);
            last_updated = (TextView) view.findViewById(R.id.last_updated);



        }
    }


    public CoinsAdapter(List<Coins> personsList) {
        this.coinsList = personsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //todo

        Coins coins = coinsList.get(position);
        holder.name.setText(coins.name);
        holder.symbol.setText(coins.symbol);
        holder.rank.setText(coins.rank);
        holder.price_usd.setText(coins.price_usd);
        holder.price_btc.setText(coins.price_btc);
        holder.vol_usd.setText(coins.vol_usd);
        holder.market_cap_usd.setText(coins.market_cap_usd);
        holder.available_supply.setText(coins.available_supply);
        holder.total_supply.setText(coins.total_supply);
        holder.max_supply.setText(coins.max_supply);
        holder.percent_change_1h.setText(coins.percent_change_1h);
        holder.percent_change_24h.setText(coins.percent_change_24h);
        holder.percent_change_7d.setText(coins.percent_change_7d);
        holder.last_updated.setText(coins.last_updated);



    }

    @Override
    public int getItemCount() {
        return coinsList.size();
    }
}
