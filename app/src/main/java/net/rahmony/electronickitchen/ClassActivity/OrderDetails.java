package net.rahmony.electronickitchen.ClassActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import net.rahmony.electronickitchen.APIService;
import net.rahmony.electronickitchen.R;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class OrderDetails extends AppCompatActivity {

    /***************************** Retrofit ************************************/
    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://rahmony.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    APIService apiService = retrofit.create(APIService.class);

    /***************************************************************************/

    /**
     *
     * ###################################################################################
     * ************************************ On Create ************************************
     * ###################################################################################
     *
     */
    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Bundle extra = getIntent().getExtras();
        //Integer.toString(extra.getInt("Invoice_ID"))

    }

    /**
     *
     * ###################################################################################
     * ************************************ On Resume ************************************
     * ###################################################################################
     *
     */
    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     *
     * ###################################################################################
     * ************************************ On Stop ************************************
     * ###################################################################################
     *
     */
    @Override
    protected void onStop() {
        super.onStop();
    }
}
