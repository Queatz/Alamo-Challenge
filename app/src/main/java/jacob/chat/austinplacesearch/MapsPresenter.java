package jacob.chat.austinplacesearch;

import com.google.gson.Gson;

import jacob.chat.austinplacesearch.models.FoursquareVenue;

/**
 * Created by jacob on 2/8/18.
 */

public class MapsPresenter {

    private final Gson gson = new Gson();

    private final MapsActivity view;

    public MapsPresenter(MapsActivity view) {
        this.view = view;
    }

    public void backPressed() {
        view.goBack();
    }

    public void markerInfoWindowClicked(FoursquareVenue place) {
        view.showPlace(gson.toJson(place));
    }
}
