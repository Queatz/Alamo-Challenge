package jacob.chat.austinplacesearch;

import java.util.Collections;
import java.util.List;

import jacob.chat.austinplacesearch.models.FoursquareResponse;
import jacob.chat.austinplacesearch.models.FoursquareVenue;
import jacob.chat.austinplacesearch.network.FoursquareConfig;
import jacob.chat.austinplacesearch.network.Networking;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jacob on 2/7/18.
 */

public class SearchPresenter {

    private static final int SEARCH_RESULTS_LIMIT = 20;
    private static final String DEFAULT_SEARCH_LOCATION = "Austin,+TX";
    private static final String DEFAULT_SEARCH_LAT_LNG = "30.2672,-97.7431";

    private Networking networking;

    private final SearchActivity view;

    public SearchPresenter(SearchActivity view) {
        this.view = view;
        networking = new Networking();
        setup();
    }

    public void showMapButtonClicked() {
        view.showMap();
    }

    public void searchQueryChanged(String searchQuery) {

    }

    public void search(String query) {
        view.hideKeyboard();
        view.showLoading(true);

        networking.getFoursquareService().venuesSearch(
                FoursquareConfig.CLIENT_ID,
                FoursquareConfig.CLIENT_SECRET,
                FoursquareConfig.API_VERSION,
                DEFAULT_SEARCH_LOCATION,
                DEFAULT_SEARCH_LAT_LNG,
                query,
                SEARCH_RESULTS_LIMIT).enqueue(new Callback<FoursquareResponse>() {
            @Override
            public void onResponse(Call<FoursquareResponse> call, Response<FoursquareResponse> response) {
                view.showLoading(false);

                if (response.isSuccessful()) {
                    List<FoursquareVenue> venues = response.body().getResponse().getVenues();
                    Collections.sort(venues, (a, b) -> a.getLocation().getDistance() < b.getLocation().getDistance() ? -1 : 1);

                    if (venues.isEmpty()) {
                        view.showSearchResultsEmpty();
                        view.showMapButton(false);
                    } else {
                        view.showSearchResults(venues);
                        view.showMapButton(true);
                    }
                } else {
                    view.showNetworkError();
                    view.showMapButton(false);
                }
            }

            @Override
            public void onFailure(Call<FoursquareResponse> call, Throwable t) {
                view.showLoading(false);
                view.showNetworkError();
                view.showMapButton(false);
            }
        });
    }

    public void searchResultClicked(FoursquareVenue venue) {

    }

    public void searchResultFavoriteClicked(FoursquareVenue venue) {

    }

    private void setup() {
        view.showMapButton(false);
        view.showSearchResultsNullState();
    }
}
