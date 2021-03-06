package net.rahmony.electronickitchen.ClassActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.rahmony.electronickitchen.APIService;
import net.rahmony.electronickitchen.Data.Results;
import net.rahmony.electronickitchen.Data.Store;
import net.rahmony.electronickitchen.Data.StoreResult;
import net.rahmony.electronickitchen.R;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class CreateStoreActivity extends AppCompatActivity {


    private EditText mEt_store_create_store_name,mEt_store_create_store_description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_store);



    }

    
    public void OnClick(View view){
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rahmony.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mEt_store_create_store_name= (EditText) findViewById(R.id.et_store_create_store_name);
        mEt_store_create_store_description= (EditText) findViewById(R.id.et_store_create_store_description);

        final Bundle extra = getIntent().getExtras();

        APIService apiService = retrofit.create(APIService.class);

        final Store store = new Store();

        store.setStoreName(mEt_store_create_store_name.getText().toString());
        store.setStoreDescription(mEt_store_create_store_description.getText().toString());
        store.setSeller_ID(extra.getInt("ID"));




        Call<StoreResult> reg =  apiService.createStore(store);

        reg.enqueue(new Callback<StoreResult>(){


            @Override
            public void onResponse(Response<StoreResult> response, Retrofit retrofit) {
                if (response.message().equalsIgnoreCase("ok")) {
                    Toast.makeText(getBaseContext(), " تم انشاء المتجر بنجاح ", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(CreateStoreActivity.this, StoreActivity.class);

                    store.setStoreID(response.body().getStore_ID());
                    store.setStoreName(response.body().getStoreName());
                    store.setImage(response.body().getImage());
                    store.setStoreDescription(response.body().getStoreDescription());
                    store.setAvailable(response.body().getAvailable());
                    store.setSeller_ID(response.body().getSeller_ID());

                    intent.putExtra("Store_ID",store.getStoreID());
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
                    finish();
                }if(response.message().equalsIgnoreCase("unauthorized")){
                    Toast.makeText(getBaseContext(), "اسم المتجر موجود سابقا", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwble is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        });

    }


}


