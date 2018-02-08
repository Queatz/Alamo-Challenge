package jacob.chat.austinplacesearch.models;

import java.util.List;

/**
 * Created by jacob on 2/7/18.
 */

public class FoursquareVenue {
    private String id;
    private String name;
    private String url;
    private FoursquareLocation location;
    private List<FoursquareCategory> categories;

    public String getId() {
        return id;
    }

    public FoursquareVenue setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public FoursquareVenue setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public FoursquareVenue setUrl(String url) {
        this.url = url;
        return this;
    }

    public FoursquareLocation getLocation() {
        return location;
    }

    public FoursquareVenue setLocation(FoursquareLocation location) {
        this.location = location;
        return this;
    }

    public List<FoursquareCategory> getCategories() {
        return categories;
    }

    public FoursquareVenue setCategories(List<FoursquareCategory> categories) {
        this.categories = categories;
        return this;
    }
}
