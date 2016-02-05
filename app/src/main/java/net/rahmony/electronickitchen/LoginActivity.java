package net.rahmony.electronickitchen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
// RaHmOnY

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



    }

    public void onLoginClick(View view){
        Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
        startActivity(intent);
    }

}
