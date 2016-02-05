package net.rahmony.electronickitchen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


    }
    public void onCostumerClick(View view){
        Intent intent = new Intent(MainPageActivity.this, CostumerActivity.class);
        startActivity(intent);

    }
    public void onSellerClick(View view){
        Intent intent = new Intent(MainPageActivity.this, StoreActivity.class);
        startActivity(intent);

    }





}
