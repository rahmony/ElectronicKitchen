package net.rahmony.electronickitchen.ClassActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.rahmony.electronickitchen.APIService;
import net.rahmony.electronickitchen.Data.Cart;
import net.rahmony.electronickitchen.Data.Product;
import net.rahmony.electronickitchen.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class StoreActivity extends AppCompatActivity implements TabHost.OnTabChangeListener, AdapterView.OnItemClickListener {



    /****************************** Retrofit ************************************/
    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://rahmony.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    APIService apiService = retrofit.create(APIService.class);
    /***************************************************************************/

    // List View in Store To show product
    ListView mListView_product;

    //List View in Store To show Order
    ListView mListView_store_order;

    // Product Object
    final Product product = new Product();

    //Cart Object
    final  Cart cart = new Cart();

    //Array For getting productName and productPrice
    ArrayList list_productName = new ArrayList();
    ArrayList list_productPrice = new ArrayList();

    //Array For getting firstName and lastName
    ArrayList list_FirstName = new ArrayList();
    ArrayList list_LastName = new ArrayList();

    TabHost mTab;
    ImageButton mbtnImage_store_left;
    ImageView mImage_store;
    TextView mStoreName, mText_store_description;

   // static final int mCamRequest = 2;

    /**
     *
     * ###################################################################################
     * ************************************ On Create ************************************
     * ###################################################################################
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        mbtnImage_store_left = (ImageButton) findViewById(R.id.btnImage_store_left);
        mImage_store = (ImageView) findViewById(R.id.image_store);


        mTab = (TabHost) findViewById(R.id.tab_store);
        mTab.setup();
        TabHost.TabSpec spec = mTab.newTabSpec("tag1");
        spec.setIndicator("متجري");
        spec.setContent(R.id.tab_store_1);
        mTab.addTab(spec);

        spec = mTab.newTabSpec("tag2");
        spec.setIndicator("وصلكم طلب");
        spec.setContent(R.id.tab_store_2);
        mTab.addTab(spec);

        final Bundle extra = getIntent().getExtras();
        mStoreName = (TextView) findViewById(R.id.text_store_name);
        mStoreName.setText(extra.getString("StoreName"));
        mText_store_description = (TextView) findViewById(R.id.text_store_description);
        mText_store_description.setText(extra.get("StoreDescription").toString());

        product.setStore_ID(extra.getInt("Store_ID"));

    }

    /**
     *
     * ###################################################################################
     * ************************************ On Resume ************************************
     * ###################################################################################
     *
     */

    @Override
    protected void onResume(){
        super.onResume();

        final Bundle extra = getIntent().getExtras();

        /**
         * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Showing Product ~~~~~~~~~~~~~~~~~~~~~~~~~ *
         */
        //implement List View For Showing Product
        mListView_product = (ListView) findViewById(R.id.listView_product);

        Call<List<Product>> reg = apiService.getMyProducts(product);

        reg.enqueue(new Callback<List<Product>>() {
                        @Override
                        public void onResponse(Response<List<Product>> response, Retrofit retrofit) {


                            ArrayList<Product> arrayList = (ArrayList) response.body();

                            String[] productName = new String[arrayList.size()];
                            String[] productPrice = new String[arrayList.size()];
                            for (int i = 0; i < arrayList.size(); i++) {
                                if (arrayList != null) {
                                    productName[i] = arrayList.get(i).getProductName();
                                    list_productName.add(i, arrayList.get(i).getProductName());
                                    productPrice[i] = arrayList.get(i).getPrice() + "";
                                    list_productPrice.add(i, arrayList.get(i).getPrice());
                                }
                            }
                            myAdapter arr = new myAdapter(getBaseContext(), android.R.layout.simple_list_item_1, productName);
                            mListView_product.setAdapter(arr);
                        }


                        @Override
                        public void onFailure(Throwable t) {
                            Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwble is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
        );

        mListView_product.setOnItemClickListener(this);

        /**
         * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Showing Orders ~~~~~~~~~~~~~~~~~~~~~~~~~ *
         */

        //implement List View For Showing Order
        mListView_store_order = (ListView) findViewById(R.id.listView_store_order);

        //ID For The User
        cart.setID(extra.getInt("ID"));
        //Store_ID For The User
        cart.setStore_ID(extra.getInt("Store_ID"));

        //Start Call
        Call<List<Cart>> callOrder = apiService.getOrder(cart);
        callOrder.enqueue(new Callback<List<Cart>>() {
                              @Override
                              public void onResponse(Response<List<Cart>> response, Retrofit retrofit) {


                                  if(response.message().equalsIgnoreCase("ok")){
                                  ArrayList<Cart> arrayList = (ArrayList) response.body();

                                  String[] FirstName = new String[arrayList.size()];
                                  String[] LastName = new String[arrayList.size()];
                                  for (int i = 0; i < arrayList.size(); i++) {
                                      if (arrayList != null) {
                                          FirstName[i] = arrayList.get(i).getFirstName();
                                          list_FirstName.add(i, arrayList.get(i).getFirstName());
                                          LastName[i] = arrayList.get(i).getLastName();
                                          list_LastName.add(i, arrayList.get(i).getLastName());
                                      }
                                  }
                                  mySecondAdapter arr = new mySecondAdapter(getBaseContext(), android.R.layout.simple_list_item_1, FirstName);
                                  mListView_store_order.setAdapter(arr);
                                  }
                              }

                              @Override
                              public void onFailure(Throwable t) {
                                  Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwble is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
                              }
                          }
        );
    }


    /**
     *
     * ###################################################################################
     * ************************************ On Stop **************************************
     * ###################################################################################
     *
     */

    @Override
    protected void onStop(){
        super.onStop();
    }



    public void onClick_addProduct(View view) {

        Bundle extras = getIntent().getExtras();
        Intent intent = new Intent(StoreActivity.this, Product_Activity.class).putExtra("Store_ID", extras.getInt("Store_ID"));
        startActivity(intent);

    }


    public void onClickme(View view) {

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "64861-200.png");

        Glide.with(this).load(file).into(mImage_store);


    }


    public void onClick(View view) {

    }




    @Override
    public void onTabChanged(String tabId) {
        /*if (tabId.equals("tag1")) {
            Toast.makeText(this, "Tab 1 changed", Toast.LENGTH_LONG).show();
        } else if (tabId.equals("tag2")) {
            Toast.makeText(this, "Tab 2 changed", Toast.LENGTH_LONG).show();
        }*/
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(this, list_productName.get(position).toString(), Toast.LENGTH_LONG).show();

    }

    private class myAdapter extends ArrayAdapter<String> {

        public myAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.list_product, parent, false);

            TextView mText_ProductName = (TextView) v.findViewById(R.id.text_ProductName);
            mText_ProductName.setText(list_productName.get(position).toString());

            TextView mText_Price = (TextView) v.findViewById(R.id.text_Price);
            mText_Price.setText(list_productPrice.get(position).toString());


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
            View v = inflater.inflate(R.layout.list_order, parent, false);

            TextView mText_list_order_FirstName = (TextView) v.findViewById(R.id.text_list_order_FirstName);
            mText_list_order_FirstName.setText(list_FirstName.get(position).toString());

            TextView mText_list_order_LastName = (TextView) v.findViewById(R.id.text_list_order_LastName);
            mText_list_order_LastName.setText(list_LastName.get(position).toString());


            return v;
        }


    }

}
