package net.rahmony.electronickitchen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import retrofit.*;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class SignUpActivity extends AppCompatActivity {

    private Button mBtn_confirm, mBtn_cancel;
    private EditText mEt_first_name , mEt_last_name , mEt_email , mEt_phone_number , mEt_password , mEt_address ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mBtn_confirm = (Button) findViewById(R.id.btn_confirm);
        mBtn_cancel = (Button) findViewById(R.id.btn_cancel);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rahmony.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        mBtn_confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                APIService apiService = retrofit.create(APIService.class);

                User user = new User();


                Call<Results> reg = apiService.signup(user);

                reg.enqueue(new Callback<Results>() {

                    @Override
                    public void onResponse(Response<Results> response, Retrofit retrofit) {
                        Toast.makeText(getBaseContext(), " user successfully registered ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwble is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }
                });


            }
            });


        // TODO:  complate the operation !!
        
        mBtn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }
}
