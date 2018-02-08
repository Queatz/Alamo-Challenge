package jacob.chat.austinplacesearch.models;

/**
 * Created by jacob on 2/7/18.
 */

public class FoursquareResponse {
    private FoursquareVenueResponse response;

    public FoursquareVenueResponse getResponse() {
        return response;
    }

    public FoursquareResponse setResponse(FoursquareVenueResponse response) {
        this.response = response;
        return this;
    }
}
