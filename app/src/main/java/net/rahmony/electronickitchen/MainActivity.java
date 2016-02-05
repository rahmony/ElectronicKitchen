package net.rahmony.electronickitchen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

// RaHmOnY

public class MainActivity extends AppCompatActivity {

    private  ImageView mImage ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mImage = (ImageView) findViewById(R.id.image_logo);

    }
    public void onImageClick(View v) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
