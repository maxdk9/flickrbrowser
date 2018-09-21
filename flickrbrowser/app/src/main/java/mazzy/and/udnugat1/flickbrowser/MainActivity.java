package mazzy.and.udnugat1.flickbrowser;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements GetFlickJsonData.OnDataAvailable {
    private static final String TAG = "MainActivity";

    private final String FlickrUrl1="https://api.flickr.com/services/feeds/photos_public.gne?tags=nougat,android,sdk&tagmode=any&format=json";
    private final String FlickrUrl2="https://api.flickr.com/services/feeds/photos_public.gne";

    private Button buttonTest;


    FlickrRecyclerViewAdapter flickrRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreateMethodStarted");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        /*GetRawData getRawData = new GetRawData(this);
        getRawData.doInBackground(FlickrUrl1);*/


        //buttonTest=(Button) findViewById(R.id.buttontest);
        //buttonTest.setOnClickListener(this.buttonTestClickListener);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        flickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter( new ArrayList<Photo>(),this);
        RecyclerView recyclerView = this.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(flickrRecyclerViewAdapter);

        Log.d(TAG, "OnCreateMethodEnd");
    }


    private View.OnClickListener buttonTestClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GetFlickrData();
        }
    };

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume starts");
        super.onResume();
        GetFlickrData();
        Log.d(TAG, "onResume ends");

    }


    private void GetFlickrData() {
        GetFlickJsonData getFlickJsonData = new GetFlickJsonData(FlickrUrl2,"en-us",true,this);
        //getFlickJsonData.executeOnSameThread("android, nougat");
        getFlickJsonData.execute("android, nougat");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        Log.d(TAG, "OnCreateOptionsMenu "+true);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "OnOptionsItemSelected returned" + true);
        return super.onOptionsItemSelected(item);
    }







    @Override
    public void onDataAvailable(List<Photo> list, DownloadStatus status) {
        if (status == DownloadStatus.OK) {
            Log.d(TAG, "OnDownloadComplete. downloadData is"+list);
            flickrRecyclerViewAdapter.loadNewData(list);
        }
        else{
            Log.e(TAG, "OnDownloadComplete. Error, Download status is " + status);
        }
    }
}
