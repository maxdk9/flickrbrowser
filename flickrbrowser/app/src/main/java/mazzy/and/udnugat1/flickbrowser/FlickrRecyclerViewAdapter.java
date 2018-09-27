package mazzy.and.udnugat1.flickbrowser;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrRecyclerViewHolder> {
    private static final String TAG = "FlickrRecyclerViewAdapt";

    private List<Photo> mPhotoList;
    private Context mContext;

    public FlickrRecyclerViewAdapter(List<Photo> mPhotoList, Context mContext) {
        this.mPhotoList = mPhotoList;
        this.mContext = mContext;
    }

    class FlickrRecyclerViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "FlickrRecyclerViewHolde";
        private ImageView thumbnail=null;
        private TextView title=null;


        public FlickrRecyclerViewHolder(View itemView) {


            super(itemView);
            thumbnail=itemView.findViewById(R.id.thumbnail);
            title=itemView.findViewById(R.id.frametitle);


        }
    }


    void loadNewData(List<Photo> list) {
        mPhotoList=list;
        notifyDataSetChanged();
    }


    public Photo getPhoto(int position)
    {

        if (mPhotoList != null && mPhotoList.size() > position) {
            return mPhotoList.get(position);
        }
        return  null;

    }


    

    @Override
    public FlickrRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d(TAG, "Create new ViewHolder");
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.frame,parent,false);
        return new FlickrRecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(FlickrRecyclerViewHolder holder, int position) {
        Photo photo = mPhotoList.get(position);
        Picasso.with(this.mContext).load(photo.getLink()).error(R.drawable.error).placeholder(R.drawable.placeholder).into(holder.thumbnail);
        holder.title.setText(photo.getTitle());
    }

    @Override
    public int getItemCount() {

        int result=(mPhotoList!=null&&mPhotoList.size()!=0)?mPhotoList.size():0;
        return result;
    }
}
