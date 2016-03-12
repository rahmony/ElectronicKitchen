package net.rahmony.electronickitchen.ClassActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import net.rahmony.electronickitchen.APIService;
import net.rahmony.electronickitchen.Data.Cart;

import net.rahmony.electronickitchen.R;


import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


// TODO: 15/02/16 i will finish it later
public class CustomerProductActivity extends AppCompatActivity{
    TextView   mText_costumer_quantity_number;
    TextView mText_costumer_product_description;

    int quantity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_product);

        final Bundle extras = getIntent().getExtras();

        mText_costumer_product_description = (TextView) findViewById(R.id.text_costumer_product_description);
        mText_costumer_product_description.setText(extras.getString("ProductDescription"));

        mText_costumer_quantity_number = (TextView) findViewById(R.id.text_costumer_quantity_number);

}
    public void onClick(View view){

        switch(view.getId()){

            case R.id.text_costumer_product_plus:
                int up =Integer.parseInt(mText_costumer_quantity_number.getText().toString());
                mText_costumer_quantity_number.setText(Integer.toString(up + 1));
                quantity=Integer.parseInt(mText_costumer_quantity_number.getText().toString());

                break;
            case R.id.text_costumer_product_minus:

                final int down =Integer.parseInt(mText_costumer_quantity_number.getText().toString());
                if(down == 1){
                    Toast.makeText(getBaseContext(), "No! No Under 1!", Toast.LENGTH_SHORT).show();
                }else{
                    mText_costumer_quantity_number.setText(Integer.toString(down - 1));

                }

                break;
            case R.id.btn_add_to_cart:

                Bundle extra = getIntent().getExtras();
                final Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://rahmony.net/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                APIService apiService = retrofit.create(APIService.class);

                Cart cart = new Cart();
                cart.setQuantity(Integer.parseInt(mText_costumer_quantity_number.getText().toString()));
                cart.setID(extra.getInt("ID"));
                cart.setProduct_ID(extra.getInt("ProductID"));
                cart.setStoreID(extra.getInt("StoreID"));


                Call<Cart> reg = apiService.addCart(cart);
                reg.enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Response<Cart> response, Retrofit retrofit) {
                        if(response.message().equalsIgnoreCase("ok")){
                            Toast.makeText(getBaseContext(), "Order Added!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        if(response.message().equalsIgnoreCase("unauthorized")){

                            Toast.makeText(getBaseContext(), "not the same Store!", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                        Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwble is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case R.id.btn_costumer_product_cancel:

                finish();
                break;
        }
    }
}

