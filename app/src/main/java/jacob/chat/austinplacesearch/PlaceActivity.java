package jacob.chat.austinplacesearch;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import jacob.chat.austinplacesearch.models.FoursquareCategory;
import jacob.chat.austinplacesearch.models.FoursquareLocation;
import jacob.chat.austinplacesearch.models.FoursquareVenue;
import jacob.chat.austinplacesearch.util.FavoriteUtil;
import jacob.chat.austinplacesearch.util.UrlUtil;
import jacob.chat.austinplacesearch.util.ViewUtil;

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

            ImageView venueIcon = findViewById(R.id.venueIcon);
            TextView venueCategory = findViewById(R.id.venueCategory);
            TextView venueDistance = findViewById(R.id.venueDistance);
            TextView phone = findViewById(R.id.phone);
            TextView website = findViewById(R.id.website);

            if (place.getCategories() != null && !place.getCategories().isEmpty()) {
                FoursquareCategory category = place.getCategories().get(0);
                Picasso.with(this)
                        .load(category.getIcon().getPrefix() + "88" + category.getIcon().getSuffix())
                        .placeholder(R.drawable.ic_local_cafe_black_24dp)
                        .into(venueIcon);

                venueCategory.setText(category.getName());
            }

            int miles = ViewUtil.metersToMiles(place.getLocation().getDistance());

            if (miles == 0) {
                venueDistance.setText(getResources().getString(R.string.closeBy));
            } else {
                venueDistance.setText(getResources().getQuantityString(R.plurals.distanceInMiles, miles, miles));
            }

            if (place.getContact() == null || place.getContact().getFormattedPhone() == null) {
                phone.setVisibility(View.GONE);
            } else {
                phone.setText(place.getContact().getFormattedPhone());
                phone.setOnClickListener(view -> presenter.phoneClicked());
            }

            if (place.getUrl() == null) {
                website.setVisibility(View.GONE);
            } else {
                website.setText(place.getUrl());
                website.setOnClickListener(view -> presenter.websiteClicked());
            }

            venueFavorited = findViewById(R.id.venueFavorited);
            venueFavorited.setOnClickListener(view -> presenter.clickedFavoritedButton());
            updateFavorited(FavoriteUtil.isPlaceFavorited(this, place.getId()));

            Picasso.with(this).load(UrlUtil.getStaticMapUrl(
                    getString(R.string.google_maps_key),
                    DEFAULT_LOCATION,
                    place.getLocation()))
                    .placeholder(R.color.colorAccent)
                    .into((ImageView) findViewById(R.id.mapImage));
        }

        presenter = new PlacePresenter(this);
        presenter.setPlace(place);
    }

    public void openPhone(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
        startActivity(intent);
    }

    public void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void updateFavorited(boolean favorited) {
        int color = getResources().getColor(favorited ? R.color.colorAccent : R.color.placeholder);
        venueFavorited.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    public void toggleFavorite() {
            FavoriteUtil.togglePlaceFavorited(this, place.getId());
            boolean favorited = FavoriteUtil.isPlaceFavorited(this, place.getId());
            updateFavorited(favorited);

            String message = getString(favorited ? R.string.addedToFavorites : R.string.removedFromFavorites);
            Snackbar.make(venueFavorited, message, Snackbar.LENGTH_LONG).setAction(getString(R.string.undo),
                    snackbar -> presenter.undoToggleFavoriteClicked()).show();
    }
}
