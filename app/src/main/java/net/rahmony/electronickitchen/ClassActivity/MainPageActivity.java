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
import net.rahmony.electronickitchen.Data.StoreResult;
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
            case R.id.btn_customer_enter:
                startActivity(new Intent(MainPageActivity.this, CostumerActivity.class));

                break;


            case R.id.btn_seller_enter:
               final Bundle extras = getIntent().getExtras();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://rahmony.net/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIService apiService = retrofit.create(APIService.class);
                final Store store = new Store();



                int seller_ID =  extras.getInt("userID");
                store.setSeller_ID(seller_ID);

                Call<StoreResult> reg = apiService.hasStore(store);

                reg.enqueue(new Callback<StoreResult>() {
                    @Override
                    public void onResponse(Response<StoreResult> response, Retrofit retrofit) {


                        if (response.message().equalsIgnoreCase("ok")) {
                            store.setStoreID(response.body().getStoreID());
                            store.setStoreName(response.body().getStoreName());
                            store.setImage(response.body().getImage());
                            store.setStoreDescription(response.body().getStoreDescription());
                            store.setAvailable(response.body().getAvailable());
                            store.setSeller_ID(response.body().getSeller_ID());


                            startActivity(new Intent(MainPageActivity.this, StoreActivity.class).putExtra("storeName",store.getStoreName()));


                        }
                       else if(response.message().equalsIgnoreCase("Unauthorized")){

                            startActivity(new Intent(MainPageActivity.this, CreateStoreActivity.class));

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
