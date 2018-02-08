package jacob.chat.austinplacesearch.models;

/**
 * Created by jacob on 2/7/18.
 */

public class FoursquareIcon {
    private String prefix;
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public FoursquareIcon setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public String getSuffix() {
        return suffix;
    }

    public FoursquareIcon setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }
}
