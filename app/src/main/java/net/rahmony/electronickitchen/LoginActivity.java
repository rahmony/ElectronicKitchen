package net.rahmony.electronickitchen;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class LoginActivity extends AppCompatActivity {

    private TextView mText_login_sinup, mText_login_forgot_password;
    private Button mBtn_login;
    private EditText mEt_email, mEt_password;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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


        mText_login_sinup = (TextView) findViewById(R.id.text_login_sinup);
        mText_login_sinup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


        mBtn_login = (Button) findViewById(R.id.btn_login);
        mBtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                APIService apiService = retrofit.create(APIService.class);
              final  User user = new User();
                user.setEmail(mEt_email.getText().toString());
                user.setPassword(mEt_password.getText().toString());

                Call<LogInResult> reg = apiService.login(user);


                reg.enqueue(new Callback<LogInResult>() {
                                @Override
                                public void onResponse(Response<LogInResult> response, Retrofit retrofit) {
                                    try {

                                        if (response.message().equalsIgnoreCase("ok")) {

                                            user.setID(response.body().getID());
                                            user.setFirstName(response.body().getFirstName());
                                            user.setLastName(response.body().getLastName());
                                            user.setEmail(response.body().getEmail());
                                            user.setPhoneNumber(response.body().getPhoneNumber());
                                            user.setCity(response.body().getCity());
                                            user.setAddress(response.body().getAddress());
                                            user.setLon(response.body().getLon());
                                            user.setLat(response.body().getLat());

                                            startActivity(new Intent(LoginActivity.this , MainPageActivity.class).putExtra("userName" , user.getFirstName() + " " + user.getLastName()));

                                        }

                                        else
                                            Toast.makeText(getBaseContext(), "Wrong Email or Password !!", Toast.LENGTH_LONG).show();


                                    } catch (Exception e) {
                                        Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Throwable t) {
                                    Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwble is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();

                                }
                            }
                );

            }


        });


        mText_login_forgot_password = (TextView) findViewById(R.id.text_login_forgot_password);
        mText_login_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Unavailable ", Toast.LENGTH_LONG).show();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://net.rahmony.electronickitchen/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://net.rahmony.electronickitchen/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
