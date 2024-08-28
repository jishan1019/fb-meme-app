package com.example.banglastatusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private static final int REQUEST_WRITE_STORAGE = 112;



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


            holder.llTvViewBtn.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), PreviewMemeActivity.class);
                intent.putExtra("imageUrl", imageUrl);
                holder.itemView.getContext().startActivity(intent);
            });

            holder.llTvLikeBtn.setOnClickListener(v -> {

            });

            holder.llTvDownloadBtn.setOnClickListener(v -> {
                downloadAndSaveImage(memeList.get(position));
            });

            holder.llTvShareBtn.setOnClickListener(v -> {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, imageUrl);

                v.getContext().startActivity(Intent.createChooser(shareIntent, "Share Image URL"));
            });

        }

        @Override
        public int getItemCount() {
            return memeList.size();
        }

        //------------ Recycel view ----------------------------------

        public class MemeViewHolder extends RecyclerView.ViewHolder {
            ImageView imageViewMeme;
            LinearLayout llTvLikeBtn,llTvDownloadBtn,llTvViewBtn,llTvShareBtn;

            public MemeViewHolder(View itemView) {
                super(itemView);

                imageViewMeme = itemView.findViewById(R.id.imageViewMeme);
                llTvLikeBtn = itemView.findViewById(R.id.llTvLikeBtn);
                llTvDownloadBtn = itemView.findViewById(R.id.llTvDownloadBtn);
                llTvViewBtn = itemView.findViewById(R.id.llTvViewBtn);
                llTvShareBtn = itemView.findViewById(R.id.llTvShareBtn);

            }
        }


        //------------ Img download check permission ----------------------------------

        private void downloadAndSaveImage(final String imageUrl) {

            new Thread(() -> {
                try {
                    java.net.URL url = new URL(imageUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(input);

                    String savedImageURL = MediaStore.Images.Media.insertImage(
                            getContentResolver(), bitmap,
                            "Image_" + System.currentTimeMillis(), "Image downloaded from URL");

                    if (savedImageURL != null) {
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(),
                                "Image saved to gallery!", Toast.LENGTH_SHORT).show());
                    } else {
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(),
                                "Error saving image!", Toast.LENGTH_SHORT).show());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(),
                            "Error saving image!", Toast.LENGTH_SHORT).show());
                }
            }).start();
        }



    }
}
