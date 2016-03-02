package net.rahmony.electronickitchen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainPageActivity extends AppCompatActivity {

    private Button mBtn_customer_enter , mBtn_seller_enter , mBtn_driver_enter ;
    private TextView mText_userName ;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


            Bundle extras = getIntent().getExtras();

            mText_userName = (TextView)findViewById(R.id.text_userName);
           String welcome =  mText_userName.getText().toString();
            mText_userName.setText(extras.getString("userName") + "   " + welcome);

        mBtn_customer_enter = (Button) findViewById(R.id.btn_customer_enter);
        mBtn_customer_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, CostumerActivity.class);
                startActivity(intent);

            }
        });

        mBtn_seller_enter = (Button) findViewById(R.id.btn_seller_enter);
        mBtn_seller_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, CreateStoreActivity.class);
                startActivity(intent);
            }
        });



    }



}
