package com.example.rebelfoodschallenge;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView, searchRecyclerview;
    List<Model> modelList, searchList;
    ProgressDialog progressDialog;

    int c = 0 ;

    EditText etsearch;
    String search;
    ImageView searchimg;
    private final static String jsonURL = "https://s3-ap-southeast-1.amazonaws.com/he-public-data/beercraft5bac38c.json";

    MyAdapter adapter, searchAdapter;
    private RequestQueue requestQueue;
    private RequestQueue requestQueue1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etsearch = findViewById(R.id.edittext_search);
        searchimg = findViewById(R.id.search_image);
        searchRecyclerview = findViewById(R.id.searchRecyclerview);

        recyclerView = findViewById(R.id.recyclerview);
        modelList = new ArrayList<>();

//        Log.i("Hiiii","Mainnn" );

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading data from API");
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        extract();

        searchimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = etsearch.getText().toString().trim();
                searchFunction(search);
            }
        });

    }

    private void extract(){

        requestQueue = Volley.newRequestQueue(this);
        requestQueue1 = Volley.newRequestQueue(this);

        Log.i("Hiii","Extract Method" );

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, jsonURL, null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    try {
                        Log.i("Hiii",response.getString(0) );


                        for( int i= 0; i<response.length() ; i++) {
                            JSONObject jsonObject = response.getJSONObject(i);

                            String newjsonURL = "https://s3-ap-southeast-1.amazonaws.com/he-public-data/beerimages7e0480d.json";

                            int finalI = i;


                            Model model = new Model();

                            model.setAbv(jsonObject.getString("abv"));
                            model.setIbu(jsonObject.getString("ibu"));
                            model.setId(jsonObject.getInt("id"));
                            model.setStyle(jsonObject.getString("style"));
                            model.setName(jsonObject.getString("name"));
                            model.setOunces(jsonObject.getDouble("ounces"));

                            JsonArrayRequest jsonArrayRequest2 = new JsonArrayRequest(Request.Method.GET, newjsonURL, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response1) {
                                        try {
                                            Log.i("Hiii", response1.getString(0));
//
//                                            for (int j = 0; j < response1.length(); j++) {
//                                                JSONObject jsonObject2 = response1.getJSONObject(0);

                                                Log.i("Hiiiii", jsonObject.getString("ounces"));

                                                if(finalI % 5 ==0){
                                                    model.setImage(response1.getJSONObject(0).getString("image"));
                                                }
                                                else if(finalI % 5 == 1){
                                                    model.setImage(response1.getJSONObject(1).getString("image"));
                                                }
                                                else if(finalI % 5 == 2){
                                                    model.setImage(response1.getJSONObject(2).getString("image"));
                                                }
                                                else if(finalI % 5 == 3){
                                                    model.setImage(response1.getJSONObject(3).getString("image"));
                                                }
                                                else if(finalI % 5 == 4){
                                                    model.setImage(response1.getJSONObject(4).getString("image"));
                                                }

//                                            }
                                        } catch (JSONException e) {
                                            Log.i("Hiii", "Something wrong 2nd link");
                                            e.printStackTrace();
                                        }

                                    }
                                },new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    });

                            requestQueue1.add(jsonArrayRequest2);

                            modelList.add(model);
                        }
                    }
                    catch (JSONException e) {
                        Log.i("Hiii","Something wrong 1" );
                        e.printStackTrace();
                    }
//                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                    final LinearLayoutManager layoutManager = new LinearLayoutManager(com.example.rebelfoodschallenge.MainActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemViewCacheSize(5);

                    adapter = new MyAdapter(com.example.rebelfoodschallenge.MainActivity.this , modelList);
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext() , "Something went wrong" , Toast.LENGTH_SHORT).show();
                Log.d("Hii" , "Something went wrong");
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void searchFunction(String searchText){

        searchList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        requestQueue1 = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, jsonURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
//                            Log.i("Hiii",response.getString(0) );

                            for( int i= 0; i<response.length() ; i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                String newjsonURL = "https://s3-ap-southeast-1.amazonaws.com/he-public-data/beerimages7e0480d.json";

                                int finalI = i;

                                Model model = new Model();

                                model.setAbv(jsonObject.getString("abv"));
                                model.setIbu(jsonObject.getString("ibu"));
                                model.setId(jsonObject.getInt("id"));
                                model.setStyle(jsonObject.getString("style"));
                                model.setName(jsonObject.getString("name"));
                                model.setOunces(jsonObject.getDouble("ounces"));

                                if(searchText.matches(jsonObject.getString("name"))) {
                                    JsonArrayRequest jsonArrayRequest2 = new JsonArrayRequest(Request.Method.GET, newjsonURL, null,
                                            new Response.Listener<JSONArray>() {
                                                @Override
                                                public void onResponse(JSONArray response1) {
                                                    try {
                                                        Log.i("Hiiiii", jsonObject.getString("ounces"));

                                                        if (finalI % 5 == 0) {
                                                            model.setImage(response1.getJSONObject(0).getString("image"));
                                                        } else if (finalI % 5 == 1) {
                                                            model.setImage(response1.getJSONObject(1).getString("image"));
                                                        } else if (finalI % 5 == 2) {
                                                            model.setImage(response1.getJSONObject(2).getString("image"));
                                                        } else if (finalI % 5 == 3) {
                                                            model.setImage(response1.getJSONObject(3).getString("image"));
                                                        } else if (finalI % 5 == 4) {
                                                            model.setImage(response1.getJSONObject(4).getString("image"));
                                                        }

//                                            }
                                                    } catch (JSONException e) {
                                                        Log.i("Hiii", "Something wrong 2nd link");
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    });

                                    requestQueue1.add(jsonArrayRequest2);

                                    searchList.add(model);
                                    c = 1;
                                }
                            }

                            if(c == 0){
                                Toast.makeText(getApplicationContext() , "No result found" , Toast.LENGTH_LONG).show();
                                extract();
                            }
                        }
                        catch (JSONException e) {
                            Log.i("Hiii","Something wrong 1" );
                            e.printStackTrace();
                        }

                        final LinearLayoutManager layoutManager = new LinearLayoutManager(com.example.rebelfoodschallenge.MainActivity.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        searchRecyclerview.setLayoutManager(layoutManager);
                        searchRecyclerview.setItemViewCacheSize(5);

                        if(c == 1) {
                            searchAdapter = new MyAdapter(com.example.rebelfoodschallenge.MainActivity.this, searchList);
                            searchRecyclerview.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            searchRecyclerview.setAdapter(searchAdapter);
                            searchRecyclerview.setAdapter(searchAdapter);
                        }
                        else {
                            Toast.makeText(getApplicationContext() , "No result found" , Toast.LENGTH_LONG).show();
                            recyclerView.setVisibility(View.VISIBLE);
                            extract();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext() , "Something went wrong" , Toast.LENGTH_SHORT).show();
                Log.d("Hii" , "Something went wrong");
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}