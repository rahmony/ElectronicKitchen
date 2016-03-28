package net.rahmony.electronickitchen.ClassActivity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.rahmony.electronickitchen.APIService;
import net.rahmony.electronickitchen.Data.Cart;
import net.rahmony.electronickitchen.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class OrderDetails extends AppCompatActivity {

    /***************************** Retrofit ************************************/
    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://rahmony.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    APIService apiService = retrofit.create(APIService.class);

    /***************************************************************************/
    //List View For Showing Order Details.
    ListView mListView_order_details;

    //ArrayList For Order Details.
    ArrayList list_productName = new ArrayList();
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
        setContentView(R.layout.activity_order_details);

        Bundle extra = getIntent().getExtras();
        //Integer.toString(extra.getInt("Invoice_ID")).

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
        //implement List View.
        mListView_order_details= (ListView) findViewById(R.id.listView_order_details);

        //Get Extra Object.
        Bundle extra = getIntent().getExtras();

        //Set Invoice_ID From Extra.
        cart.setInvoice_ID(extra.getInt("Invoice_ID"));

        //Start Call.
        Call<List<Cart>> callOrderDetails = apiService.getOrderDetails(cart);
        callOrderDetails.enqueue(new Callback<List<Cart>>() {

            @Override
            public void onResponse(Response<List<Cart>> response, Retrofit retrofit) {

                //if Response From PHP is 200, For example: setStatus(200).
                if (response.message().equalsIgnoreCase("ok")) {

                    //Create ArrayList.
                    ArrayList<Cart> arrayList = (ArrayList) response.body();

                    //Create Array For each Column, productName, Price, Quantity.
                    String[] productName = new String[arrayList.size()];
                    int[] Price = new int[arrayList.size()];
                    int[] Quantity = new int[arrayList.size()];

                    //Get All Data From The Call And
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList != null) {
                            productName[i] = arrayList.get(i).getProductName();
                            list_productName.add(i, arrayList.get(i).getProductName());
                            Price[i] = arrayList.get(i).getPrice();
                            list_Price.add(i, arrayList.get(i).getPrice());
                            Quantity[i] = arrayList.get(i).getQuantity();
                            list_Quantity.add(i, arrayList.get(i).getQuantity());
                        }

                    }

                    myAdapter arr = new myAdapter(getBaseContext(), android.R.layout.simple_list_item_1, productName);
                    mListView_order_details.setAdapter(arr);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwble is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
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
     * ************************************ Adapter Class ************************************
     * ###################################################################################
     *
     */
    private class myAdapter extends ArrayAdapter<String> {

        public myAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
        }
        public myAdapter(Context context, int resource) {
            super(context, resource);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.list_order_details, parent, false);

            TextView mText_list_order_details_ProductName = (TextView) v.findViewById(R.id.text_list_order_details_ProductName);
            mText_list_order_details_ProductName.setText(list_productName.get(position).toString());

            TextView mText_list_order_details_Price = (TextView) v.findViewById(R.id.text_list_order_details_Price);
            mText_list_order_details_Price.setText(list_Price.get(position).toString());

            TextView mText_list_order_details_Quantity = (TextView) v.findViewById(R.id.text_list_order_details_Quantity);
            mText_list_order_details_Quantity.setText(list_Quantity.get(position).toString());


            return v;
        }


    }

}
