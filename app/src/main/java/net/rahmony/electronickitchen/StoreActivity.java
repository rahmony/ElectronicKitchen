package net.rahmony.electronickitchen;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;


public class StoreActivity extends AppCompatActivity implements TabHost.OnTabChangeListener{
    TabHost mTab;
    ImageButton mbtnImage_store_left;
    ImageView mImage_store;
    static final int mCamRequest = 1;
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

    }


    public  void onClick(View view){


        //the event for taking image
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //pass the file that comes from getFile() to the camera_intent object by using extra method.
        File file = getFile();
        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
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
