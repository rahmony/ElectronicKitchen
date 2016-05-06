package net.rahmony.electronickitchen.ClassActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
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


        mEt_first_name.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!mEt_first_name.getText().toString().matches("^[a-zA-Z0-9]+$"))
                   mEt_first_name.setError("الرجاء ادخال اسم صحيح");
            }
        });

        mEt_last_name.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mEt_last_name.getText().toString().matches("^[a-zA-Z0-9]+$"))
                    mEt_last_name.setError("الرجاء ادخال اسم صحيح");
            }
        });

        mEt_address.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mEt_address.getText().toString().matches("^[a-zA-Z0-9]+$"))
                    mEt_address.setError("الرجاء ادخال عنوان صحيح");
            }
        });

        mEt_email.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mEt_email.getText().toString().matches("^[A-Za-z0-9](([_\\.\\-]?[a-zA-Z0-9]+)*)@([A-Za-z0-9]+)(([\\.\\-]?[a-zA-Z0-9]+)*)\\.([A-Za-z]{2,})$"))
                    mEt_email.setError("الرجاء إدخال إيميل صحيح");
            }
        });


        mEt_phone_number.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mEt_phone_number.getText().toString().matches("^(009665|9665|\\+9665|05|\\d)(5|0|3|6|4|9|1|8|7)([0-9]{7})$"))
                    mEt_phone_number.setError("يجب ان يكون رقم الجوال على الصيغة التالية 966xxxxxxxxx");
            }
        });

        mEt_password.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mEt_password.getText().toString().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}$"))
                    mEt_password.setError("يجب ان تكون كلمة المرور 8 خانات وتحتوي على حرف كبير و حرف صغير ورقم واحد على الاقل");
            }
        });



    }
    public void onClick(View v) {




        switch (v.getId()) {
            case R.id.btn_confirm:

                AlertDialog.Builder dialog=new AlertDialog.Builder(SignUpActivity.this);
                dialog.setTitle("شروط استخدام التطبيق ");
                dialog.setMessage("-------------------------\n ------------------------");
               // dialog.setIcon(R.drawable.ic_announcement_black_24dp);
                dialog.setNegativeButton("موافق",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {

                        if (mEt_first_name.getText().toString().isEmpty() || mEt_last_name.getText().toString().isEmpty()|| mEt_email.toString().isEmpty()
                                || mEt_phone_number.getText().toString().isEmpty() || mEt_password.getText().toString().isEmpty() || mEt_address.toString().isEmpty()) {

                            Toast.makeText(getBaseContext(),"جميع الحقول مهمه الرجاء التاكد من كتابة القيم بشكل صحيح " , Toast.LENGTH_LONG).show();

                        } else {
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
                        }
                    }

                });
                dialog.setPositiveButton("غير موافق", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }

                });
                dialog.show();

                break;
            case R.id.btn_cancel:

          finish();

                break;

            case R.id.btn_share_location :

                startActivity(new Intent(SignUpActivity.this , MapsActivity.class));


                break;
        }
    }





}

