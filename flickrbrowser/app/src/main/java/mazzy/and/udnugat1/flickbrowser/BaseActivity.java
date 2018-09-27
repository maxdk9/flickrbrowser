package mazzy.and.udnugat1.flickbrowser;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;


public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    static final String FLICKR_QUERY="FLICKR_QUERY";
    static final String FILE_TRANSFER = "FILE_TRANSFER";

    void ActivateToolbar(boolean homeEnabled) {
        Log.d(TAG, "ActivateToolbar: starts");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                actionBar=getSupportActionBar();

            }
        }


        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(homeEnabled);
        }
    }

}
