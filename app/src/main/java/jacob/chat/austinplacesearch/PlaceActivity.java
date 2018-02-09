package jacob.chat.austinplacesearch;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import jacob.chat.austinplacesearch.models.FoursquareLocation;
import jacob.chat.austinplacesearch.models.FoursquareVenue;
import jacob.chat.austinplacesearch.util.FavoriteUtil;
import jacob.chat.austinplacesearch.util.UrlUtil;

public class PlaceActivity extends AppCompatActivity {

    public static final FoursquareLocation DEFAULT_LOCATION = new FoursquareLocation()
            .setLat(30.2672)
            .setLng(-97.7431);

    public static final String EXTRA_PLACE = "place";

    private final Gson gson = new Gson();

    private FoursquareVenue place;
    private View venueFavorited;

    private PlacePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackground(null);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        if (intent != null) {
            place = gson.fromJson(intent.getStringExtra(EXTRA_PLACE), FoursquareVenue.class);

            getSupportActionBar().setTitle(place.getName());
        }

        venueFavorited = findViewById(R.id.venueFavorited);
        venueFavorited.setOnClickListener(view -> {
            FavoriteUtil.togglePlaceFavorited(this, place.getId());
            boolean favorited = FavoriteUtil.isPlaceFavorited(this, place.getId());
            updateFavorited(favorited);

            String message = getString(favorited ? R.string.addedToFavorites : R.string.removedFromFavorites);
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(getString(R.string.undo),
                    snackbar -> venueFavorited.callOnClick()).show();
        });
        updateFavorited(FavoriteUtil.isPlaceFavorited(this, place.getId()));

        Picasso.with(this).load(UrlUtil.getStaticMapUrl(
                    getString(R.string.google_maps_key),
                    DEFAULT_LOCATION,
                    place.getLocation()))
                .placeholder(R.color.colorAccent)
            .into((ImageView) findViewById(R.id.mapImage));

        presenter = new PlacePresenter(this);
    }

    private void updateFavorited(boolean favorited) {
        int color = getResources().getColor(favorited ? R.color.colorAccent : R.color.placeholder);
        venueFavorited.setBackgroundTintList(ColorStateList.valueOf(color));
    }
}
