package net.rahmony.electronickitchen.ClassActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import net.alhazmy13.mediapicker.Image.ImagePicker;
import net.alhazmy13.mediapicker.Video.VideoPicker;
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
    ArrayList list_Invoice_ID = new ArrayList();

    TabHost mTab;
    ImageButton mbtnImage_store_left;
    ImageView mImage_store;
    TextView mStoreName, mText_store_description;

    //image Object
    ImageView mImage_Store;

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

        product.setStore_ID(extra.getInt("Store_ID"));
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
                                      int[] Invoice_ID = new int[arrayList.size()];
                                      for (int i = 0; i < arrayList.size(); i++) {
                                          if (arrayList != null) {
                                              FirstName[i] = arrayList.get(i).getFirstName();
                                              list_FirstName.add(i, arrayList.get(i).getFirstName());
                                              LastName[i] = arrayList.get(i).getLastName();
                                              list_LastName.add(i, arrayList.get(i).getLastName());
                                              Invoice_ID[i] = arrayList.get(i).getInvoice_ID();
                                              list_Invoice_ID.add(i, arrayList.get(i).getInvoice_ID());
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
        mListView_store_order.setOnItemClickListener(this);
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


    /**
     *
     * ###################################################################################
     * ************************************ On Click **************************************
     * ###################################################################################
     *
     */
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnImage_store_left:
                Bundle extra = getIntent().getExtras();
                Intent intent = new Intent(StoreActivity.this, Product_Activity.class).putExtra("Store_ID", extra.getInt("Store_ID"));
                startActivity(intent);
                break;
            case R.id.image_store:
                //initialized the image store
                mImage_Store = (ImageView) findViewById(R.id.image_store);
                ImagePicker mImagePicker = new ImagePicker.Builder(this)
                        .mode(ImagePicker.Mode.CAMERA)
                        .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                        .extension(ImagePicker.Extension.JPG)
                        .directory(ImagePicker.Directory.DEFAULT)
                        .build();
                break;
        }

    }


    //Media Picker Result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            String mPath = data.getStringExtra(ImagePicker.EXTRA_IMAGE_PATH);

            //Your Code
            mImage_store.setImageBitmap(BitmapFactory.decodeFile(mPath));
        }
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
        switch (parent.getId()){
            case  R.id.listView_product:
                    Toast.makeText(this, list_productName.get(position).toString(), Toast.LENGTH_LONG).show();
                break;
            case R.id.listView_store_order:
                Intent intent = new Intent(StoreActivity.this, OrderDetails.class);
                intent.putExtra("Invoice_ID", Integer.parseInt(list_Invoice_ID.get(position).toString()));
                startActivity(intent);
                break;
        }

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
