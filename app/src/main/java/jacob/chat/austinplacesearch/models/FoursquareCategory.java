package jacob.chat.austinplacesearch.models;

/**
 * Created by jacob on 2/7/18.
 */

public class FoursquareCategory {
    private String name;
    private FoursquareIcon icon;

    public String getName() {
        return name;
    }

    public FoursquareCategory setName(String name) {
        this.name = name;
        return this;
    }

    public FoursquareIcon getIcon() {
        return icon;
    }

    public FoursquareCategory setIcon(FoursquareIcon icon) {
        this.icon = icon;
        return this;
    }
}
