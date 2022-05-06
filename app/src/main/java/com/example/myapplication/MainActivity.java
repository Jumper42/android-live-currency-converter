package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.os.AsyncTask;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    TextView chfText, usdText, jpyText, tryText, cadText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chfText = findViewById(R.id.textchf);
        usdText = findViewById(R.id.textusd);
        jpyText = findViewById(R.id.textjpy);
        tryText = findViewById(R.id.texttry);
        cadText = findViewById(R.id.textcad);
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(v -> {
            getRates();
        });

    }

    public void getRates (){
        DownloadData downloadData = new DownloadData();
        try {
            String url = "http://data.fixer.io/api/latest?acces_key=qft0kfwz1Jmz8V63AZKKx62JesWYSyYd&format=1";
            downloadData.execute(url);
            Log.i("test", "messagee");
        }catch (Exception e) {

        }
    }

    private class  DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;
            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while (data>0){
                    char character = (char) data;
                    data = inputStreamReader.read();
                }

                return result;

            }catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");
                String rates = jsonObject.getString("rates");

                JSONObject jsonObject1 = new JSONObject(rates);

                String tryMoney = jsonObject1.getString("TRY");
                String cadMoney = jsonObject1.getString("CAD");
                String usdMoney = jsonObject1.getString("USD");
                String jpyMoney = jsonObject1.getString("JPY");
                String chfMoney = jsonObject1.getString("CHF");

                tryText.setText(tryText.getText() + tryMoney);
                cadText.setText(cadText.getText() + cadMoney);
                usdText.setText(usdText.getText() + usdMoney);
                jpyText.setText(jpyText.getText() + jpyMoney);
                chfText.setText(chfText.getText() + chfMoney);
            } catch (Exception e){

            }
        }
    }
}
