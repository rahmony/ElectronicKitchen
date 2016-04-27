package net.rahmony.electronickitchen.ClassActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.rahmony.electronickitchen.APIService;
import net.rahmony.electronickitchen.Data.Cart;
import net.rahmony.electronickitchen.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class TrackingForDriverDetailsActivity extends AppCompatActivity {

    /***************************** Retrofit ************************************/
    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://rahmony.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    APIService apiService = retrofit.create(APIService.class);

    /***************************************************************************/
    //List View For Showing Accepted order Details.


    //Text Views: Store Name, Customer Name
    TextView mText_tracking_for_driver_details_store_name;
    TextView mText_tracking_for_driver_details_customer_name;
    TextView mText_tracking_for_driver_details_product_number;
    TextView mText_tracking_for_driver_details_price;

     //ArrayList For Order Details.
     ArrayList list_Price = new ArrayList();
     ArrayList list_Quantity = new ArrayList();

     //Cart Object.
     final Cart cart = new Cart();


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
        setContentView(R.layout.activity_tracking_for_driver_details);


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
         * ~~~~~~~~~~~~~~~~~~~~~~~~~~~ Showing Order Details ~~~~~~~~~~~~~~~~~~~~~~~~~~~~ *
         */

        //Get Extra Object.
        Bundle extra = getIntent().getExtras();

        //Set Invoice_ID From Extra.
        cart.setInvoice_ID(extra.getInt("Invoice_ID"));

        //Start Call.
        Call<List<Cart>> callOrderDetails = apiService.trackingForDriverDetails(cart);
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
                            Price[i] = arrayList.get(i).getPrice();
                            list_Price.add(i, arrayList.get(i).getPrice());
                            Quantity[i] = arrayList.get(i).getQuantity();
                            list_Quantity.add(i, arrayList.get(i).getQuantity());
                        }

                        TotalQuantity += Quantity[i];
                        TotalPrice += Price[i] * Quantity[i];

                    }


                    // initialization Text Views
                    mText_tracking_for_driver_details_store_name = (TextView) findViewById(R.id.text_tracking_for_driver_details_store_name);
                    mText_tracking_for_driver_details_customer_name = (TextView) findViewById(R.id.text_tracking_for_driver_details_customer_name);
                    mText_tracking_for_driver_details_product_number = (TextView) findViewById(R.id.text_tracking_for_driver_details_product_number);
                    mText_tracking_for_driver_details_price = (TextView) findViewById(R.id.text_tracking_for_driver_details_price);

                    //Set Texts
                    mText_tracking_for_driver_details_store_name.setText(cart.getStoreName());
                    mText_tracking_for_driver_details_customer_name.setText(cart.getFirstName() + " " + cart.getLastName());
                    mText_tracking_for_driver_details_product_number.setText(Integer.toString(TotalQuantity));
                    mText_tracking_for_driver_details_price.setText(Integer.toString(TotalPrice));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwable is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }




    /**
     *
     * ###################################################################################
     * ************************************ On Stop ************************************
     * ###################################################################################
     *
     */
    @Override
    protected void onStop() {
        super.onStop();
    }


    /**
     *
     * ###################################################################################
     * ************************************ On Click ************************************
     * ###################################################################################
     *
     */
    public void onClick(View v) throws IOException {

        //Get Extra Object.
        Bundle extra = getIntent().getExtras();

        //Switch For Clicks
        switch (v.getId()){

            //If Seller Clicked ready Order Button
            case R.id.btn_tracking_for_driver_details_accept_order:

                //Set Invoice_ID in Cart
                cart.setInvoice_ID(extra.getInt("Invoice_ID"));
                cart.setID(extra.getInt("ID"));

                //Start Call
                Call<Cart> callAcceptOrder  = apiService.acceptTrackingForDriverDetails(cart);
                callAcceptOrder.enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Response<Cart> response, Retrofit retrofit) {
                        if(response.message().equalsIgnoreCase("ok")){
                            Toast.makeText(getBaseContext(), "you are now Delivering this order!", Toast.LENGTH_LONG).show();
                            finish();
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

}
