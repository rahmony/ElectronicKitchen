package net.rahmony.electronickitchen.ClassActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RestorePasswordActivity extends AppCompatActivity {


    private EditText mEt_email, mEt_password , mEt_phoneNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_password);

        mEt_email = (EditText) findViewById(R.id.et_current_email);
        mEt_phoneNumber = (EditText) findViewById(R.id.et_current_phoneNumber);
        mEt_password = (EditText) findViewById(R.id.et_new_password);


        mEt_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!mEt_email.getText().toString().matches("^[A-Za-z0-9](([_\\.\\-]?[a-zA-Z0-9]+)*)@([A-Za-z0-9]+)(([\\.\\-]?[a-zA-Z0-9]+)*)\\.([A-Za-z]{2,})$")){
                    mEt_email.setError("الرجاء إدخال إيميل صحيح");

                }
            }
        });
        mEt_phoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!mEt_phoneNumber.getText().toString().matches("([0-9]{12})")){
                    mEt_phoneNumber.setError("يجب ان يكون رقم الجوال على الصيغة التالية 966xxxxxxxxx");

                }
            }
        });
        mEt_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!mEt_password.getText().toString().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}$")) {
                    mEt_password.setError("يجب ان تكون كلمة المرور 8 خانات وتحتوي على حرف كبير و حرف صغير ورقم واحد على الاقل");
                }
            }
        });
    }


    //-----------------------------*************************************************************************************-------------------------//
    //--------------------------****************************************** On Click Method **************************************--------------------------//
//--------------------------**********************************************************************************************************-------------------------//
    public void onClick(View view) {


        switch (view.getId()){
            // -------------------------------------------------Case Button for restore password   if CLICKED!-------------------------------------//
            case R.id.btn_restore_password:



                if(!mEt_email.getText().toString().matches("^[A-Za-z0-9](([_\\.\\-]?[a-zA-Z0-9]+)*)@([A-Za-z0-9]+)(([\\.\\-]?[a-zA-Z0-9]+)*)\\.([A-Za-z]{2,})$")){
                    Toast.makeText(getBaseContext(), "الرجاء ادخال ايميل صحيح", Toast.LENGTH_SHORT).show();
                }else if(!mEt_password.getText().toString().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}$")){
                    Toast.makeText(getBaseContext(),"الرجاء ادخال كلمة مرور صحيحة",Toast.LENGTH_SHORT).show();
                }
                else if(!mEt_phoneNumber.getText().toString().matches("/^(009665|9665|\\+9665|05|\\d)(5|0|3|6|4|9|1|8|7)([0-9]{7})$/i"))
                {
                    Toast.makeText(getBaseContext(), "الرجاء ادخال رقم جوال صحيح", Toast.LENGTH_SHORT).show();
                }
                else{
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://rahmony.net/api/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    APIService apiService = retrofit.create(APIService.class);
                    final User user = new User();
                    user.setEmail(mEt_email.getText().toString());
                    user.setPhoneNumber(mEt_phoneNumber.getText().toString());
                    user.setPassword(mEt_password.getText().toString());

                    Call<LogInResult> reg = apiService.forgetPassword(user);


                    reg.enqueue(new Callback<LogInResult>() {
                                    @Override
                                    public void onResponse(Response<LogInResult> response, Retrofit retrofit) {
                                        try {

                                            if (response.message().equalsIgnoreCase("ok")) {

                                                Toast.makeText(getBaseContext(), "تم", Toast.LENGTH_LONG).show();
                                                finish();
                                            }

                                            else
                                                Toast.makeText(getBaseContext(), "الايميل او رقم الجوال غير صحيحين", Toast.LENGTH_LONG).show();


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

            //------------------------------------------case for btn restore password FINISHED!------------------------------------------------//

            // *****************************************case if text cancel CLICKED!**************************************//
            case R.id.btn_cancel_restore_password:

                startActivity(new Intent(RestorePasswordActivity.this, LoginActivity.class));

                break;
            //------------------------------------------case for text cancel FINISHED!------------------------------------//


        }




    }
    //---------------------------------------------------METHOD onClick FINISHED!---------------------------------------------------------//
    //-----------------------------***************************************************************************************-------------------------//
    //--------------------------***********************************************************************************************--------------------------//
//--------------------------**********************************************************************************************************-----------------------//

}
