package mazzy.and.udnugat1.flickbrowser;

import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CallLog;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class GetFlickJsonData extends AsyncTask<String,Void,List<Photo>> implements  GetRawData.onDownloadComplete {

    private static final String TAG = "GetFlickJsonData";
    private List<Photo> mPhotoList = null;
    private String mBaseUrl;
    private String mLanguage;
    private boolean mMatchAll;


    private boolean RunningInSameThread=false;


    private final OnDataAvailable callback;

    @Override
    protected List<Photo> doInBackground(String... strings) {
        Log.d(TAG,"doInBackground starts");

        String destinationUri = createURI(strings[0], mLanguage, this.mMatchAll);
        GetRawData getRawData = new GetRawData(this);
        getRawData.RunInSameThread(destinationUri);

        Log.d(TAG, "doInBackground finishes");
        return mPhotoList;

    }

    @Override
    protected void onPostExecute(List<Photo> photos) {
        Log.d(TAG, "onPostExecute: starts");
        if (callback != null) {
            callback.onDataAvailable(photos,DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute: finish");

    }

    public interface OnDataAvailable{
        void onDataAvailable(List<Photo> list, DownloadStatus status);
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete, status: " + status.toString());

        if (status == DownloadStatus.OK) {
            mPhotoList = new ArrayList<Photo>();
            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemArray = (JSONArray) jsonData.get("items");
                for (int i = 0; i < itemArray.length(); i++) {
                    JSONObject jsonPhoto = (JSONObject) itemArray.get(i);
                    String title = jsonPhoto.getString("title");
                    String author = jsonPhoto.getString("author");
                    String author_id = jsonPhoto.getString("author_id");

                    String tags = jsonPhoto.getString("tags");


                    JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                    String mediaUrl = jsonMedia.getString("m");

                    String link = mediaUrl.replaceFirst("_m", "_b");

                    Photo photoObject = new Photo(title, author, author_id, link, tags, mediaUrl);
                    mPhotoList.add(photoObject);

                    Log.d(TAG, "OnDownloadComplete " + photoObject.toString());
                    //String phot

                }


            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "onDownloadComplete Error in json parsing" + e.getMessage());
                status=DownloadStatus.FAILED_OR_EMPTY;

            }
        }
        if (this.RunningInSameThread&&callback != null) {
            callback.onDataAvailable(mPhotoList,status);
        }
        Log.d(TAG, "OnDownloadComplete ends");
    }

    public GetFlickJsonData(  String mBaseUrl,String mLanguage, boolean mMatchAll, OnDataAvailable callback) {
        Log.d(TAG, "Created GetFlickJsonData");
        this.mBaseUrl = mBaseUrl;
        this.mLanguage = mLanguage;
        this.mMatchAll = mMatchAll;
        this.callback = callback;
    }

    public  void executeOnSameThread(String searchCriteria) {
        Log.d(TAG, "execute on same thread starts");
        this.RunningInSameThread=true;
        String destinationUri = createURI(searchCriteria, mLanguage, mMatchAll);
        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationUri);
        Log.d(TAG, "execute on same thread finish");

    }

    private String createURI(String searchCriteria, String lang, boolean matchall) {
        Log.d(TAG, "create URI");
        String result=Uri.parse(mBaseUrl).buildUpon()
                .appendQueryParameter("tags", searchCriteria)
                .appendQueryParameter("tagmode", matchall ? "ALL" : "ANY")
                .appendQueryParameter("lang", lang)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1").build().toString();
        return result;
    }

}
