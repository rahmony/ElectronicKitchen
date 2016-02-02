package net.rahmony.electronickitchen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity {

    private Button mBtn_confirm, mBtn_cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mBtn_confirm = (Button) findViewById(R.id.btn_confirm);
        mBtn_cancel = (Button) findViewById(R.id.btn_cancel);

        mBtn_confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        mBtn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }
}
