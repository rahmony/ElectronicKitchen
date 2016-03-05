package net.rahmony.electronickitchen.ClassActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import net.rahmony.electronickitchen.APIService;
import net.rahmony.electronickitchen.Data.LogInResult;
import net.rahmony.electronickitchen.Data.StoreResult;
import net.rahmony.electronickitchen.Data.User;
import net.rahmony.electronickitchen.R;
import net.rahmony.electronickitchen.Data.Store;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class MainPageActivity extends AppCompatActivity {

    private Button mBtn_customer_enter , mBtn_seller_enter , mBtn_driver_enter ;
    private TextView mText_userName ;










            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


            Bundle extras = getIntent().getExtras();


            mText_userName = (TextView)findViewById(R.id.text_userName);
            String welcome =  mText_userName.getText().toString();
            mText_userName.setText(extras.getString("userName") + "   " + welcome);

    }
    public void onClick(View view){
        switch (view.getId()){
            //----------------------------------------------------Costumer---------------------------------------------//
            case R.id.btn_customer_enter:



                User userCostumer = new User();
                final Bundle extrasCostumer = getIntent().getExtras();
                int Costumer_ID =  extrasCostumer.getInt("id");

                Retrofit retrofitCostumer = new Retrofit.Builder()
                        .baseUrl("http://rahmony.net/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                APIService apiServiceCostumer = retrofitCostumer.create(APIService.class);

                userCostumer.setID(Costumer_ID);
                Call<LogInResult> addNewCostumer = apiServiceCostumer.addNewCostumer(userCostumer);

                addNewCostumer.enqueue(new Callback<LogInResult>() {
                    @Override
                    public void onResponse(Response<LogInResult> response, Retrofit retrofit) {
                        if(response.message().equalsIgnoreCase("ok")){
                            Toast.makeText(getBaseContext() , "Seller Added!" , Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getBaseContext() , t.getMessage().toString() , Toast.LENGTH_LONG).show();
                    }
                });


                startActivity(new Intent(MainPageActivity.this, CostumerActivity.class));

                break;

//----------------------------------------------------Seller---------------------------------------------//
            case R.id.btn_seller_enter:

                final Store store = new Store();
                User userSeller = new User();
                final Bundle extrasSeller = getIntent().getExtras();
                int Seller_ID =  extrasSeller.getInt("id");

                Retrofit retrofitSeller = new Retrofit.Builder()
                        .baseUrl("http://rahmony.net/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                APIService apiServiceSeller = retrofitSeller.create(APIService.class);

                userSeller.setID(Seller_ID);
                Call<LogInResult> addNewSeller = apiServiceSeller.addNewSeller(userSeller);

                addNewSeller.enqueue(new Callback<LogInResult>() {
                    @Override
                    public void onResponse(Response<LogInResult> response, Retrofit retrofit) {
                        if(response.message().equalsIgnoreCase("ok")){
                            Toast.makeText(getBaseContext() , "Seller Added!" , Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getBaseContext() , t.getMessage().toString() , Toast.LENGTH_LONG).show();
                    }
                });


                store.setSeller_ID(Seller_ID);
                Call<StoreResult> reg = apiServiceSeller.hasStore(store);


                reg.enqueue(new Callback<StoreResult>() {
                    @Override
                    public void onResponse(Response<StoreResult> response, Retrofit retrofit) {


                        if (response.message().equalsIgnoreCase("ok")) {
                            Bundle extra = getIntent().getExtras();
                            Intent intent = new Intent(MainPageActivity.this ,StoreActivity.class);

                            store.setStoreID(response.body().getStoreID());
                            store.setStoreName(response.body().getStoreName());
                            store.setImage(response.body().getImage());
                            store.setStoreDescription(response.body().getStoreDescription());
                            store.setAvailable(response.body().getAvailable());
                            store.setSeller_ID(response.body().getSeller_ID());

                            intent.putExtra("Store_ID",store.getStoreID());
                            intent.putExtra("storeName", store.getStoreName());
                            intent.putExtra("image", store.getImage());
                            intent.putExtra("storeDescription", store.getStoreDescription());
                            intent.putExtra("available", store.getAvailable());
                            intent.putExtra("Seller_ID", store.getSeller_ID());


                            intent.putExtra("id",extra.getInt("id"));
                            intent.putExtra("userName", extra.getString("userName"));
                            intent.putExtra("email", extra.getString("email"));
                            intent.putExtra("phoneNumber", extra.getString("phoneNumber"));
                            intent.putExtra("address", extra.getString("address"));
                            intent.putExtra("lon", extra.getFloat("lon"));
                            intent.putExtra("lat", extra.getFloat("lat"));
                            startActivity(intent);


                        }
                       else if(response.message().equalsIgnoreCase("Unauthorized")){

                            Bundle extra = getIntent().getExtras();
                            Intent intent = new Intent(MainPageActivity.this ,CreateStoreActivity.class);

                            intent.putExtra("id",extra.getInt("id"));
                            intent.putExtra("userName", extra.getString("userName"));
                            intent.putExtra("email", extra.getString("email"));
                            intent.putExtra("phoneNumber", extra.getString("phoneNumber"));
                            intent.putExtra("address", extra.getString("address"));
                            intent.putExtra("lon", extra.getFloat("lon"));
                            intent.putExtra("lat", extra.getFloat("lat"));
                            startActivity(intent);


                        }

                        else Toast.makeText(getBaseContext() ,"No response" , Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getBaseContext() , t.getMessage().toString() , Toast.LENGTH_LONG).show();
                    }
                });



                break;
            case R.id.btn_driver_enter:

                break;


        }

    }



}
