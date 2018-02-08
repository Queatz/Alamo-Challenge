package jacob.chat.austinplacesearch.models;

/**
 * Created by jacob on 2/7/18.
 */

public class FoursquareBaseResponse {
    private FoursquareResponse response;

    public FoursquareResponse getResponse() {
        return response;
    }

    public FoursquareBaseResponse setResponse(FoursquareResponse response) {
        this.response = response;
        return this;
    }
}
