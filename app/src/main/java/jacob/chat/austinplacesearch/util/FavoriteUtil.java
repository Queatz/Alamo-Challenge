package jacob.chat.austinplacesearch.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by jacob on 2/8/18.
 */

public class FavoriteUtil {

    private static final String PREFERENCE_FAVORITES = "favorites";
    private static final String PREFERENCES = "austinplacesearch";

    private static Set<String> favorites;
    private static SharedPreferences preferences;
    private static PublishSubject<FavoriteChange> onFavoriteChange = PublishSubject.create();

    public static boolean isPlaceFavorited(Context context, String placeId) {
        ensure(context);

        return favorites.contains(placeId);
    }

    public static void togglePlaceFavorited(Context context, String placeId) {
        setPlaceFavorited(context, placeId, !isPlaceFavorited(context, placeId));
    }

    public static void setPlaceFavorited(Context context, String placeId, boolean favorited) {
        ensure(context);

        if (favorited) {
            favorites.add(placeId);
        } else {
            favorites.remove(placeId);
        }

        preferences.edit().putStringSet(PREFERENCE_FAVORITES, favorites).apply();
        onFavoriteChange.onNext(new FavoriteChange(placeId, favorited));
    }

    public static Observable<FavoriteChange> onFavoriteChange() {
        return onFavoriteChange;
    }

    private static void ensure(Context context) {
        if (favorites == null) {
            preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
            favorites = preferences.getStringSet(PREFERENCE_FAVORITES, new HashSet<>());
        }
    }
}
