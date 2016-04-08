package net.rahmony.electronickitchen.ClassActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import net.rahmony.electronickitchen.R;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private View anim;
    private  ImageView mImage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume(){
        super.onResume();
        new CountDownTimer(800,800) {

            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                System.out.println("  gdgtdhbdhdhdhghfghghg "+Locale.getDefault().getLanguage());
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.trans_cross_1, R.anim.trans_cross_2);
                finish();
            }
        }.start();
    }
    
}
