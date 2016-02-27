package net.rahmony.electronickitchen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainPageActivity extends AppCompatActivity {

    private Button mBtn_customer_enter , mBtn_seller_enter , mBtn_driver_enter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


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
