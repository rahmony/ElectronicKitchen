package net.rahmony.electronickitchen.ClassActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import net.rahmony.electronickitchen.APIService;
import net.rahmony.electronickitchen.Data.Cart;
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

public class DriverOrdersActivity extends AppCompatActivity   implements AdapterView.OnItemClickListener {



    /****************************** Retrofit ************************************/
    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://rahmony.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    APIService apiService = retrofit.create(APIService.class);
    /***************************************************************************/

    //Activity key so i can kill it in another Activity
    public static Activity DriverOrdersActivity_key_kill;

    //Tabs
    TabHost mTab;

    // List View in Store To show product
    ListView mListView_ready_orders;
    
    //Text Views: Store Name, Customer Name
    TextView mText_driver_orders_store_name;
    TextView mText_driver_orders_customer_name;
    TextView mText_driver_orders_product_number;
    TextView mText_driver_orders_price;

    //ArrayList For Order Details.
    ArrayList list_Seller_ID = new ArrayList();
    ArrayList list_PhoneNumber_customer = new ArrayList();
    ArrayList list_Address_customer = new ArrayList();
    ArrayList list_Price = new ArrayList();
    ArrayList list_Quantity = new ArrayList();

    //Cart Object.
    final Cart cart = new Cart();


    //List of tracking info
    ArrayList list_tracking_storeName= new ArrayList();
    ArrayList list_tracking_Invoice_ID = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_orders);

        // declare the Key
        DriverOrdersActivity_key_kill = this;

        mTab = (TabHost) findViewById(R.id.tab_driver);
        mTab.setup();

        TabHost.TabSpec spec= mTab.newTabSpec("tag1");
        spec.setIndicator("الطلبات");
        spec.setContent(R.id.tab_driver_1);
        mTab.addTab(spec);

        spec = mTab.newTabSpec("tag2");
        spec.setIndicator("طلب تحت التوصيل");
        spec.setContent(R.id.tab_driver_2);
        mTab.addTab(spec);

    }


    /**
     *
     * ###################################################################################
     * ************************************ On Resume ************************************
     * ###################################################################################
     *
     */

    @Override
    protected void onResume() {
        super.onResume();

        /**
         * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Showing Orders ready to Deliver ~~~~~~~~~~~~~~~~~~~~~~~~~ *
         */
        //implementation for List View, list for tracking order .
        mListView_ready_orders = (ListView) findViewById(R.id.listView_ready_orders);


        Call<List<Cart>> reg = apiService.trackingForDriver(cart);
        reg.enqueue(new Callback<List<Cart>>() {

            @Override
            public void onResponse(Response<List<Cart>> response, Retrofit retrofit) {
                if (response.message().equalsIgnoreCase("ok")) {

                    ArrayList<Cart> arrayList = (ArrayList) response.body();
                    String[] storeName = new String[arrayList.size()];
                    int[] Invoice_ID_trackOrder = new int[arrayList.size()];
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList != null) {
                            storeName[i] = arrayList.get(i).getStoreName();
                            list_tracking_storeName.add(i, arrayList.get(i).getStoreName());
                            Invoice_ID_trackOrder[i] = arrayList.get(i).getInvoice_ID();
                            list_tracking_Invoice_ID.add(i, arrayList.get(i).getInvoice_ID());
                        }

                    }
                    myAdapter arr = new myAdapter(getBaseContext(), android.R.layout.simple_list_item_1, storeName);
                    mListView_ready_orders.setAdapter(arr);

                }
            }
            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwable is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        });
        mListView_ready_orders.setOnItemClickListener(this);



        /**
         * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Showing Delivering Order ~~~~~~~~~~~~~~~~~~~~~~~~~ *
         */

        //Get Extra Object.
        Bundle extra = getIntent().getExtras();

        //Set Invoice_ID From Extra.
        cart.setID(extra.getInt("ID"));

        //Start Call.
        Call<List<Cart>> callOrderDetails = apiService.trackingDeliveringOrder(cart);
        callOrderDetails.enqueue(new Callback<List<Cart>>() {

            @Override
            public void onResponse(Response<List<Cart>> response, Retrofit retrofit) {

                //if Response From PHP is 200, For example: setStatus(200).
                if (response.message().equalsIgnoreCase("ok")) {

                    //Total
                    int TotalQuantity = 0;
                    int TotalPrice = 0;

                    //Create ArrayList.
                    ArrayList<Cart> arrayList = (ArrayList) response.body();

                    //get the store name from the first index of the array, note: all the indexes will have the same name.
                    cart.setStoreName(arrayList.get(0).getStoreName());
                    //get the first/last name of the customer
                    cart.setFirstName(arrayList.get(0).getFirstName());
                    cart.setLastName(arrayList.get(0).getLastName());


                    //Create Array For each Column, productName, Price, Quantity.


                    int[] Price = new int[arrayList.size()];
                    int[] Quantity = new int[arrayList.size()];

                    //Get All Data From The Call
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList != null) {

                            list_Seller_ID.add(i, arrayList.get(i).getSeller_ID());
                            list_PhoneNumber_customer.add(i, arrayList.get(i).getPhoneNumber());
                            list_Address_customer.add(i, arrayList.get(i).getAddress());

                            Price[i] = arrayList.get(i).getPrice();
                            list_Price.add(i, arrayList.get(i).getPrice());

                            Quantity[i] = arrayList.get(i).getQuantity();
                            list_Quantity.add(i, arrayList.get(i).getQuantity());
                        }

                        TotalQuantity += Quantity[i];
                        TotalPrice += Price[i] * Quantity[i];

                    }


                    // initialization Text Views
                    mText_driver_orders_store_name = (TextView) findViewById(R.id.text_driver_orders_store_name);
                    mText_driver_orders_customer_name = (TextView) findViewById(R.id.text_driver_orders_customer_name);
                    mText_driver_orders_product_number = (TextView) findViewById(R.id.text_driver_orders_product_number);
                    mText_driver_orders_price = (TextView) findViewById(R.id.text_driver_orders_price);

                    //Set Texts
                    mText_driver_orders_store_name.setText(cart.getStoreName());
                    mText_driver_orders_customer_name.setText(cart.getFirstName() + " " + cart.getLastName());
                    mText_driver_orders_product_number.setText(Integer.toString(TotalQuantity));
                    mText_driver_orders_price.setText(Integer.toString(TotalPrice));
                } else {

                    LinearLayout mTab_driver_2 = (LinearLayout) findViewById(R.id.tab_driver_2);
                    mTab_driver_2.removeAllViews();
                    mTab_driver_2.setGravity(Gravity.CENTER);

                    //
                    TextView mNo_data = new TextView(DriverOrdersActivity.this);
                    mNo_data.setText(R.string.no_Date);
                    mNo_data.setGravity(Gravity.CENTER);
                    mNo_data.setTextSize(26);

                    mTab_driver_2.addView(mNo_data);

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwable is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
        
    }


    @Override
    protected void onStop() {
        super.onStop();


    }


    public void onClick(View v){

        switch (v.getId()){

            case R.id.text_driver_orders_customer_name:

                //Creating Dialog With it's Title
                AlertDialog.Builder dialog =new AlertDialog.Builder(DriverOrdersActivity.this);
                dialog.setTitle("بيانات الزبون");

                //Create Layout
                LinearLayout linearLayout = new LinearLayout(DriverOrdersActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                //Create Text Views for PhoneNumber
                final TextView mEt_input_phoneNumber = new TextView(DriverOrdersActivity.this);

                //Create Text Views for Address
                final TextView mEt_input_address = new TextView(DriverOrdersActivity.this);


                //Set Gravity
                linearLayout.setGravity((Gravity.CENTER));
                mEt_input_phoneNumber.setGravity(Gravity.CENTER);
                mEt_input_address.setGravity(Gravity.CENTER);

                //Set Text
                mEt_input_phoneNumber.setText("Phone Number is:  " + list_PhoneNumber_customer.get(0).toString());
                mEt_input_address.setText("Address is:  " + list_Address_customer.get(0).toString());


                //Add TextViews to The Layout
                linearLayout.addView(mEt_input_phoneNumber);
                linearLayout.addView(mEt_input_address);

                //Set Ok Button
                dialog.setPositiveButton("حسنــاً", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                //Set View by Adding The Layout
                dialog.setView(linearLayout);

                //Show The Dialog
                dialog.show();
                break;

            case R.id.text_driver_orders_store_name:


                cart.setID(Integer.parseInt(list_Seller_ID.get(0).toString()));

                Call<Cart> callOrderDetails = apiService.getSellerData(cart);
                callOrderDetails.enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Response<Cart> response, Retrofit retrofit) {

                        if (response.message().equalsIgnoreCase("ok")){

                           cart.setPhoneNumber(response.body().getPhoneNumber());
                           cart.setAddress(response.body().getAddress());

                            //Creating Dialog With it's Title
                            AlertDialog.Builder dialog =new AlertDialog.Builder(DriverOrdersActivity.this);
                            dialog.setTitle("بيانات الزبون");

                            //Create Layout
                            LinearLayout linearLayout = new LinearLayout(DriverOrdersActivity.this);
                            linearLayout.setOrientation(LinearLayout.VERTICAL);

                            //Create Text Views for PhoneNumber
                            final TextView mEt_input_phoneNumber = new TextView(DriverOrdersActivity.this);

                            //Create Text Views for Address
                            final TextView mEt_input_address = new TextView(DriverOrdersActivity.this);


                            //Set Gravity
                            linearLayout.setGravity((Gravity.CENTER));
                            mEt_input_phoneNumber.setGravity(Gravity.CENTER);
                            mEt_input_address.setGravity(Gravity.CENTER);

                            //Set Text
                            mEt_input_phoneNumber.setText("Phone Number is:  " + cart.getPhoneNumber());
                            mEt_input_address.setText("Address is:  " + cart.getAddress());


                            //Add TextViews to The Layout
                            linearLayout.addView(mEt_input_phoneNumber);
                            linearLayout.addView(mEt_input_address);

                            //Set Ok Button
                            dialog.setPositiveButton("حسنــاً", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            //Set View by Adding The Layout
                            dialog.setView(linearLayout);

                            //Show The Dialog
                            dialog.show();
                        }

                    }

                    @Override
                    public void onFailure(Throwable t) {

                        Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwable is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }


                });



                    break;

        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //Get Extra Object
        Bundle extra = getIntent().getExtras();

        final  Intent intent = new Intent(DriverOrdersActivity.this,TrackingForDriverDetailsActivity.class);
        intent.putExtra("Invoice_ID", Integer.parseInt(list_tracking_Invoice_ID.get(position).toString()));
        intent.putExtra("ID",extra.getInt("ID"));

        switch(parent.getId()){
            case R.id.listView_ready_orders:

               cart.setID(extra.getInt("ID"));

                Call<Cart> reg = apiService.isDriverDelivering(cart);
                reg.enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Response<Cart> response, Retrofit retrofit) {
                       if(response.message().equalsIgnoreCase("ok")){

                           Toast.makeText(getBaseContext(),"You can't Deliver more than one order at a time!",Toast.LENGTH_LONG).show();
                       }else {
                           startActivity(intent);
                       }

                    }
                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwable is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                            }
                );

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
            View v = inflater.inflate(R.layout.list_tracking_for_driver, parent, false);

            TextView mText_StoreName = (TextView) v.findViewById(R.id.text_list_tracking_driver_storeName);
            mText_StoreName.setText(list_tracking_storeName.get(position).toString());

            return v;
        }


    }
}
