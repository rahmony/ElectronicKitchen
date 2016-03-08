package net.rahmony.electronickitchen.ClassActivity;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.rahmony.electronickitchen.APIService;
import net.rahmony.electronickitchen.Data.Order;
import net.rahmony.electronickitchen.Data.Product;
import net.rahmony.electronickitchen.R;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


// TODO: 15/02/16 i will finish it later
public class CostumerProductActivity extends AppCompatActivity{
    TextView   mText_costumer_product_number;
    TextView mText_costumer_product_description;

    int quantity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_product);

        final Bundle extras = getIntent().getExtras();

        mText_costumer_product_description = (TextView) findViewById(R.id.text_costumer_product_description);
        mText_costumer_product_description.setText(extras.getString("ProductDescription"));

        mText_costumer_product_number = (TextView) findViewById(R.id.text_costumer_product_number);

}
    public void onClick(View view){

        switch(view.getId()){

            case R.id.text_costumer_product_plus:
                int up =Integer.parseInt(mText_costumer_product_number.getText().toString());
                mText_costumer_product_number.setText(Integer.toString(up + 1));
                quantity=Integer.parseInt(mText_costumer_product_number.getText().toString());

                break;
            case R.id.text_costumer_product_minus:

                final int down =Integer.parseInt(mText_costumer_product_number.getText().toString());
                if(down == 0){
                    Toast.makeText(getBaseContext(), "No! No Under 0!", Toast.LENGTH_SHORT).show();
                }else{
                mText_costumer_product_number.setText(Integer.toString(down - 1));
                quantity=Integer.parseInt(mText_costumer_product_number.getText().toString());
                }

                break;
            case R.id.btn_add_to_cart:

                Bundle extra = getIntent().getExtras();
                final Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://rahmony.net/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                APIService apiService = retrofit.create(APIService.class);

                Order order = new Order();
                order.setQuantity(Integer.parseInt(mText_costumer_product_number.getText().toString()));
                order.setID(extra.getInt("ID"));
                order.setProduct_ID(extra.getInt("ProductID"));

                Call<Order> reg = apiService.addOrder(order);
                reg.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Response<Order> response, Retrofit retrofit) {
                        if(response.message().equalsIgnoreCase("ok")){
                            if(quantity==0)
                            {
                                Toast.makeText(getBaseContext(), "Sorry !! plz add at least one ", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getBaseContext(), "Order Added!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
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

