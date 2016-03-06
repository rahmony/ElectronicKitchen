package net.rahmony.electronickitchen.ClassActivity;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.rahmony.electronickitchen.R;

/**
 * Created by Team on 15/02/16.
 */

// TODO: 15/02/16 i will finish it later
public class CostumerProductActivity extends AppCompatActivity{

    TextView mText_costumer_product_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_product);

        final Bundle extras = getIntent().getExtras();

        mText_costumer_product_description = (TextView) findViewById(R.id.text_costumer_product_description);
        mText_costumer_product_description.setText(extras.getString("ProductDescription"));



}
    public void onClick(View view){
        switch(view.getId()){

            case R.id.text_costumer_product_plus:

                break;
            case R.id.text_costumer_product_minus:

                break;
            case R.id.btn_add_to_cart:

                break;
            case R.id.btn_costumer_product_cancel:

                break;
        }
    }
}

