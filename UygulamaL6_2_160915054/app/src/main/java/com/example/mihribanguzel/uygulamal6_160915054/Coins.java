package com.example.mihribanguzel.uygulamal6_160915054;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kadirguzel on 3/21/18.
 */


//todo
public class Coins {



    @SerializedName("name")
    public String name;
    @SerializedName("symbol")
    public String symbol;
    @SerializedName("rank")
    public String rank;
    @SerializedName("price_usd")
    public String price_usd;
    @SerializedName("price_btc")
    public String price_btc;
    @SerializedName("24h_volume_usd")
    public String vol_usd;
    @SerializedName("market_cap_usd")
    public String market_cap_usd;
    @SerializedName("available_supply")
    public String available_supply;
    @SerializedName("total_supply")
    public String total_supply;
    @SerializedName("max_supply")
    public String max_supply;
    @SerializedName("percent_change_1h")
    public String percent_change_1h;
    @SerializedName("percent_change_24h")
    public String percent_change_24h;
    @SerializedName("percent_change_7d")
    public String percent_change_7d;
    @SerializedName("last_updated")
    public String last_updated;


}


