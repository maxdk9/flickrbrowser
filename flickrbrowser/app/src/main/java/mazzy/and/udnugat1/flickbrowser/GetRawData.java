package mazzy.and.udnugat1.flickbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


enum DownloadStatus {IDLE,PROCESSING,NOT_INITIALISING,FAILED_OR_EMPTY,OK};

public class GetRawData extends AsyncTask<String, Void, String> {

    private static final String TAG = "GetRawData";

    private DownloadStatus mDownloadStatus;
    private onDownloadComplete mCallback;


    interface onDownloadComplete {
        void onDownloadComplete(String data, DownloadStatus status);

    }


    public GetRawData(onDownloadComplete callback) {
        this.mCallback = callback;
        this.mDownloadStatus = DownloadStatus.IDLE;


    }

    public void RunInSameThread(String s) {
        Log.d(TAG, "RunInSameThread starts");

        //onPostExecute(s);
        if (mCallback != null) {
            /*String result = doInBackground(s);
            mCallback.onDownloadComplete(result,mDownloadStatus);*/

            this.mCallback.onDownloadComplete(doInBackground(s),mDownloadStatus);
        }

        Log.d(TAG, "RunInSameThread finish");
    }


    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        if (strings == null) {
            this.mDownloadStatus = DownloadStatus.NOT_INITIALISING;
            return null;
        }


        try {
            this.mDownloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(strings[0]);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responceCode = connection.getResponseCode();
            Log.d(TAG, "DoInBackground. Responce code is " + responceCode);
            StringBuilder result = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));


            for (String line = reader.readLine(); line != null; line=reader.readLine()) {
                result.append(line).append("\n");
            }
            this.mDownloadStatus=DownloadStatus.OK;
            return result.toString();

        } catch (MalformedURLException e) {
            Log.e(TAG, "DoInBackground. Invalid url " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "DoInBackground. IO exception reading data " + e.getMessage());

        } catch (SecurityException e) {
            Log.e(TAG, "DoInBackground. Security exception. Need permission?" + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    Log.e(TAG, "DoInBackground error closing system" + e.getMessage());
                }
            }
        }

        mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: ");
        if (mCallback != null) {
            mCallback.onDownloadComplete(s, mDownloadStatus);
        }

    }


}
