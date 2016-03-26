
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
import net.rahmony.electronickitchen.Data.Cart;
import net.rahmony.electronickitchen.Data.Store;
import net.rahmony.electronickitchen.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class CustomerActivity extends AppCompatActivity implements  AdapterView.OnItemClickListener {

    TabHost mTab;
    ListView mListView_stores;

    //List View For showing Cart
    ListView mListView_cart;

    ArrayList list_storeName = new ArrayList();
    ArrayList list_storeDescription = new ArrayList();
    ArrayList list_product_ID = new ArrayList();


    //List of Product That in Cart, and it's Price
    ArrayList list_productName = new ArrayList();
    ArrayList list_Price = new ArrayList();
    ArrayList list_Quantity = new ArrayList();
    ArrayList list_storeID = new ArrayList();


//----***** Retrofit ***---//
    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://rahmony.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    APIService apiService = retrofit.create(APIService.class);
    final  Cart cart = new Cart();

//---**************************//

    TextView mTextView_text_cart_no_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        mListView_stores = (ListView) findViewById(R.id.listView_stores);
        mListView_cart = (ListView) findViewById(R.id.listView_cart);

        mTab = (TabHost) findViewById(R.id.tabHost);
        mTab.setup();
        TabHost.TabSpec spec= mTab.newTabSpec("tag1");
        spec.setIndicator("المتاجر");
        spec.setContent(R.id.tab1);
        mTab.addTab(spec);


        mTextView_text_cart_no_data = (TextView) findViewById(R.id.text_cart_no_data);



        Call<List<Store>> reg = apiService.getAllStores();

        reg.enqueue(new Callback<List<Store>>() {
                        @Override
                        public void onResponse(Response<List<Store>> response, Retrofit retrofit) {

                            ArrayList<Store> arrayList = (ArrayList) response.body();
                            String[] storeName = new String[arrayList.size()];
                            String[] storeDescription = new String[arrayList.size()];
                            int[] storeID = new int[arrayList.size()];
                            for (int i = 0; i < arrayList.size(); i++) {
                                if (arrayList != null) {
                                    storeName[i] = arrayList.get(i).getStoreName();
                                    list_storeName.add(i, arrayList.get(i).getStoreName());
                                    storeDescription[i] = arrayList.get(i).getStoreDescription();
                                    list_storeDescription.add(i, arrayList.get(i).getStoreDescription());
                                    storeID[i] = arrayList.get(i).getStoreID();
                                    list_storeID.add(i, arrayList.get(i).getStoreID());

                                }


                            }

                            myAdapter arr = new myAdapter(getBaseContext(), android.R.layout.simple_list_item_1, storeName);
                            mListView_stores.setAdapter(arr);
                        }


                        @Override
                        public void onFailure(Throwable t) {
                            Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwble is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();

                        }
                    }
        );



        mListView_stores.setOnItemClickListener(this);


        spec = mTab.newTabSpec("tag2");
        spec.setIndicator("طلباتي");
        spec.setContent(R.id.tab2);
        mTab.addTab(spec);




        final Bundle extra = getIntent().getExtras();
        cart.setID(extra.getInt("ID"));
        Call<List<Cart>> regCart = apiService.getCart(cart);
        regCart.enqueue(new Callback<List<Cart>>() {

            @Override
            public void onResponse(Response<List<Cart>> response, Retrofit retrofit) {
                if (response.message().equalsIgnoreCase("unauthorized")) {

                    mTextView_text_cart_no_data.setVisibility(View.VISIBLE);

                } else {
                    ArrayList<Cart> arrayList = (ArrayList) response.body();
                    String[] productName = new String[arrayList.size()];
                    int[] Price = new int[arrayList.size()];
                    int[] Quantity = new int[arrayList.size()];
                    int[] Product_ID = new int[arrayList.size()];
                    int[] Invoice_ID = new int[arrayList.size()];
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList != null) {
                            productName[i] = arrayList.get(i).getProductName();
                            list_productName.add(i, arrayList.get(i).getProductName());
                            Price[i] = arrayList.get(i).getPrice();
                            list_Price.add(i, arrayList.get(i).getPrice());
                            Quantity[i] = arrayList.get(i).getQuantity();
                            list_Quantity.add(i, arrayList.get(i).getQuantity());
                            Product_ID[i] = arrayList.get(i).getProduct_ID();
                            list_product_ID.add(i, arrayList.get(i).getProduct_ID());

                        }

                    }
                    mySecondAdapter arr = new mySecondAdapter(getBaseContext(), android.R.layout.simple_list_item_1, productName);
                    mListView_cart.setAdapter(arr);

                    //Take The Invoice ID
                    Invoice_ID[0] = arrayList.get(0).getInvoice_ID();
                    cart.setInvoice_ID(Invoice_ID[0]);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwble is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });



    }


    public void onClick(View v ){

        switch(v.getId()){
            case R.id.btn_confirm_order:


                Call<Cart> call = apiService.confirmOrder(cart);
                call.enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Response<Cart> response, Retrofit retrofit) {
                        if(response.message().equalsIgnoreCase("ok")){
                            Toast.makeText(getBaseContext(),"Order Confirmed",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(CustomerActivity.this, ShowProductOfStroeActivity.class);
        Bundle extra = getIntent().getExtras();


        intent.putExtra("ID", extra.getInt("ID"));
        intent.putExtra("StoreID", Integer.parseInt(list_storeID.get(position).toString()));
        intent.putExtra("StoreName", list_storeName.get(position).toString());
        intent.putExtra("StoreDescription", list_storeDescription.get(position).toString());
        startActivity(intent);

    }




    private class myAdapter extends ArrayAdapter<String> {

        public myAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.list_store, parent, false);

            TextView mText_storeName = (TextView) v.findViewById(R.id.text_StoreName);
            mText_storeName.setText(list_storeName.get(position).toString());

            TextView mText_storeDescription = (TextView) v.findViewById(R.id.text_StoreDescription);
            mText_storeDescription.setText(list_storeDescription.get(position).toString());


            return v;
        }


    }

    private class mySecondAdapter extends ArrayAdapter<String> {

        public mySecondAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.list_cart, parent, false);

            TextView mText_list_ProductName = (TextView) v.findViewById(R.id.text_list_cart_ProductName);
            mText_list_ProductName.setText(list_productName.get(position).toString());

            TextView mText_list_Price = (TextView) v.findViewById(R.id.text_list_cart_Price);
            mText_list_Price.setText(list_Price.get(position).toString());

            TextView mText_list_Quantity = (TextView) v.findViewById(R.id.text_list_cart_Quantity);
            mText_list_Quantity.setText(list_Quantity.get(position).toString());


            return v;
        }


    }

}
