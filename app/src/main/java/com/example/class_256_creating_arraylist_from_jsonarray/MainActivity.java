package com.example.class_256_creating_arraylist_from_jsonarray;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    ProgressBar progressBar;
    ListView listView;
    TextView tvTitle;

    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        listView = findViewById(R.id.listView);

        String url = "https://masterbari69.000webhostapp.com/apps/video.json";

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,     new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);

                try {

                    for(int x=0; x < response.length(); x++){
                        JSONObject jsonObject = response.getJSONObject(x);
                        String title = jsonObject.getString("title");
                        String video_id = jsonObject.getString("video_id");

                        hashMap = new HashMap<>();
                        hashMap.put("title", title);
                        hashMap.put("video_id", video_id);
                        arrayList.add(hashMap);
                    }

                    MyAdapter myAdapter = new MyAdapter();
                    listView.setAdapter(myAdapter);


                } catch (JSONException e) {
                    throw new RuntimeException(e);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(arrayRequest);
    }

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
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View myView = layoutInflater.inflate(R.layout.item, null);

            TextView tvTitle = myView.findViewById(R.id.tvTitle);
            ImageView imageThumb = myView.findViewById(R.id.imageThumb );

            HashMap<String, String> hashMap = arrayList.get(position);

            String title = hashMap.get("title");
            String video_id = hashMap.get("video_id");

            String image_url = "https://img.youtube.com/vi/" +video_id+ "/0.jpg";

            tvTitle.setText(title);

            Picasso.get().load(image_url).placeholder(R.drawable.pic).into(imageThumb);

            return myView;
        }
    }
}