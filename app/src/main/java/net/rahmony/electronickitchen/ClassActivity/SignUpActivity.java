package net.rahmony.electronickitchen.ClassActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import net.rahmony.electronickitchen.APIService;
import net.rahmony.electronickitchen.R;
import net.rahmony.electronickitchen.Data.Results;
import net.rahmony.electronickitchen.Data.User;

import retrofit.*;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class SignUpActivity extends AppCompatActivity {

    private Button mBtn_confirm, mBtn_cancel;
    private EditText mEt_first_name , mEt_last_name , mEt_email , mEt_phone_number , mEt_password , mEt_address ;
    private RadioButton mRadioButton_madina, mRadioButton_jeddah, mRadioButton_makka;
    private RadioGroup mMyRadioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mBtn_confirm = (Button) findViewById(R.id.btn_confirm);
        mBtn_cancel = (Button) findViewById(R.id.btn_cancel);

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

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rahmony.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        mBtn_confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                APIService apiService = retrofit.create(APIService.class);

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
                if(selectedId == mRadioButton_makka.getId()) {
                   user.setCity("makka");
                } else if(selectedId == mRadioButton_jeddah.getId()) {
                    user.setCity("jeddah");
                } else {
                    user.setCity("madina");
                }


                Call<Results> reg = apiService.signup(user);

                reg.enqueue(new Callback<Results>() {

                    @Override
                    public void onResponse(Response<Results> response, Retrofit retrofit) {
                        Toast.makeText(getBaseContext(), " user successfully registered ", Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwble is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }
                });


            }
            });



        mBtn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }
}
