package net.rahmony.electronickitchen.ClassActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


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


    private EditText mEt_email, mEt_password;
    private ImageView LoginImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEt_email = (EditText) findViewById(R.id.et_login_email);
        mEt_password = (EditText) findViewById(R.id.et_login_password);
        LoginImage = (ImageView) findViewById(R.id.image_logo);



    }

       //-----------------------------*************************************************************************************-------------------------//
    //--------------------------****************************************** On Click Method **************************************--------------------------//
//--------------------------**********************************************************************************************************-------------------------//
    public void onClick(View view) {


        switch (view.getId()){
      // -------------------------------------------------Case Button for Login if CLICKED!-------------------------------------//
            case R.id.btn_login:



                if(mEt_email.getText().toString().matches("")){
                    Toast.makeText(getBaseContext(),"Please fill the Email",Toast.LENGTH_SHORT).show();
                }else if(mEt_password.getText().toString().matches("")){
                    Toast.makeText(getBaseContext(),"Please fill the Password",Toast.LENGTH_SHORT).show();
                }
                else{
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
                );}
                break;

      //------------------------------------------case for btn login FINISHED!------------------------------------------------//

      // *****************************************case if text forgot password CLICKED!**************************************//
            case R.id.text_login_forgot_password:

                startActivity(new Intent(LoginActivity.this, RestorePasswordActivity.class));

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
