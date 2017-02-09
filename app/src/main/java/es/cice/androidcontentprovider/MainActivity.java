package es.cice.androidcontentprovider;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import es.cice.androidcontentprovider.dataprovider.AndroidFlavorContract;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Cursor cursor=getContentResolver().query(AndroidFlavorContract.ANDROID_FLAVOR_CONTENT,new String[]{
                AndroidFlavorContract.FLAVORS_CONTENT.NAME_COLUMN,
                AndroidFlavorContract.FLAVORS_CONTENT.DESCRITION_COLUMN},
                null,null,null);
    }
}
