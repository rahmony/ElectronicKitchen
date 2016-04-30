package net.rahmony.electronickitchen.ClassActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.rahmony.electronickitchen.APIService;
import net.rahmony.electronickitchen.Data.Cart;
import net.rahmony.electronickitchen.Data.Product;
import net.rahmony.electronickitchen.R;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class CustomerTrackingDetailsActivity extends AppCompatActivity {

    /****************************** Retrofit ************************************/
    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://rahmony.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    APIService apiService = retrofit.create(APIService.class);
    /***************************************************************************/




    //Button received
    Button mBtn_customer_tracking_details_order_received;

    //imageView shows the tracking
    ImageView mImageView_customer_tracking_details;


    TextView mText_customer_tracking_details_current_status;
    final Cart cart = new Cart();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_tracking_details);

    }


    protected void onResume(){
        super.onResume();
        mImageView_customer_tracking_details  = (ImageView) findViewById(R.id.imageView_customer_tracking_details);


        Bundle extra = getIntent().getExtras();
        cart.setInvoice_ID(extra.getInt("Invoice_ID"));

        Call<Cart> reg = apiService.getInvoiceStatus(cart);

        reg.enqueue(new Callback<Cart>() {
                        @Override
                        public void onResponse(Response<Cart> response, Retrofit retrofit) {
                            if (response.message().equalsIgnoreCase("ok")) {

                                cart.setStatus(response.body().getStatus());

                                //Text showing the current status
                                mText_customer_tracking_details_current_status = (TextView) findViewById(R.id.text_customer_tracking_details_current_status);

                                switch (cart.getStatus().toString()) {
                                    case "Confirmed":
                                        mImageView_customer_tracking_details.setImageResource(R.drawable.ic_traker_1);
                                        mText_customer_tracking_details_current_status.setText("Confirmed");
                                        break;

                                    case "Accepted":
                                        mImageView_customer_tracking_details.setImageResource(R.drawable.ic_traker_2);
                                        mText_customer_tracking_details_current_status.setText("Accepted");
                                        break;

                                    case "ReadyForDelivery":
                                        mImageView_customer_tracking_details.setImageResource(R.drawable.ic_traker_3);
                                        mText_customer_tracking_details_current_status.setText("ReadyForDelivery");
                                        break;

                                    case "Delivering":
                                        mImageView_customer_tracking_details.setImageResource(R.drawable.ic_traker_4);
                                        mText_customer_tracking_details_current_status.setText("Delivering");


                                        mBtn_customer_tracking_details_order_received = (Button) findViewById(R.id.btn_customer_tracking_details_order_received);
                                        mBtn_customer_tracking_details_order_received.setVisibility(View.VISIBLE);
                                        mBtn_customer_tracking_details_order_received.setClickable(true);

                                        break;

                                    case "Delivered":
                                        mImageView_customer_tracking_details.setImageResource(R.drawable.ic_traker_5);
                                        mText_customer_tracking_details_current_status.setText("Delivered");

                                        break;

                                }


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
     * ************************************ On Click **************************************
     * ###################################################################################
     *
     */
    public void onClick(View v){
        Bundle extra = getIntent().getExtras();

        switch (v.getId()){
            case R.id.btn_customer_tracking_details_order_received:
                cart.setInvoice_ID(extra.getInt("Invoice_ID"));

                Call<Cart> reg = apiService.customerReceivedOrder(cart);

                reg.enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Response<Cart> response, Retrofit retrofit) {
                        if(response.message().equalsIgnoreCase("ok")){
                            Toast.makeText(getBaseContext(), "Your Order is Delivered To You", Toast.LENGTH_LONG).show();
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
