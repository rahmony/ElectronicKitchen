package net.rahmony.electronickitchen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class LoginActivity extends AppCompatActivity {

    private TextView mText_login_sinup , mText_login_forgot_password;
    private Button mBtn_login;
    private EditText  mEt_email , mEt_password ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEt_email = (EditText) findViewById(R.id.et_login_email);
        mEt_password = (EditText) findViewById(R.id.et_login_password);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rahmony.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        mText_login_sinup = (TextView)findViewById(R.id.text_login_sinup);
        mText_login_sinup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        mBtn_login = (Button)findViewById(R.id.btn_login);
        mBtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                APIService apiService = retrofit.create(APIService.class);
                Call<Results > reg = apiService.login(mEt_email.getText().toString() , mEt_password.getText().toString());

                reg.enqueue(new Callback<Results>() {

                    @Override
                    public void onResponse(Response<Results> response, Retrofit retrofit) {
                        if (response.body().getMessage().equalsIgnoreCase("LogIn successfull")) {
                            startActivity(new Intent(LoginActivity.this, MainPageActivity.class));

                        } else {
                            Toast.makeText(getBaseContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwble is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }

                });
    }
});

        mText_login_forgot_password = (TextView)findViewById(R.id.text_login_forgot_password);
        mText_login_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext() , "Unavailable " , Toast.LENGTH_LONG).show();
            }
        });
    }
}
