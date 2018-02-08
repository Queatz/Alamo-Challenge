package jacob.chat.austinplacesearch.models;

/**
 * Created by jacob on 2/7/18.
 */

public class FoursquareLocation {
    private String address;
    private double lat;
    private double lng;

    public String getAddress() {
        return address;
    }

    public FoursquareLocation setAddress(String address) {
        this.address = address;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public FoursquareLocation setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLng() {
        return lng;
    }

    public FoursquareLocation setLng(double lng) {
        this.lng = lng;
        return this;
    }
}
