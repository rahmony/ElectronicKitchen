package net.rahmony.electronickitchen.ClassActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import net.rahmony.electronickitchen.APIService;
import net.rahmony.electronickitchen.Data.Store;
import net.rahmony.electronickitchen.Data.StoreResult;
import net.rahmony.electronickitchen.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class CostumerActivity extends AppCompatActivity implements TabHost.OnTabChangeListener , AdapterView.OnItemClickListener  {

    TabHost mTab;
    ListView mListView_stores;


    ArrayList list_storeName = new ArrayList();
    ArrayList list_storeDescription = new ArrayList();
    ArrayList list_storeID = new ArrayList();


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



        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rahmony.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<List<Store>> reg = apiService.getAllStores();



        reg.enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Response<List<Store>> response, Retrofit retrofit) {

                ArrayList<Store> arrayList = (ArrayList)response.body();
                // as much as Available Stores get their Names ^^ .
                // for (int i ; i<arrayList.size();i++){
                //   arraylist.get(i).getStoreName();
                // }
                //   i need you guys to merge it with the buttons creations loop ..

                String [] storeName = new String[arrayList.size()];
                String [] storeDescription = new String[arrayList.size()];
                int [] storeID = new int[arrayList.size()];
                for(int i = 0 ; i < arrayList.size() ; i++) {
                    if (arrayList != null) {
                        storeName[i] = arrayList.get(i).getStoreName();
                        list_storeName.add(i,arrayList.get(i).getStoreName());
                        storeDescription[i] = arrayList.get(i).getStoreDescription();
                        list_storeDescription.add(i,arrayList.get(i).getStoreDescription());
                        storeID[i]=arrayList.get(i).getStoreID();
                        list_storeID.add(i,arrayList.get(i).getStoreID());

                    }


                }

                myAdapter arr=new myAdapter(getBaseContext(),android.R.layout.simple_list_item_1,storeName);
                mListView_stores.setAdapter(arr);
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
        mListView_stores.setOnItemClickListener(this);




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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       Intent intent = new Intent(CostumerActivity.this ,ShowProductOfStroeActivity.class);
        Bundle extra = getIntent().getExtras();


        intent.putExtra("ID", extra.getInt("ID"));
        intent.putExtra("StoreID",Integer.parseInt(list_storeID.get(position).toString()));
        intent.putExtra("StoreName",list_storeName.get(position).toString());
        intent.putExtra("StoreDescription",list_storeDescription.get(position).toString());
       startActivity(intent);

    }


    private class myAdapter extends ArrayAdapter<String>
    {

        public myAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
        }


        @Override
        public View getView(int position , View convertView , ViewGroup parent)
        {

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View v =  inflater.inflate(R.layout.list_store, parent, false);

            TextView mText_storeName = (TextView)v.findViewById(R.id.text_StoreName);
            mText_storeName.setText(list_storeName.get(position).toString());

            TextView mText_storeDescription = (TextView)v.findViewById(R.id.text_StoreDescription);
            mText_storeDescription.setText(list_storeDescription.get(position).toString());



            return v;
        }


    }

}

