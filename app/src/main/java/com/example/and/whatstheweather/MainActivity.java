package com.example.and.whatstheweather;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView textView,textViewTitle;
    ImageView imageView;
    EditText editText;

    public void getWeather(View view){
        textView.setText("");
        String city;
        // add your apikey here, get one in https://openweathermap.org/
        String apikey="";
        city=editText.getText().toString();
        Log.i("city",city);
        DownloadTask task = new DownloadTask();
        task.execute("https://api.openweathermap.org/data/2.5/weather?q="+city+"&APPID="+apikey);
    }

    public class DownloadTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result=new StringBuilder("");
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
                url= new URL(uri.toASCIIString());

                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result.append(current);
                    data = reader.read();
                }
                return result.toString();

            } catch (Exception e) {
                e.printStackTrace();
                textView.setText("City not found: try not to use accent or special caracteres.");
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo = jsonObject.getString("weather");
                Log.i("Weather content", weatherInfo);
                JSONArray arr = new JSONArray(weatherInfo);
                Log.i("Size of array",Integer.toString(arr.length()));
                for (int i=0; i < arr.length(); i++) {
                    JSONObject jsonPart = arr.getJSONObject(i);
                    textView.setText(jsonPart.getString("main") + ": " + jsonPart.getString("description"));
                    if (jsonPart.getString("main").matches("Clear")) {
                        imageView.setImageResource(R.drawable.clear);
                        textView.setTextColor(Color.parseColor("#19334d"));
                        editText.setTextColor(Color.parseColor("#19334d"));
                        textViewTitle.setTextColor(Color.parseColor("#19334d"));
                    } else if (jsonPart.getString("main").matches("Rain") || jsonPart.getString("main").matches("Drizzle")) {
                        imageView.setImageResource(R.drawable.rain);
                        textView.setTextColor(Color.parseColor("#000000"));
                        editText.setTextColor(Color.parseColor("#000000"));
                        textViewTitle.setTextColor(Color.parseColor("#000000"));
                    } else if (jsonPart.getString("main").matches("Clouds")) {
                        imageView.setImageResource(R.drawable.cloud);
                        textView.setTextColor(Color.parseColor("#000000"));
                        editText.setTextColor(Color.parseColor("#000000"));
                        textViewTitle.setTextColor(Color.parseColor("#000000"));
                    } else if (jsonPart.getString("main").matches("Snow")) {
                        imageView.setImageResource(R.drawable.snow);
                        textView.setTextColor(Color.parseColor("#19334d"));
                        editText.setTextColor(Color.parseColor("#19334d"));
                        textViewTitle.setTextColor(Color.parseColor("#19334d"));
                    } else if (jsonPart.getString("main").matches("Thunderstorm") || jsonPart.getString("main").matches("Squall")) {
                        imageView.setImageResource(R.drawable.thunderstorm);
                        textView.setTextColor(Color.parseColor("#FFFFFF"));
                        editText.setTextColor(Color.parseColor("#FFFFFF"));
                        textViewTitle.setTextColor(Color.parseColor("#FFFFFF"));
                    } else if (jsonPart.getString("main").matches("Mist") || jsonPart.getString("main").matches("Smoke") || jsonPart.getString("main").matches("Fog") || jsonPart.getString("main").matches("Haze")) {
                        imageView.setImageResource(R.drawable.mist);
                        textView.setTextColor(Color.parseColor("#FFFFFF"));
                        editText.setTextColor(Color.parseColor("#FFFFFF"));
                        textViewTitle.setTextColor(Color.parseColor("#FFFFFF"));
                    } else if (jsonPart.getString("main").matches("Dust") || jsonPart.getString("main").matches("Sand") || jsonPart.getString("main").matches("Ash")) {
                        imageView.setImageResource(R.drawable.dust);
                        textView.setTextColor(Color.parseColor("#FFFFFF"));
                        editText.setTextColor(Color.parseColor("#FFFFFF"));
                        textViewTitle.setTextColor(Color.parseColor("#FFFFFF"));
                    } else if (jsonPart.getString("main").matches("Tornado")) {
                        imageView.setImageResource(R.drawable.tornado);
                        textView.setTextColor(Color.parseColor("#000000"));
                        editText.setTextColor(Color.parseColor("#000000"));
                        textViewTitle.setTextColor(Color.parseColor("#000000"));
                    } else{
                        imageView.setImageResource(R.drawable.wheater2);
                        textView.setTextColor(Color.parseColor("#19334d"));
                        editText.setTextColor(Color.parseColor("#19334d"));
                        textViewTitle.setTextColor(Color.parseColor("#19334d"));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.i("Error message","didn't work");
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView) findViewById(R.id.textViewResult);
        imageView=(ImageView) findViewById(R.id.imageView);
        editText=(EditText) findViewById(R.id.editText);
        textViewTitle=(TextView) findViewById(R.id.textView);
    }
}
