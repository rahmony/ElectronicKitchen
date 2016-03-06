package net.rahmony.electronickitchen.ClassActivity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import net.rahmony.electronickitchen.APIService;
import net.rahmony.electronickitchen.Data.Product;
import net.rahmony.electronickitchen.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ShowProductOfStroeActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener{

    ArrayList list_productOfName = new ArrayList();
    ArrayList list_productOfPrice = new ArrayList();

    ListView mListView_Of_product;
    TextView mStore_Of_Name , mText_store_Of_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product_of_stroe);


        final Bundle extras = getIntent().getExtras();
        mStore_Of_Name = (TextView) findViewById(R.id.text_store_Of_name);
        mStore_Of_Name.setText(extras.getString("StoreName"));
        mText_store_Of_description = (TextView)findViewById(R.id.text_store_Of_description);
        mText_store_Of_description.setText(extras.get("StoreDescription").toString());


        mListView_Of_product=(ListView)findViewById(R.id.listView_Of_product);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rahmony.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);

        final Product product = new Product();
        product.setStore_ID(extras.getInt("StoreID"));

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
                                    list_productOfName.add(i, arrayList.get(i).getProductName());
                                    productPrice[i] = arrayList.get(i).getPrice()+"";
                                    list_productOfPrice.add(i, arrayList.get(i).getPrice());

                                }


                            }

                            myAdapter arr = new myAdapter(getBaseContext(), android.R.layout.simple_list_item_1, productName);
                            mListView_Of_product.setAdapter(arr);

                        }


                        @Override
                        public void onFailure(Throwable t) {
                            Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwble is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();

                        }
                    }
        );


        mListView_Of_product.setOnItemClickListener(this);


    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



    }

private class myAdapter extends ArrayAdapter<String>
{

    public myAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position , View convertView , ViewGroup parent)
    {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View v =  inflater.inflate(R.layout.list_product, parent, false);

        TextView mText_ProductName = (TextView)v.findViewById(R.id.text_ProductName);
        mText_ProductName.setText(list_productOfName.get(position).toString());

        TextView mText_Price = (TextView)v.findViewById(R.id.text_Price);
        mText_Price.setText(list_productOfPrice.get(position).toString());



        return v;
    }


}

}
