package net.rahmony.electronickitchen.ClassActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.rahmony.electronickitchen.APIService;
import net.rahmony.electronickitchen.Data.Product;
import net.rahmony.electronickitchen.Data.Store;
import net.rahmony.electronickitchen.R;

import org.parceler.apache.commons.lang.builder.ToStringBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class StoreActivity extends AppCompatActivity implements TabHost.OnTabChangeListener , AdapterView.OnItemClickListener {

    ArrayList list_productName = new ArrayList();
    ArrayList list_productPrice = new ArrayList();

    ListView mListView_product;
    TabHost mTab;
    ImageButton mbtnImage_store_left;
    ImageView mImage_store;
    TextView mStoreName , mText_store_description;
    static final int mCamRequest = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        mbtnImage_store_left = (ImageButton) findViewById(R.id.btnImage_store_left);
        mImage_store = (ImageView) findViewById(R.id.image_store);

        mListView_product=(ListView)findViewById(R.id.listView_product);

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

        final Bundle extras = getIntent().getExtras();
        mStoreName = (TextView) findViewById(R.id.text_store_name);
        mStoreName.setText(extras.getString("StoreName"));
        mText_store_description = (TextView)findViewById(R.id.text_store_description);
        mText_store_description.setText(extras.get("StoreDescription").toString());


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rahmony.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);

        final Product product = new Product();
        product.setStore_ID(extras.getInt("Store_ID"));
        Call<List<Product>> reg = apiService.getMyProducts(product);

        reg.enqueue(new Callback<List<Product>>() {
                        @Override
                        public void onResponse(Response<List<Product>> response, Retrofit retrofit) {


                            ArrayList<Product> arrayList = (ArrayList) response.body();

                            String[] productName = new String[arrayList.size()];
                            String[] productPrice = new String[arrayList.size()];
                            for (int i = 0; i < arrayList.size(); i++) {
                                if (arrayList != null) {
                                    productName[i] = arrayList.get(i).getProductName();
                                    list_productName.add(i, arrayList.get(i).getProductName());
                                   productPrice[i] = arrayList.get(i).getPrice()+"";
                                   list_productPrice.add(i, arrayList.get(i).getPrice());

                                }


                            }

                            myAdapter arr = new myAdapter(getBaseContext(), android.R.layout.simple_list_item_1, productName);
                            mListView_product.setAdapter(arr);

                        }


                        @Override
                        public void onFailure(Throwable t) {
                            Toast.makeText(getBaseContext(), " Oops! An error occurred  + The Throwble is " + t.getMessage().toString(), Toast.LENGTH_LONG).show();

                        }
                    }
        );


    }



    public void onClick_addProduct(View view){

        Bundle extras = getIntent().getExtras();
        Intent intent = new Intent(StoreActivity.this, Product_Activity.class).putExtra("Store_ID",extras.getInt("Store_ID"));
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(getBaseContext(),list_productName.get(position).toString(),Toast.LENGTH_LONG).show();

    }
    private class myAdapter extends ArrayAdapter<String>
    {

        public myAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
        }


        @Override
        public View getView(int position , View convertView , ViewGroup parent)
        {

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View v =  inflater.inflate(R.layout.list_product, parent, false);

            TextView mText_ProductName = (TextView)v.findViewById(R.id.text_ProductName);
            mText_ProductName.setText(list_productName.get(position).toString());

            TextView mText_Price = (TextView)v.findViewById(R.id.text_Price);
            mText_Price.setText(list_productPrice.get(position).toString());



            return v;
        }


    }

}
