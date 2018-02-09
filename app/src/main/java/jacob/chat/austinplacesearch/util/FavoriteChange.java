package jacob.chat.austinplacesearch.util;

/**
 * Created by jacob on 2/8/18.
 */

public class FavoriteChange {
    private final String placeId;
    private final boolean favorited;

    public FavoriteChange(String placeId, boolean favorited) {
        this.placeId = placeId;
        this.favorited = favorited;
    }

    public String getPlaceId() {
        return placeId;
    }

    public boolean isFavorited() {
        return favorited;
    }
}
