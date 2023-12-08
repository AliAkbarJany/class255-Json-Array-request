package com.rafsan.class255jsonarrayrequest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
public class MainActivity extends AppCompatActivity {
    TextView textView;
    ProgressBar progressBar;
    ListView listView;

    ArrayList <HashMap < String,String >> arrayList = new ArrayList<>();
    HashMap<String , String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);

        progressBar = findViewById(R.id.progressBar);
        listView = findViewById(R.id.listView);

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://ali71.000webhostapp.com/apps/video.json";
        // Request a string response from the provided URL.
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, "https://ali71.000webhostapp.com/apps/video.json", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d("serverRes", String.valueOf(response));
                        progressBar.setVisibility(View.GONE);

                        try {
                            for (int x = 0; x<response.length(); x++){

                                JSONObject jsonObject = response.getJSONObject(x);
                                String title = jsonObject.getString("title");
                                String video_id = jsonObject.getString("video_id");

                                hashMap = new HashMap<>();
                                hashMap.put("title",title);
                                hashMap.put("video_id",video_id);
                                arrayList.add(hashMap);
                            }

                            MyAdapter myAdapter = new MyAdapter();
                            listView.setAdapter(myAdapter);

                        }
                        catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.VISIBLE);
                        textView.setText("VOLLEY ERROR");

                    }
                }
        );
        queue.add(arrayRequest);
    }

    //======================= ADAPTER==============================

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View myView = layoutInflater.inflate(R.layout.items,parent,false);

            TextView textViewTitle = myView.findViewById(R.id.textViewTitle);
            ImageView imageCover = myView.findViewById(R.id.imageCover);

            HashMap< String,String > hashMap = arrayList.get(position);

            String title = hashMap.get("title");
            String video_id = hashMap.get("video_id");

            textViewTitle.setText(title);

       //     https://img.youtube.com/vi/<insert-youtube-video-id-here>/0.jpg

            String img_Url = "https://img.youtube.com/vi/" +video_id+"/0.jpg";

            Picasso.get()
                    .load(img_Url)
                    .placeholder(R.drawable.img)
                    .into(imageCover);



            return myView;
        }
    }
}