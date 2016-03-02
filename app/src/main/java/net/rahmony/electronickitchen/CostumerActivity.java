package net.rahmony.electronickitchen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

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
    private Button btn_store1;
    private Button btn_store2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costumer);


        mListView_stores=(ListView)findViewById(R.id.listView_stores);


        btn_store1 = (Button) findViewById(R.id.button);
        btn_store2 = (Button) findViewById(R.id.button2);
        mTab=(TabHost)findViewById(R.id.tabHost);
        mTab.setup();
        TabHost.TabSpec spec=mTab.newTabSpec("tag1");
        spec.setIndicator("المتاجر");
        spec.setContent(R.id.tab1);
        mTab.addTab(spec);

        // TODO: 3/1/2016 You can get the data retrieved from the server !!       ^^


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rahmony.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<List<StoreResult>> reg = apiService.getAllStores();


        reg.enqueue(new Callback<List<StoreResult>>() {
            @Override
            public void onResponse(Response<List<StoreResult>> response, Retrofit retrofit) {

                ArrayList<StoreResult> arrayList = (ArrayList)response.body();
                // as much as Available Stores get their Names ^^ .
                // for (int i ; i<arrayList.size();i++){
                //   arraylist.get(i).getStoreName();
                // }
                //   i need you guys to merge it with the buttons creations loop ..

                if (arrayList != null) {
                        String x = arrayList.get(0).getStoreName();
                        String y = arrayList.get(1).getStoreName();
                        btn_store1.setText(x);
                        btn_store2.setText(y);
                        Toast.makeText(getBaseContext(),   " I Did It ^^ ", Toast.LENGTH_LONG).show();

                }


                    }


                        @Override
                        public void onFailure(Throwable t) {
                            Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwble is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();

                        }
                    }
        );





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

