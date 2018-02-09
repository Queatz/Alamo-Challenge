package jacob.chat.austinplacesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import jacob.chat.austinplacesearch.models.FoursquareVenue;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String EXTRA_PLACES = "places";

    private static final LatLng DEFAULT_LATLNG = new LatLng(30.2672, -97.7431);

    private GoogleMap googleMap;
    private final Gson gson = new Gson();

    private MapsPresenter presenter;
    private List<FoursquareVenue> places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.backPressed();
            }
        });

        Intent intent = getIntent();

        if (intent != null) {
            places = gson.fromJson(intent.getStringExtra(EXTRA_PLACES),
                    new TypeToken<List<FoursquareVenue>>() {}.getType());
        }

        presenter = new MapsPresenter(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(DEFAULT_LATLNG));

        if (places != null) {
            BitmapDescriptor pin = BitmapDescriptorFactory.fromResource(R.drawable.pin);
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (FoursquareVenue place : places) {
                Marker marker = this.googleMap.addMarker(new MarkerOptions()
                        .position(latLngOf(place))
                        .title(place.getName()));
                marker.setTag(place);
                marker.setIcon(pin);
                builder.include(marker.getPosition());
            }

            this.googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 64));
        }

        this.googleMap.setOnInfoWindowClickListener(marker ->
                presenter.markerInfoWindowClicked((FoursquareVenue) marker.getTag()));
    }

    private LatLng latLngOf(FoursquareVenue place) {
        return new LatLng(place.getLocation().getLat(), place.getLocation().getLng());
    }

    public void goBack() {
        onBackPressed();
    }

    public void showPlace(String venue) {
        Intent intent = new Intent(this, PlaceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(PlaceActivity.EXTRA_PLACE, venue);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
