package jacob.chat.austinplacesearch;

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

    private Networking networking;

    private SearchActivity view;

    public SearchPresenter(SearchActivity view) {
        this.view = view;
        networking = new Networking();

        this.view.showSearchResultsNullState();
    }

    public void showMapButtonClicked() {

    }

    public void search(String query) {
        networking.getFoursquareService().venuesSearch(
                FoursquareConfig.CLIENT_ID,
                FoursquareConfig.CLIENT_SECRET,
                FoursquareConfig.API_VERSION,
                DEFAULT_SEARCH_LOCATION,
                query,
                SEARCH_RESULTS_LIMIT).enqueue(new Callback<FoursquareResponse>() {
            @Override
            public void onResponse(Call<FoursquareResponse> call, Response<FoursquareResponse> response) {
                if (response.isSuccessful()) {
                    List<FoursquareVenue> venues = response.body().getResponse().getVenues();

                    if (venues.isEmpty()) {
                        view.showSearchResultsEmpty();
                    } else {
                        view.showSearchResults(venues);
                    }
                } else {
                    view.showNetworkError();
                }
            }

            @Override
            public void onFailure(Call<FoursquareResponse> call, Throwable t) {
                view.showNetworkError();
            }
        });
    }
}
