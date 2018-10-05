package mazzy.and.udnugat1.flickbrowser;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class PhotoDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        ActivateToolbar(true);
        Intent intent = getIntent();
        Photo photo = (Photo) intent.getSerializableExtra(FILE_TRANSFER);

        if (photo != null) {
            TextView titleview = findViewById(R.id.photo_title);
            Resources resources=getResources();
            String tektitle = resources.getString(R.string.photo_title_text, photo.getTitle());
            titleview.setText(tektitle);



            TextView tagsview = findViewById(R.id.photo_tags);
            tagsview.setText(resources.getString(R.string.photo_tags_text,photo.getTags()));

            TextView authorview = findViewById(R.id.photo_author);
            authorview.setText(photo.getAuthor());

            ImageView imageView = findViewById(R.id.photo_image);
            Picasso.with(this.getBaseContext()).load(photo.getLink()).error(R.drawable.error).placeholder(R.drawable.placeholder).into(imageView);

        }

    }

}
