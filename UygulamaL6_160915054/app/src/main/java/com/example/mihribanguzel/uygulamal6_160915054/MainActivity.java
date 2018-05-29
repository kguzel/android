

package com.example.mihribanguzel.uygulamal6_160915054;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    //todo
    private String JSON_URL = "http://www.androidevreni.com/dersler/json_veri.txt";
    private OkHttpClient client = new OkHttpClient();

    private List<Person> personList = new ArrayList<>();
    private RecyclerView recyclerView;

    //todo
    private PersonsAdapter personsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timber.i("onCreate ..");

        // AsyncTask calistirma
        new DownloadJSON().execute();

    }


    /**
     * JSON DATAYI ARKAPLANDA İNDİRMEMİZİ SAĞLAYACAK DownloadJSON ASYCTASK SINIFIMIZ
     */
    private class DownloadJSON extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            Timber.i("onPreExecute");

        }

        @Override
        protected String doInBackground(String... params) {
            Timber.i("doInBackground ..");


            // İNDİRME İŞİNİ BURADA YAPACAĞIZ
            String jsonData = null;
            try {
                jsonData = getJsonDataFromServer(JSON_URL);
                Timber.i("JSON VERİMİZ : > " + jsonData);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // JSON VERİYİ NULL DEĞİLSE JAVA NESNELERİNE DÖNÜŞTÜR
            if (null != jsonData) {
                Timber.i("jsonData null değil ..");

                // VERİYİ GSON İLE NESNELERE DÖNÜŞTÜR
                Gson gson = new Gson();
                personList = Arrays.asList(gson.fromJson(jsonData, Person[].class));

                //todo
                // LİSTEYİ KONTROL AMAÇLI LOGCAT E YAZDIR
                for (int i = 0; i < personList.size(); i++) {

                    Person person= personList.get(i);

                    Log.d("mihriban","name #" + i + " : > " + person.name);
                    Timber.i("city #" + i + " : > " + person.city);
                    Timber.i("country #" + i + " : > " + person.country);
                }
            }


            return null;
        }

        // Task sonu
        @Override
        protected void onPostExecute(String result) {
            Timber.i("onPostExecute ..");


            // RECYCLERVIEW gostermek icin listede
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            personsAdapter = new PersonsAdapter(personList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(personsAdapter);

        }
    }




    /**
     * OkHttp İLE ADRESTEKİ VERİYİ İNDİREN METODUMUZ
     */
    private String getJsonDataFromServer(String url) throws IOException {
        Timber.i("run ..");
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
