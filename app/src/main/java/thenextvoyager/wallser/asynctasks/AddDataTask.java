package thenextvoyager.wallser.asynctasks;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import thenextvoyager.wallser.R;
import thenextvoyager.wallser.data.DataModel;
import thenextvoyager.wallser.data.ImageContract;
import thenextvoyager.wallser.utility.Utility;

/**
 * Created by Abhiroj on 3/23/2017.
 */

public class AddDataTask extends AsyncTask<DataModel, Void, Boolean> {

    Fragment fragment;
    Context context;
    ContentResolver resolver;
    FloatingActionButton favorite;

    public AddDataTask(Fragment fragment, FloatingActionButton favorite) {
        super();
        this.fragment = fragment;
        context = fragment.getContext();
        resolver = context.getContentResolver();
        this.favorite = favorite;
    }

    @Override
    protected Boolean doInBackground(DataModel... dataModels) {
        try {
            DataModel object = dataModels[0];
            if (Utility.checkIfImageIsInDatabase(resolver, ImageContract.ImageEntry.COLUMN_NAME, object.name))
                return false;
            ContentValues contentValues = new ContentValues();
            contentValues.put(ImageContract.ImageEntry.COLUMN_NAME, object.name);
            contentValues.put(ImageContract.ImageEntry.COLUMN_REGURL, object.imageURL);
            contentValues.put(ImageContract.ImageEntry.COLUMN_DLDURL, object.downloadURL);
            resolver.insert(ImageContract.ImageEntry.CONTENT_URI, contentValues);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean) {
            favorite.setImageResource(R.drawable.ic_favorite_filled);
        } else {
            Toast.makeText(context, R.string.image_present, Toast.LENGTH_SHORT).show();
        }


    }

}
