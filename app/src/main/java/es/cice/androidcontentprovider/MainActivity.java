package es.cice.androidcontentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import es.cice.androidcontentprovider.dataprovider.AndroidFlavorContract;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Flavor[] flavors = {
                new Flavor("Cupcake", "The first release of Android",
                        "http://1.bp.blogspot.com/-8-Gx8v-RbdY/ToOO8dpX6tI/AAAAAAAAJVo/ETAc42UcpCI/s1600/google+1.jpg"),
                new Flavor("Donut", "The world's information is at your fingertips – " +
                        "search the web, get driving directions... or just watch cat videos.",
                        "https://c1.staticflickr.com/9/8293/7729462544_bd1af317e9_b.jpg"),
                new Flavor("Eclair", "Make your home screen just how you want it. Arrange apps " +
                        "and widgets across multiple screens and in folders. Stunning live wallpapers " +
                        "respond to your touch.",
                        "https://androidayuda.com/app/uploads/2013/09/Android-Eclair-Donut.jpg"),
                new Flavor("Froyo", "Voice Typing lets you input text, and Voice Actions let " +
                        "you control your phone, just by speaking.",
                        "http://www.androidhoy.com/wp-content/uploads/android_froyo.jpg"),
                new Flavor("GingerBread", "New sensors make Android great for gaming - so " +
                        "you can touch, tap, tilt, and play away.",
                        "http://img.talkandroid.com/uploads/2010/11/Android-Gingerbread.jpg"),
                new Flavor("Honeycomb", "Optimized for tablets, this release opens up new " +
                        "horizons wherever you are.",
                        "http://www.infonucleo.com/wp-content/uploads/2011/02/honeycomb-android-illustration.jpg"),
                new Flavor("Ice Cream Sandwich", "Android comes of age with a new, refined design. " +
                        "Simple, beautiful and beyond smart.",
                        "http://cdn.redmondpie.com/wp-content/uploads/2011/05/Android-IceCreamSandwich.jpeg"),
                new Flavor("Jelly Bean", "Android is fast and smooth with buttery graphics. " +
                        "With Google Now, you get just the right information at the right time.",
                        "http://media.bestofmicro.com/I/E/377798/gallery/android-41-jelly-bean-logo-640_w_400.jpg"),
                new Flavor("KitKat", "Smart, simple, and truly yours. A more polished design, " +
                        "improved performance, and new features.",
                        "http://marketingland.com/wp-content/ml-loads/2013/09/Screen-Shot-2013-09-03-at-10.13.28-AM-300x437.png"),
                new Flavor("Lollipop", "A sweet new take on Android. Get the smarts of Android on" +
                        " screens big and small – with the right information at the right moment.",
                        "http://img.talkandroid.com/uploads/2014/10/android_lollipop_waving.png")};
        insertData(flavors);
        Cursor cursor=getContentResolver().query(AndroidFlavorContract.ANDROID_FLAVOR_CONTENT,
                new String[]{
                        AndroidFlavorContract.FLAVORS_CONTENT.PK_COLUMN,
                AndroidFlavorContract.FLAVORS_CONTENT.NAME_COLUMN,
                AndroidFlavorContract.FLAVORS_CONTENT.IMAGE_ID_COLUMN},
                null,null,null);
        SimpleCursorAdapter adapter=new SimpleCursorAdapter(this,R.layout.grid_cell,cursor,
                new String[]{AndroidFlavorContract.FLAVORS_CONTENT.NAME_COLUMN,
                        AndroidFlavorContract.FLAVORS_CONTENT.IMAGE_ID_COLUMN},
                new int[]{R.id.androidFlavorNameTV,R.id.androidFlavorImageIV},0);
        adapter.setViewBinder(new GridFlavorViewBinder());
        GridView flavorGV= (GridView) findViewById(R.id.flavors_grid);
        flavorGV.setAdapter(adapter);

    }

    private void insertData(Flavor[] flavors) {
        ContentValues[] values=new ContentValues[flavors.length];
        for(int i=0;i<flavors.length;i++){
            ContentValues cv=new ContentValues();
            cv.put(AndroidFlavorContract.FLAVORS_CONTENT.NAME_COLUMN,flavors[i].name);
            cv.put(AndroidFlavorContract.FLAVORS_CONTENT.DESCRITION_COLUMN,flavors[i].description);
            cv.put(AndroidFlavorContract.FLAVORS_CONTENT.IMAGE_ID_COLUMN,flavors[i].image);
            values[i]=cv;
        }
        getContentResolver().bulkInsert(AndroidFlavorContract.ANDROID_FLAVOR_CONTENT,values);
    }

    private class GridFlavorViewBinder implements SimpleCursorAdapter.ViewBinder {
        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            switch(view.getId()){
                case R.id.androidFlavorImageIV:
                    Picasso.with(getApplicationContext())
                            .load(cursor.getString(columnIndex))
                            .resize(100,100)
                            .centerCrop()
                            .into((ImageView)view);
                    return true;
                case R.id.androidFlavorNameTV:
                    ((TextView)view).setText(cursor.getString(columnIndex));
                    return true;
            }
            return false;
        }
    }
}
