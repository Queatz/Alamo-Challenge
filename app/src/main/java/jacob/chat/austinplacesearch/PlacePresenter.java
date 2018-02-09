package jacob.chat.austinplacesearch;

import jacob.chat.austinplacesearch.models.FoursquareVenue;

/**
 * Created by jacob on 2/8/18.
 */

public class PlacePresenter {

    private final PlaceActivity view;

    private FoursquareVenue place;

    public PlacePresenter(PlaceActivity view) {
        this.view = view;
    }

    public void setPlace(FoursquareVenue place) {
        this.place = place;
    }

    public void clickedFavoritedButton() {
        view.toggleFavorite();
    }

    public void phoneClicked() {
        view.openPhone(place.getContact().getFormattedPhone());
    }

    public void websiteClicked() {
        view.openUrl(place.getUrl());
    }

    public void undoToggleFavoriteClicked() {
        view.toggleFavorite();
    }
}
