package net.rahmony.electronickitchen.ClassActivity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gc.materialdesign.widgets.Dialog;

import net.rahmony.electronickitchen.APIService;
import net.rahmony.electronickitchen.R;
import net.rahmony.electronickitchen.Data.Results;
import net.rahmony.electronickitchen.Data.User;

import retrofit.*;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class SignUpActivity extends AppCompatActivity {


    private EditText mEt_first_name , mEt_last_name , mEt_email , mEt_phone_number , mEt_password , mEt_address ;
    private RadioButton mRadioButton_madina, mRadioButton_jeddah, mRadioButton_makka;
    private RadioGroup mMyRadioGroup;


    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://rahmony.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    APIService apiService = retrofit.create(APIService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mEt_first_name = (EditText) findViewById(R.id.et_first_name);
        mEt_last_name = (EditText) findViewById(R.id.et_last_name);
        mEt_email = (EditText) findViewById(R.id.et_email);
        mEt_phone_number = (EditText) findViewById(R.id.et_phone_number);
        mEt_password = (EditText) findViewById(R.id.et_password);
        mEt_address = (EditText) findViewById(R.id.et_address);

        mRadioButton_madina = (RadioButton) findViewById(R.id.radioButton_madina);
        mRadioButton_jeddah = (RadioButton) findViewById(R.id.radioButton_jeddah);
        mRadioButton_makka = (RadioButton) findViewById(R.id.radioButton_makka);
        mMyRadioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);

        mEt_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!mEt_email.getText().toString().matches("^[A-Za-z0-9](([_\\.\\-]?[a-zA-Z0-9]+)*)@([A-Za-z0-9]+)(([\\.\\-]?[a-zA-Z0-9]+)*)\\.([A-Za-z]{2,})$")){
                    mEt_email.setError("الرجاء إدخال إيميل صحيح");
                }
            }
        });
        mEt_phone_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!mEt_phone_number.getText().toString().matches("([0-9]{12})")){
                    mEt_phone_number.setError("يجب ان يكون رقم الجوال على الصيغة التالية 966xxxxxxxxx");
                }
            }
        });
        mEt_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!mEt_password.getText().toString().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}$")){
                    mEt_password.setError("يجب ان تكون كلمة المرور 8 خانات وتحتوي على حرف كبير و حرف صغير ورقم واحد على الاقل");
                }
            }
        });



    }
    public void onClick(View v) {




        switch (v.getId()) {
            case R.id.btn_confirm:


                User user = new User();
            user.setFirstName(mEt_first_name.getText().toString());
            user.setLastName(mEt_last_name.getText().toString());
            user.setEmail(mEt_email.getText().toString());
            user.setPhoneNumber(mEt_phone_number.getText().toString());
            user.setPassword(mEt_password.getText().toString());
            user.setAddress(mEt_address.getText().toString());

            user.setLat(0);
            user.setLon(0);

            int selectedId = mMyRadioGroup.getCheckedRadioButtonId();
            if (selectedId == mRadioButton_makka.getId()) {
                user.setCity("makka");
            } else if (selectedId == mRadioButton_jeddah.getId()) {
                user.setCity("jeddah");
            } else {
                user.setCity("madina");
            }


            Call<Results> reg = apiService.signup(user);

            reg.enqueue(new Callback<Results>() {

                @Override
                public void onResponse(Response<Results> response, Retrofit retrofit) {

                    Toast.makeText(getBaseContext(), " تم التسجيل بنجاح", Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwble is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            });
                break;
            case R.id.btn_cancel:

          finish();

                break;
        }
    }





}

