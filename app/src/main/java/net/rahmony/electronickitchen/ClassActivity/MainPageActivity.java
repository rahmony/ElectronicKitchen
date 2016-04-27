package net.rahmony.electronickitchen.ClassActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


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


    private TextView mText_welcome ;










            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


            Bundle extras = getIntent().getExtras();


                mText_welcome = (TextView)findViewById(R.id.text_welcome);
                String welcome =  mText_welcome.getText().toString();
                mText_welcome.setText(extras.getString("UserName") + "   " + welcome);

    }
    public void onClick(View view){
        switch (view.getId()){
            //----------------------------------------------------Costumer---------------------------------------------//
            case R.id.btn_customer_enter:




                User userCostumer = new User();
                final Bundle extrasCostumer = getIntent().getExtras();
                int Costumer_ID =  extrasCostumer.getInt("ID");

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
                        if (response.message().equalsIgnoreCase("ok")) {
                            Toast.makeText(getBaseContext(), "Costumer Added!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getBaseContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

                Bundle extra = getIntent().getExtras();
                Intent intent = new Intent(MainPageActivity.this, CustomerActivity.class);

                intent.putExtra("ID", extra.getInt("ID"));

                startActivity(intent);

                break;

//----------------------------------------------------Seller---------------------------------------------//
            case R.id.btn_seller_enter:

                final Store store = new Store();
                User userSeller = new User();
                final Bundle extrasSeller = getIntent().getExtras();
                int Seller_ID =  extrasSeller.getInt("ID");

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
                            Intent intent = new Intent(MainPageActivity.this, StoreActivity.class);

                            store.setStoreID(response.body().getStore_ID());
                            store.setStoreName(response.body().getStoreName());
                            store.setImage(response.body().getImage());
                            store.setStoreDescription(response.body().getStoreDescription());
                            store.setAvailable(response.body().getAvailable());
                            store.setSeller_ID(response.body().getSeller_ID());

                            intent.putExtra("Store_ID", store.getStoreID());
                            intent.putExtra("StoreName", store.getStoreName());
                            intent.putExtra("Image", store.getImage());
                            intent.putExtra("StoreDescription", store.getStoreDescription());
                            intent.putExtra("Available", store.getAvailable());
                            intent.putExtra("Seller_ID", store.getSeller_ID());


                            intent.putExtra("ID", extra.getInt("ID"));
                            intent.putExtra("UserName", extra.getString("UserName"));
                            intent.putExtra("Email", extra.getString("Email"));
                            intent.putExtra("PhoneNumber", extra.getString("PhoneNumber"));
                            intent.putExtra("Address", extra.getString("Address"));
                            intent.putExtra("Lon", extra.getFloat("Lon"));
                            intent.putExtra("Lat", extra.getFloat("Lat"));
                            startActivity(intent);


                        } else if (response.message().equalsIgnoreCase("Unauthorized")) {

                            Bundle extra = getIntent().getExtras();
                            Intent intent = new Intent(MainPageActivity.this, CreateStoreActivity.class);

                            intent.putExtra("ID", extra.getInt("ID"));
                            intent.putExtra("UserName", extra.getString("UserName"));
                            intent.putExtra("Email", extra.getString("Email"));
                            intent.putExtra("PhoneNumber", extra.getString("PhoneNumber"));
                            intent.putExtra("Address", extra.getString("Address"));
                            intent.putExtra("Lon", extra.getFloat("Lon"));
                            intent.putExtra("Lat", extra.getFloat("Lat"));
                            startActivity(intent);


                        } else
                            Toast.makeText(getBaseContext(), "No response", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getBaseContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });



                break;



//----------------------------------------------------Driver---------------------------------------------//
            case R.id.btn_driver_enter:

                User userDriver = new User();
                final Bundle extrasDriver = getIntent().getExtras();
                int Driver_ID =  extrasDriver.getInt("ID");

                Retrofit retrofitDriver = new Retrofit.Builder()
                        .baseUrl("http://rahmony.net/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                APIService apiServiceDriver= retrofitDriver.create(APIService.class);

                userDriver.setID(Driver_ID);
                Call<LogInResult> addNewDriver = apiServiceDriver.addNewDriver(userDriver);

                addNewDriver.enqueue(new Callback<LogInResult>() {
                    @Override
                    public void onResponse(Response<LogInResult> response, Retrofit retrofit) {
                        if(response.message().equalsIgnoreCase("ok")){
                            Toast.makeText(getBaseContext() , "Driver Added!" , Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getBaseContext() , t.getMessage().toString() , Toast.LENGTH_LONG).show();
                    }
                });


                startActivity(new Intent(MainPageActivity.this, DriverOrdersActivity.class).putExtra("ID",extrasDriver.getInt("ID")));

                break;




        }

    }



}
