package es.cice.androidcontentprovider.dataprovider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by cice on 8/2/17.
 */

public class AndroidFlavorContract {
    public static final String AUTHORITY="es.cice.androidcontentprovider.app";
    public static final String AUTHORITY_URI_STRING="content://es.cice.androidcontentprovider.app";
    public static final String CONTENT_URI_SEGMENT="flavors";
    public static final String ITEM_URI_SEGMENT="item";

    public static final Uri AUTHORITY_URI=Uri.parse("content://es.cice.androidcontentprovider.app");
    public static final String ANDROID_FLAVOR_ITEM_MIME="vnd.android.cursor.item/" +
            "es.cice.androidcontentprovider.flavors";
    public static final String ANDROID_FLAVOR_DIR_MIME="vnd.android.cursor.dir/" +
            "es.cice.androidcontentprovider.flavors";
    public static final Uri ANDROID_FLAVOR_CONTENT=AUTHORITY_URI.buildUpon().appendPath("flavors").build();
    public static final Uri ANDROID_FLAVOR_ITEM=AUTHORITY_URI.buildUpon().appendPath("item").build();

    public static class FLAVORS_CONTENT implements BaseColumns{
        public static final String PK_COLUMN="_id";
        public static final String NAME_COLUMN="NAME";
        public static final String DESCRITION_COLUMN="DESCRIPTION";
        public static final String IMAGE_ID_COLUMN="IMAGE_ID";
    }

}
