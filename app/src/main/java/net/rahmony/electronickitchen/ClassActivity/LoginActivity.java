package net.rahmony.electronickitchen.ClassActivity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


import net.rahmony.electronickitchen.APIService;
import net.rahmony.electronickitchen.Data.LogInResult;
import net.rahmony.electronickitchen.Data.User;
import net.rahmony.electronickitchen.R;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class LoginActivity extends AppCompatActivity {

    private TextView mText_login_sinup, mText_login_forgot_password;
    private Button mBtn_login;
    private EditText mEt_email, mEt_password;
    private ImageView LoginImage;
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
        LoginImage = (ImageView) findViewById(R.id.image_logo);
        //Glide.with(this).load("http://mombasket.com/Mobile/images/mobile_login_icon.PNG").into(LoginImage);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

       //-----------------------------*************************************************************************************-------------------------//
    //--------------------------****************************************** On Click Method **************************************--------------------------//
//--------------------------**********************************************************************************************************-------------------------//
    public void onClick(View view) {


        switch (view.getId()){
      // -------------------------------------------------Case Button for Login if CLICKED!-------------------------------------//
            case R.id.btn_login:
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://rahmony.net/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

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

                                            Intent intent = new Intent(LoginActivity.this , MainPageActivity.class);
                                            intent.putExtra("ID",user.getID() );
                                            intent.putExtra("UserName" , user.getFirstName() + "  " + user.getLastName());
                                            intent.putExtra( "Email" , user.getEmail() );
                                            intent.putExtra("PhoneNumber" , user.getPhoneNumber() );
                                            intent.putExtra("Address" , user.getAddress());
                                            intent.putExtra( "Lon" , user.getLon() );
                                            intent.putExtra( "Lat" , user.getLat());

                                            startActivity(intent);
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
                break;

      //------------------------------------------case for btn login FINISHED!------------------------------------------------//

      // *****************************************case if text forgot password CLICKED!**************************************//
            case R.id.text_login_forgot_password:
                Toast.makeText(getBaseContext(), "Unavailable ", Toast.LENGTH_LONG).show();

                break;
      //------------------------------------------case for text forgot password  FINISHED!------------------------------------//

      // ***************************************** case if text signup ***************************************************** //
            case R.id.text_login_signup:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;
      //------------------------------------------case for text signup  FINISHED!--------------------------------------------//
        }




    }
           //---------------------------------------------------METHOD onClick FINISHED!---------------------------------------------------------//
       //-----------------------------***************************************************************************************-------------------------//
    //--------------------------***********************************************************************************************--------------------------//
//--------------------------**********************************************************************************************************-----------------------//



}