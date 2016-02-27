package net.rahmony.electronickitchen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

        APIService apiService = retrofit.create(APIService.class);
        Store store = new Store();
        store.setStoreName(mEt_store_create_store_name.getText().toString());
        store.setStoreDescription(mEt_store_create_store_description.getText().toString());


        Call<Results> reg =  apiService.createStore(store);

        reg.enqueue(new Callback<Results>(){


            @Override
            public void onResponse(Response<Results> response, Retrofit retrofit) {
                if(response.message().equalsIgnoreCase("ok")){
                Toast.makeText(getBaseContext(), " Store Created! ", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(CreateStoreActivity.this, StoreActivity.class));
                finish();}

                else{
                Toast.makeText(getBaseContext() , "Store Name is Already existed !!" , Toast.LENGTH_LONG).show();}

            }


            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwble is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        });

    }


}


