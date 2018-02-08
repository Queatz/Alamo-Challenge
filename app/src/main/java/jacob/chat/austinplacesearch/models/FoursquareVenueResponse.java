package jacob.chat.austinplacesearch.models;

import java.util.List;

/**
 * Created by jacob on 2/7/18.
 */

public class FoursquareVenueResponse {
    private List<FoursquareVenue> venues;

    public List<FoursquareVenue> getVenues() {
        return venues;
    }

    public FoursquareVenueResponse setVenues(List<FoursquareVenue> venues) {
        this.venues = venues;
        return this;
    }
}
