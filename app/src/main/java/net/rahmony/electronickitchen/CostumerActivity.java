package net.rahmony.electronickitchen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class CostumerActivity extends AppCompatActivity implements TabHost.OnTabChangeListener{

    TabHost mTab;
    ListView mListView_stores;


   String [] list = {"rahmony" ,"khalid"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costumer);

        mListView_stores=(ListView)findViewById(R.id.listView_stores);



        mTab=(TabHost)findViewById(R.id.tabHost);
        mTab.setup();
        TabHost.TabSpec spec=mTab.newTabSpec("tag1");
        spec.setIndicator("المتاجر");
        spec.setContent(R.id.tab1);
        mTab.addTab(spec);

        // TODO: 3/1/2016 I can't get the data retrieved from the server !!


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rahmony.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<StoreResult> reg = apiService.getAllStores();


        reg.enqueue(new Callback<StoreResult>() {
                        @Override
                        public void onResponse(Response<StoreResult> response, Retrofit retrofit) {
                            Toast.makeText(getBaseContext(), "here " , Toast.LENGTH_LONG).show();
                            

                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwble is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();

                        }
                    }
        );



        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1,list );
        mListView_stores.setAdapter(adapter);



        spec=mTab.newTabSpec("tag2");
        spec.setIndicator("طلباتي");
        spec.setContent(R.id.tab2);
        mTab.addTab(spec);

        mTab.setOnTabChangedListener(this);
    }

    @Override
    public void onTabChanged(String tabId) {

        if(tabId.equals("tag1"))
        {
            Toast.makeText(this, "Tab 1 changed", Toast.LENGTH_LONG).show();
        }
        else if(tabId.equals("tag2"))
        {
            Toast.makeText(this,"Tab 2 changed",Toast.LENGTH_LONG).show();
        }


    }



}

