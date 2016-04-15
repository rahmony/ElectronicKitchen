package net.rahmony.electronickitchen.ClassActivity;

import android.content.Context;
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
import net.rahmony.electronickitchen.Data.Product;
import net.rahmony.electronickitchen.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ReadyOrders extends AppCompatActivity   implements AdapterView.OnItemClickListener {



    /****************************** Retrofit ************************************/
    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://rahmony.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    APIService apiService = retrofit.create(APIService.class);
    /***************************************************************************/

    // List View in Store To show product
    ListView mListView_ready_orders;

    //Array For getting productName and productPrice
    ArrayList list_productName = new ArrayList();
    ArrayList list_productQuantity = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_orders);


        mListView_ready_orders = (ListView) findViewById(R.id.listView_ready_orders);
        Call<List<Cart>> reg = apiService.trackingForDriver();

        reg.enqueue(new Callback<List<Cart>>() {
                        @Override
                        public void onResponse(Response<List<Cart>> response, Retrofit retrofit) {


                            ArrayList<Cart> arrayList = (ArrayList) response.body();

                            String[] productName = new String[arrayList.size()];
                            String[] productQuantity = new String[arrayList.size()];
                            for (int i = 0; i < arrayList.size(); i++) {
                                if (arrayList != null) {
                                    productName[i] = arrayList.get(i).getProductName();
                                    list_productName.add(i, arrayList.get(i).getProductName());
                                    productQuantity[i] = arrayList.get(i).getQuantity() + "";
                                    list_productQuantity.add(i, arrayList.get(i).getQuantity());
                                }
                            }
                            myAdapter arr = new myAdapter(getBaseContext(), android.R.layout.simple_list_item_1, productName);
                            mListView_ready_orders.setAdapter(arr);
                        }


                        @Override
                        public void onFailure(Throwable t) {
                            Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwble is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
        );

        mListView_ready_orders.setOnItemClickListener(this);
    }






    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }



    private class myAdapter extends ArrayAdapter<String> {

        public myAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.list_tracking_for_driver, parent, false);

            TextView mText_ProductName = (TextView) v.findViewById(R.id.trackingForDriver_text_product_name_val);
            mText_ProductName.setText(list_productName.get(position).toString());

            TextView mText_Quantity = (TextView) v.findViewById(R.id.trackingForDriver_text_product_quantity_val);
            mText_Quantity.setText(list_productQuantity.get(position).toString());


            return v;
        }


    }
}
