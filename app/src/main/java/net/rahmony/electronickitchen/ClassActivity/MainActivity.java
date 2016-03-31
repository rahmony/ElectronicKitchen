package net.rahmony.electronickitchen.ClassActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import net.rahmony.electronickitchen.R;


public class MainActivity extends AppCompatActivity {

    private  ImageView mImage ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mImage= (ImageView) findViewById(R.id.image_logo);
        //Glide.with(this).load("http://androidadn.com/wp-content/uploads/sites/18/2013/02/android-musical2.jpg").into(mImage);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.trans_cross_1, R.anim.trans_cross_2);
            }
        });


    }
    @Override
      protected void onPause()
    {
        super.onPause();
        //closing transition animations
        overridePendingTransition(R.anim.trans_cross_2,R.anim.trans_cross_1);
    }

}
