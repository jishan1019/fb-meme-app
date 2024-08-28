package com.example.banglastatusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import java.util.Map;

public class MemeActivity extends AppCompatActivity {

    private RecyclerView memeRecyclerView;
    private ArrayList<String> memeList;
    private MemeAdapter memeAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static String URL = "https://appslabbd.buzz/test/0banglaMeme/api/meme_api.php";
    private static String TOKEN = "Bearer QXV0aG9yaXphdGlvbiBoZWFkZXIgbm90IGZvdW5k";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme);

        memeRecyclerView = findViewById(R.id.memeRecyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        memeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        memeList = new ArrayList<>();
        memeAdapter = new MemeAdapter();
        memeRecyclerView.setAdapter(memeAdapter);

        // Initial data fetch
        fetchMemes();

        // Setup SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchMemes();
            }
        });
    }

    private void fetchMemes() {
        swipeRefreshLayout.setRefreshing(true);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        swipeRefreshLayout.setRefreshing(false);

                        memeList.clear(); // Clear existing data
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject memeObject = response.getJSONObject(i);
                                String img = memeObject.getString("img");
                                memeList.add(img);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        memeAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MemeActivity.this, "Error fetching memes!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", TOKEN);
                return headers;
            }
        };

        requestQueue.add(jsonArrayRequest);
    }

    private class MemeAdapter extends RecyclerView.Adapter<MemeAdapter.MemeViewHolder> {

        @Override
        public MemeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mid_screen_meme, parent, false);
            return new MemeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MemeViewHolder holder, int position) {
            String imageUrl = memeList.get(position);

            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.cover)
                    .error(R.drawable.icon)
                    .into(holder.imageViewMeme);
        }

        @Override
        public int getItemCount() {
            return memeList.size();
        }

        public class MemeViewHolder extends RecyclerView.ViewHolder {
            ImageView imageViewMeme;

            public MemeViewHolder(View itemView) {
                super(itemView);

                imageViewMeme = itemView.findViewById(R.id.imageViewMeme);
            }
        }
    }
}
