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

public class DriverOrdersActivity extends AppCompatActivity   implements AdapterView.OnItemClickListener {



    /****************************** Retrofit ************************************/
    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://rahmony.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    APIService apiService = retrofit.create(APIService.class);
    /***************************************************************************/

    // List View in Store To show product
    ListView mListView_ready_orders;

    //Cart Object
    final  Cart cart = new Cart();


    //List of tracking info
    ArrayList list_tracking_storeName= new ArrayList();
    ArrayList list_tracking_Invoice_ID = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_orders);





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

                           Toast.makeText(getBaseContext(),"لا تستطيع توصيل اكثر من طلب في نفس الوقت",Toast.LENGTH_LONG).show();
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
