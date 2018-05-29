//App2Activity.java


package com.example.mihribanguzel.uygulamal5_160915054;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class App2Activity extends AppCompatActivity {

    private EditText editText;
    private ProgressBar progressBar;
    private int counter;
    private Timer timer;
    private TimerTask timerTask;
    final Handler handler = new Handler();

    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app2);

        editText=(EditText)findViewById(R.id.editText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);


        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);

                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = editText.getText().toString();
                runner.execute(sleepTime);
            }
        });
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {

            //publishProgress((i+1)*percentage);

            int time = Integer.parseInt(params[0]);
            try {

                final int percentage = 100/time;

                for ( i = 0; i < time; i++) {

                    // ekranda 1 sey degismek icin mecbur
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress((i+1)*percentage);
                        }
                    });

                    // 1 saniye bekletme
                    Thread.sleep(1000);
                }
                resp = "";
            } catch (InterruptedException e) {
                e.printStackTrace();
                resp = e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // işlem bitince çalıştırılır
        }


        @Override
        protected void onPreExecute() {
           //ilk burasi kosulur

        }

        @Override
        protected void onProgressUpdate(String... percentage) {
            // ara ara burasi cagrilir

           // progressBar.setProgress(percentage[0]);

            //finalResult.setText(text[0]);
            // Uzun sürecek işlemlerin yürütülmesinde yapılacak işlemler
            // örneğin, ProgessDialog güncellenebilir.

            //progressDialog = ProgressDialog.show(MainActivity.this,
            //        "ProgressDialog",
            //        "Az KALDI " + " saniye bekleyiniz");
        }
    }

}
