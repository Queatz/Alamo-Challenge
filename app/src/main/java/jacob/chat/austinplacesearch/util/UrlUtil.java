package jacob.chat.austinplacesearch.util;

import jacob.chat.austinplacesearch.models.FoursquareLocation;

/**
 * Created by jacob on 2/8/18.
 */

public class UrlUtil {
    public static String getStaticMapUrl(String apiKey, FoursquareLocation start, FoursquareLocation stop) {
        return "https://maps.googleapis.com/maps/api/staticmap?center=Austin,+TX&zoom=13&size=600x300&maptype=roadmap" +
                "&markers=color:red%7Clabel:A%7C" + latLngString(start) + "&markers=color:green%7Clabel:B%7C" +
                latLngString(stop) + "&key=" + apiKey;
    }

    private static String latLngString(FoursquareLocation location) {
        return location.getLat() + "," + location.getLng();
    }
}
