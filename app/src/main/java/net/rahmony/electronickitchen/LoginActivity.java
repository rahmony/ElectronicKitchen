package net.rahmony.electronickitchen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    private TextView mText_login_sinup , mText_login_forgot_password;
    private Button mBtn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                startActivity(intent);
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
