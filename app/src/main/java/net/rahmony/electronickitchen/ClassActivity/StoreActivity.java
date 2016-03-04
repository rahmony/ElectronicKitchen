package net.rahmony.electronickitchen.ClassActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.rahmony.electronickitchen.R;

import java.io.File;


public class StoreActivity extends AppCompatActivity implements TabHost.OnTabChangeListener{
    TabHost mTab;
    ImageButton mbtnImage_store_left;
    ImageView mImage_store;
    TextView mStoreName;
    static final int mCamRequest = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        mbtnImage_store_left = (ImageButton) findViewById(R.id.btnImage_store_left);
        mImage_store = (ImageView) findViewById(R.id.image_store);

        mTab=(TabHost)findViewById(R.id.tab_store);
        mTab.setup();
        TabHost.TabSpec spec=mTab.newTabSpec("tag1");
        spec.setIndicator("متجري");
        spec.setContent(R.id.tab_store_1);
        mTab.addTab(spec);

        spec=mTab.newTabSpec("tag2");
        spec.setIndicator("وصلكم طلب");
        spec.setContent(R.id.tab_store_2);
        mTab.addTab(spec);

        Bundle extras = getIntent().getExtras();
        mStoreName = (TextView) findViewById(R.id.text_store_name);
        mStoreName.setText(extras.getString("storeName"));
        System.out.print(extras.getString("storeName"));
    }

    public void onClicky(View view){


        Intent intent = new Intent(StoreActivity.this, Product_Activity.class);
        startActivity(intent);

    }


    public  void onClickme (View view ){

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "64861-200.png");

        Glide.with(this).load(file).into(mImage_store);


    }




    public  void onClick(View view){
        //the event for taking image
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //pass the file that comes from getFile() to the camera_intent object by using extra method.
        File file = getFile();
        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        //start the intent
        startActivityForResult(camera_intent, mCamRequest);

    }


    //this will make folder inside the mobile, the folder will contain the image
    private File getFile(){

        //check if folder not exist
        File folder = new File("sdcard/camera_app");
        if(!folder.exists()){

            folder.mkdir();
        }
        File imageFile = new File(folder,"cam_image.jpg");

        return imageFile;
    }

    //this method will take the capture pic from the folder camera_app and put in the activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String path = "sdcard/camera_app/cam_image.jpg";
        mImage_store.setImageDrawable(Drawable.createFromPath(path));

    }

    @Override
    public void onTabChanged(String tabId) {
        if(tabId.equals("tag1"))
        {
            Toast.makeText(this, "Tab 1 changed", Toast.LENGTH_LONG).show();
        }
        else if(tabId.equals("tag2"))
        {
            Toast.makeText(this,"Tab 2 changed",Toast.LENGTH_LONG).show();
        }

    }
    public void onEditClick(View view){
        Toast.makeText(getBaseContext(),"Hello Motherfucker",Toast.LENGTH_LONG).show();

    }

}
