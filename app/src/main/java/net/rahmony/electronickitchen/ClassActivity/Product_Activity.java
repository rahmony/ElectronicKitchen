package net.rahmony.electronickitchen.ClassActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.rahmony.electronickitchen.APIService;
import net.rahmony.electronickitchen.Data.Product;
import net.rahmony.electronickitchen.Data.Results;
import net.rahmony.electronickitchen.R;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Product_Activity extends AppCompatActivity {

    private EditText mEt_product_name , mEt_product_price , mEt_product_discription ;
    private Button mBtn_product_save ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_);

        mEt_product_name = (EditText) findViewById(R.id.et_product_name);
        mEt_product_price = (EditText) findViewById(R.id.et_product_price);
        mEt_product_discription = (EditText) findViewById(R.id.et_product_discription);

        mBtn_product_save = (Button) findViewById(R.id.btn_product_save);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rahmony.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mBtn_product_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIService apiService = retrofit.create(APIService.class);

                Bundle extras = getIntent().getExtras();

                Product product = new Product();
                product.setProductName(mEt_product_name.getText().toString());
                product.setPrice(Integer.parseInt(mEt_product_price.getText().toString()));
                product.setDescription(mEt_product_discription.getText().toString());
                product.setStore_ID(extras.getInt("Store_ID"));


                Call<Results> reg = apiService.addNewProduct(product);

                reg.enqueue(new Callback<Results>() {

                    @Override
                    public void onResponse(Response<Results> response, Retrofit retrofit) {
                        Toast.makeText(getBaseContext(), " product  successfully added ", Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwble is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }
                });

            }
        });


    }
}
