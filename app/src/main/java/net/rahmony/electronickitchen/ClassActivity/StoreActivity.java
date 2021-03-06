package net.rahmony.electronickitchen.ClassActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import net.alhazmy13.mediapicker.Image.ImagePicker;
import net.rahmony.electronickitchen.APIService;
import net.rahmony.electronickitchen.Data.Cart;
import net.rahmony.electronickitchen.Data.Product;
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

    //List View in Store To show accepted  Order
    ListView mListView_accepted_orders;

    // Product Object
    final Product product = new Product();

    //Cart Object
    final Cart cart = new Cart();
    final Cart cart_accepted_orders = new Cart();

    //Array For getting productName and productPrice
    ArrayList list_productName = new ArrayList();
    ArrayList list_productPrice = new ArrayList();
    ArrayList list_product_ID = new ArrayList();
    //Array For getting firstName and lastName
    ArrayList list_FirstName = new ArrayList();
    ArrayList list_LastName = new ArrayList();
    ArrayList list_Invoice_ID = new ArrayList();

    //Array For getting firstName and lastName
    ArrayList list_FirstName_accepted_orders = new ArrayList();
    ArrayList list_LastName_accepted_orders = new ArrayList();
    ArrayList list_Invoice_ID_accepted_orders = new ArrayList();


    TabHost mTab;
    ImageButton mBtnImage_store;
    ImageView mImage_store;
    TextView mText_store_name, mText_store_description;

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

        mBtnImage_store = (ImageButton) findViewById(R.id.btnImage_store);
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

        spec = mTab.newTabSpec("tag3");
        spec.setIndicator("الطلبات الحالية");
        spec.setContent(R.id.tab_store_3);
        mTab.addTab(spec);

        final Bundle extra = getIntent().getExtras();
        mText_store_name = (TextView) findViewById(R.id.text_store_name);
        mText_store_name.setText(extra.getString("StoreName"));
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
                                int[] productID = new int[arrayList.size()];

                                for (int i = 0; i < arrayList.size(); i++) {
                                    if (arrayList != null) {
                                        productName[i] = arrayList.get(i).getProductName();
                                        list_productName.add(i, arrayList.get(i).getProductName());
                                        productPrice[i] = arrayList.get(i).getPrice() + "";
                                        list_productPrice.add(i, arrayList.get(i).getPrice());
                                        productID[i] = arrayList.get(i).getProduct_ID();
                                        list_product_ID.add(i, arrayList.get(i).getProduct_ID());
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
        cart_accepted_orders.setID(extra.getInt("ID"));
        //Store_ID For The User
        cart_accepted_orders.setStore_ID(extra.getInt("Store_ID"));

        //Start Call
        Call<List<Cart>> callOrder = apiService.getOrder(cart_accepted_orders);
        callOrder.enqueue(new Callback<List<Cart>>() {
                              @Override
                              public void onResponse(Response<List<Cart>> response, Retrofit retrofit) {


                                  if (response.message().equalsIgnoreCase("ok")) {
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




        /**
         * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ tracking order ~~~~~~~~~~~~~~~~~~~~~~~~~ *
         */



        //implementation for List View, list for tracking order .
        mListView_accepted_orders = (ListView) findViewById(R.id.listView_accepted_orders);

        //ID For The User
        cart_accepted_orders.setID(extra.getInt("ID"));
        //Store_ID For The User
        cart_accepted_orders.setStore_ID(extra.getInt("Store_ID"));

        Call<List<Cart>> trackingOrder = apiService.acceptedOrder(cart_accepted_orders);
        trackingOrder.enqueue(new Callback<List<Cart>>() {

            @Override
            public void onResponse(Response<List<Cart>> response, Retrofit retrofit) {
                if (response.message().equalsIgnoreCase("unauthorized")) {

                    /*mTextView_text_accepted_ordersart_no_data.setVisibility(View.VISIBLE);*/

                } else {
                    ArrayList<Cart> arrayList = (ArrayList) response.body();
                    String[] FirstName_accepted_orders = new String[arrayList.size()];
                    String[] LastName_accepted_orders = new String[arrayList.size()];
                    int[] Invoice_ID_accepted_orders = new int[arrayList.size()];
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList != null) {
                            FirstName_accepted_orders[i] = arrayList.get(i).getFirstName();
                            list_FirstName_accepted_orders.add(i, arrayList.get(i).getFirstName());
                            LastName_accepted_orders[i] = arrayList.get(i).getLastName();
                            list_LastName_accepted_orders.add(i, arrayList.get(i).getLastName());
                            Invoice_ID_accepted_orders[i] = arrayList.get(i).getInvoice_ID();
                            list_Invoice_ID_accepted_orders.add(i, arrayList.get(i).getInvoice_ID());


                        }

                    }
                    myTheardAdapter arr = new myTheardAdapter(getBaseContext(), android.R.layout.simple_list_item_1, FirstName_accepted_orders);
                    mListView_accepted_orders.setAdapter(arr);

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwble is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });



        mListView_accepted_orders.setOnItemClickListener(this);
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
        mListView_store_order = null;
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
            case R.id.btnImage_store:
                final Bundle extra = getIntent().getExtras();
                Intent intent = new Intent(StoreActivity.this, Product_Activity.class).putExtra("Store_ID", extra.getInt("Store_ID"));
                startActivity(intent);
                break;
            case R.id.image_store:
                new ImagePicker.Builder(this)
                        .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                        .extension(ImagePicker.Extension.PNG)
                        .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                        .directory(ImagePicker.Directory.DEFAULT)
                        .build();

                break;

            case R.id.text_store_name:
                AlertDialog.Builder dialog=new AlertDialog.Builder(StoreActivity.this);
                dialog.setTitle("أدخل اسم المتجر الجديد");
                final EditText mEt_input = new EditText(StoreActivity.this);

                mEt_input.setText(mText_store_name.getText());
                mEt_input.setGravity(Gravity.CENTER);


                LinearLayout.LayoutParams layoutParams = new  LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                mEt_input.setLayoutParams(layoutParams);
                dialog.setIcon(R.drawable.shop);

                dialog.setNegativeButton("تعديل", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int which) {

                        Bundle bundle = getIntent().getExtras();
                        Store store = new Store();
                        store.setStoreID(bundle.getInt("Store_ID"));
                        store.setStoreName(mEt_input.getText().toString());
                        Call<StoreResult> reg = apiService.changeStoreName(store);
                        reg.enqueue(new Callback<StoreResult>() {
                            @Override
                            public void onResponse(Response<StoreResult> response, Retrofit retrofit) {

                                dialog.cancel();
                                Toast.makeText(getBaseContext(),"تم تعديل اسم المتجر بنجاح" ,Toast.LENGTH_LONG).show();
                                finish();





                            }

                            @Override
                            public void onFailure(Throwable t) {

                                Toast.makeText(getBaseContext(),t.getMessage().toString(),Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                });
                dialog.setPositiveButton("الغاء", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }

                });
                dialog.setView(mEt_input);
                dialog.show();
                break;

            case R.id.image_edit:
                Toast.makeText(getBaseContext(),"لتعديل البيانات فقط اضغط عليها وادخل القيمة الجديدة",Toast.LENGTH_LONG).show();

                break;

            case R.id.text_store_description:

                AlertDialog.Builder dialog_description =new AlertDialog.Builder(StoreActivity.this);
                dialog_description.setTitle("أدخل الوصف الجديد");
                
                final EditText mEt_input_description = new EditText(StoreActivity.this);

                mEt_input_description.setGravity(Gravity.CENTER);

                LinearLayout.LayoutParams layoutParams_description = new  LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                mEt_input_description.setText(mText_store_description.getText());
                
                mEt_input_description.setLayoutParams(layoutParams_description);
                
                dialog_description.setIcon(R.drawable.shop);

                dialog_description.setNegativeButton("تعديل", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int which) {

                        Bundle bundle = getIntent().getExtras();
                        Store store = new Store();
                        store.setStoreID(bundle.getInt("Store_ID"));
                        
                        store.setStoreDescription(mEt_input_description.getText().toString());
                        
                        Call<StoreResult> reg = apiService.changeStoreDescription(store);
                        reg.enqueue(new Callback<StoreResult>() {
                            @Override
                            public void onResponse(Response<StoreResult> response, Retrofit retrofit) {

                                dialog.cancel();
                                Toast.makeText(getBaseContext(),"تم تعديل وصف المتجر بنجاح" ,Toast.LENGTH_LONG).show();
                                
                            }
                            @Override
                            public void onFailure(Throwable t) {

                                Toast.makeText(getBaseContext(),t.getMessage().toString(),Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                });
                dialog_description.setPositiveButton("الغاء", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }

                });
                dialog_description.setView(mEt_input_description);
                dialog_description.show();
                
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            String mPath = data.getStringExtra(ImagePicker.EXTRA_IMAGE_PATH);

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
        final int mPosition = position;
        switch (parent.getId()){
            case  R.id.listView_product:
                AlertDialog.Builder dialog=new AlertDialog.Builder(StoreActivity.this);
                dialog.setTitle("أدخل اسم المنتج وسعره الجديد");

                final EditText mEt_input_productName = new EditText(StoreActivity.this);
                final EditText mEt_input_price = new EditText(StoreActivity.this);

                mEt_input_productName.setGravity(Gravity.CENTER);
                mEt_input_price.setGravity(Gravity.CENTER);




                LinearLayout linearLayout = new LinearLayout(StoreActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(mEt_input_productName);
                linearLayout.addView(mEt_input_price);



                mEt_input_productName.setText(list_productName.get(mPosition).toString());
                mEt_input_price.setText(list_productPrice.get(mPosition).toString());

                dialog.setIcon(R.drawable.foodicon1);

                dialog.setNegativeButton("تعديل", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int which) {


                        Product product = new Product();

                        product.setProduct_ID(Integer.parseInt(list_product_ID.get(mPosition).toString()));
                        product.setProductName(mEt_input_productName.getText().toString());
                        product.setPrice(Integer.parseInt(mEt_input_price.getText().toString()));

                        Call<Product> reg = apiService.changeProductName(product);
                        reg.enqueue(new Callback<Product>() {
                            @Override
                            public void onResponse(Response<Product> response, Retrofit retrofit) {

                                dialog.cancel();
                                Toast.makeText(getBaseContext(), "تم تعديل اسم المنتج", Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onFailure(Throwable t) {

                                Toast.makeText(getBaseContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                });
                dialog.setPositiveButton("الغاء", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }

                });


                dialog.setView(linearLayout);


                dialog.show();


                break;
            case R.id.listView_store_order:
                Intent intent_1 = new Intent(StoreActivity.this, OrderDetails.class);
                intent_1.putExtra("Invoice_ID", Integer.parseInt(list_Invoice_ID.get(position).toString()));
                startActivity(intent_1);
                break;
            case R.id.listView_accepted_orders:
                Intent intent_2 = new Intent(StoreActivity.this, acceptedOrderDetails.class);
                intent_2.putExtra("Invoice_ID", Integer.parseInt(list_Invoice_ID_accepted_orders.get(position).toString()));
                startActivity(intent_2);
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


    private class myTheardAdapter extends ArrayAdapter<String> {

        public myTheardAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.list_accepted_order, parent, false);

            TextView mText_list_order_FirstName = (TextView) v.findViewById(R.id.text_list_order_FirstName_accepted_orders);
            mText_list_order_FirstName.setText(list_FirstName_accepted_orders.get(position).toString());

            TextView mText_list_order_LastName = (TextView) v.findViewById(R.id.text_list_order_LastName_accepted_orders);
            mText_list_order_LastName.setText(list_LastName_accepted_orders.get(position).toString());


            return v;
        }


    }
}
