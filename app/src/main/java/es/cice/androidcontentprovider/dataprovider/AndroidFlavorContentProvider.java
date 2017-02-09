package es.cice.androidcontentprovider.dataprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

public class AndroidFlavorContentProvider extends ContentProvider {
    private SQLiteDatabase db;
    private static UriMatcher matcher;
    private static final int FLAVOR_CONTENT_URI_VALUE=100;
    private static final int FLAVOR_ITEM_URI_VALUE=200;

    public static void buildMatcher(){
        matcher=new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(AndroidFlavorContract.AUTHORITY,
                AndroidFlavorContract.CONTENT_URI_SEGMENT,FLAVOR_CONTENT_URI_VALUE);

        matcher.addURI(AndroidFlavorContract.AUTHORITY,
                AndroidFlavorContract.ITEM_URI_SEGMENT + "/#",FLAVOR_ITEM_URI_VALUE);
    }


    public AndroidFlavorContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        switch(matcher.match(uri)){

            case FLAVOR_CONTENT_URI_VALUE:
                return db.delete(AndroidFlavorOpenHelper.ANDROID_FLAVORS_TABLE,
                        selection,selectionArgs);

            case FLAVOR_ITEM_URI_VALUE:
                int id=Integer.parseInt(uri.getLastPathSegment());
                selection =AndroidFlavorContract.FLAVORS_CONTENT.PK_COLUMN +"=" + id;
                return db.delete(AndroidFlavorOpenHelper.ANDROID_FLAVORS_TABLE,
                        selection,null);

            default:
                throw new UnsupportedOperationException(uri + " no reconocida");
        }
    }

    @Override
    public String getType(Uri uri) {
        switch(matcher.match(uri)){

            case FLAVOR_CONTENT_URI_VALUE:
                return AndroidFlavorContract.ANDROID_FLAVOR_DIR_MIME;

            case FLAVOR_ITEM_URI_VALUE:
                return AndroidFlavorContract.ANDROID_FLAVOR_ITEM_MIME;

            default:
                throw new UnsupportedOperationException(uri + " no reconocida");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        switch(matcher.match(uri)){
            case FLAVOR_CONTENT_URI_VALUE:
                if(values.containsKey(AndroidFlavorContract.FLAVORS_CONTENT.PK_COLUMN))
                    values.remove(AndroidFlavorContract.FLAVORS_CONTENT.PK_COLUMN);
                long id=db.insert(AndroidFlavorOpenHelper.ANDROID_FLAVORS_TABLE,null,values);
                return AndroidFlavorContract.ANDROID_FLAVOR_ITEM.buildUpon().appendPath("" + id).build();
            default:
                throw new UnsupportedOperationException( uri  + "no valida");
        }

    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int numInsertedRegs=0;
        switch(matcher.match(uri)){
            case FLAVOR_CONTENT_URI_VALUE:
                db.beginTransaction();
                for(ContentValues cv:values){
                    long rowId=db.insert(AndroidFlavorOpenHelper.ANDROID_FLAVORS_TABLE,null,cv);
                    if(rowId!=-1)
                        numInsertedRegs++;
                }
                db.setTransactionSuccessful();
                db.endTransaction();
                return numInsertedRegs;
            default:
                throw new UnsupportedOperationException( uri  + "no valida");
        }
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        AndroidFlavorOpenHelper helper=new AndroidFlavorOpenHelper(getContext(),3);
        db=helper.getWritableDatabase();
        buildMatcher();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        switch(matcher.match(uri)){

            case FLAVOR_CONTENT_URI_VALUE:
                Cursor cursor= db.query(AndroidFlavorOpenHelper.ANDROID_FLAVORS_TABLE,
                       projection,selection,selectionArgs,null,null,sortOrder);
                /*MatrixCursor mCursor=new MatrixCursor(projection);

                while(cursor.moveToNext()){
                    Object[] backData=new Object[projection.length];
                    for(int i=0;i<projection.length;i++){
                        switch(projection[i]){
                            case AndroidFlavorContract.FLAVORS_CONTENT.PK_COLUMN:
                                backData[i]=cursor.getInt(cursor.getColumnIndex(
                                        AndroidFlavorContract.FLAVORS_CONTENT.PK_COLUMN));
                                break;
                            case AndroidFlavorContract.FLAVORS_CONTENT.DESCRITION_COLUMN:
                                backData[i]=cursor.getString(cursor.getColumnIndex(
                                        AndroidFlavorContract.FLAVORS_CONTENT.DESCRITION_COLUMN));
                                break;
                            case AndroidFlavorContract.FLAVORS_CONTENT.NAME_COLUMN:
                                backData[i]=cursor.getString(cursor.getColumnIndex(
                                        AndroidFlavorContract.FLAVORS_CONTENT.NAME_COLUMN));
                                break;
                            case AndroidFlavorContract.FLAVORS_CONTENT.IMAGE_ID_COLUMN:
                                int localImageId=cursor.getInt(cursor.getColumnIndex(
                                        AndroidFlavorContract.FLAVORS_CONTENT.IMAGE_ID_COLUMN));
                                Bitmap bitmap= BitmapFactory.decodeResource(getContext().getResources(),
                                        localImageId);
                                backData[i]=bitmap;
                                break;
                        }
                    }

                    mCursor.addRow(backData);
                }*/
                return cursor;
            case FLAVOR_ITEM_URI_VALUE:
                int id=Integer.parseInt(uri.getLastPathSegment());
                return db.query(AndroidFlavorOpenHelper.ANDROID_FLAVORS_TABLE,projection,
                         AndroidFlavorContract.FLAVORS_CONTENT.PK_COLUMN + "=?",
                        new String[]{"" + id},null,null,null);

            default:
                throw new UnsupportedOperationException(uri + " no reconocida");
        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        switch(matcher.match(uri)){

            case FLAVOR_CONTENT_URI_VALUE:
                return db.update(AndroidFlavorOpenHelper.ANDROID_FLAVORS_TABLE,values,
                        selection,selectionArgs);

            case FLAVOR_ITEM_URI_VALUE:
                int id=Integer.parseInt(uri.getLastPathSegment());
                selection =AndroidFlavorContract.FLAVORS_CONTENT.PK_COLUMN +"=" + id;
                return db.update(AndroidFlavorOpenHelper.ANDROID_FLAVORS_TABLE,values,
                    selection,null);

            default:
                throw new UnsupportedOperationException(uri + " no reconocida");
        }

    }
}
