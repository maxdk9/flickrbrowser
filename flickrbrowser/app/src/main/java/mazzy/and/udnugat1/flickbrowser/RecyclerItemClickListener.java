package mazzy.and.udnugat1.flickbrowser;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerItemClickListen";
    interface  onRecyclerClickListener{
        void onItemClick(View view, int Position);
        void onLongClick(View view, int Position);
    }
    private final onRecyclerClickListener mGestureListneter;
    private final GestureDetectorCompat mGestureDetector;


    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, final onRecyclerClickListener mGestureListneter) {


        this.mGestureListneter = mGestureListneter;
        this.mGestureDetector=new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "onSingleTapUp: starts");

                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if((childView!=null)&&(mGestureListneter != null)){
                    Log.d(TAG,"onSingleTapUp: calling listener.onItemClick");
                    mGestureListneter.onItemClick(childView,recyclerView.getChildAdapterPosition(childView));

                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongPress: starts");
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mGestureListneter != null) {
                    mGestureListneter.onLongClick(childView,recyclerView.getChildAdapterPosition(childView));
                }

            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: starts");
        if (mGestureDetector != null) {
            boolean result = mGestureDetector.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent: return " + result);
            return result;

        }
        else{
            Log.d(TAG, "onInterceptTouchEvent: return false - gesture detector is null");
            return false;
        }

    }
}
