package jacob.chat.austinplacesearch;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import jacob.chat.austinplacesearch.models.FoursquareVenue;
import jacob.chat.austinplacesearch.views.SearchResultsAdapter;

public class SearchActivity extends AppCompatActivity {

    private FloatingActionButton showMapButton;
    private View searchResultsNullState;
    private View searchResultsEmptyState;
    private View networkError;
    private RecyclerView searchResultsRecycler;
    private SearchResultsAdapter searchResultsAdapter;

    private SearchPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        showMapButton = findViewById(R.id.showMapButton);
        searchResultsNullState = findViewById(R.id.searchResultsNullState);
        searchResultsEmptyState = findViewById(R.id.searchResultsEmptyState);
        networkError = findViewById(R.id.networkError);
        searchResultsRecycler = findViewById(R.id.searchResultsRecycler);

        presenter = new SearchPresenter(this);
        presenter.search("pho");

        searchResultsRecycler.setLayoutManager(new LinearLayoutManager(this));

        showMapButton.setOnClickListener(view -> presenter.showMapButtonClicked());
        showMapButton.hide();
    }

    public void showMapButton(boolean show) {
        if (show) {
            showMapButton.show();
        } else {
            showMapButton.hide();
        }
    }

    public void showSearchResults(List<FoursquareVenue> venues) {
        networkError.setVisibility(View.GONE);
        searchResultsNullState.setVisibility(View.GONE);
        searchResultsEmptyState.setVisibility(View.GONE);

        if (searchResultsAdapter == null) {
            searchResultsAdapter = new SearchResultsAdapter(new SearchResultsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(FoursquareVenue venue) {
                    presenter.searchResultClicked(venue);
                }

                @Override
                public void onItemFavoriteClick(FoursquareVenue venue) {
                    presenter.searchResultFavoriteClicked(venue);
                }
            });
            searchResultsRecycler.setAdapter(searchResultsAdapter);
        }

        searchResultsAdapter.setVenues(venues);
    }

    public void showSearchResultsNullState() {
        networkError.setVisibility(View.GONE);
        searchResultsEmptyState.setVisibility(View.GONE);
        searchResultsNullState.setVisibility(View.VISIBLE);
    }

    public void showNetworkError() {
        searchResultsNullState.setVisibility(View.GONE);
        searchResultsEmptyState.setVisibility(View.GONE);
        networkError.setVisibility(View.VISIBLE);
    }

    public void showSearchResultsEmpty() {
        networkError.setVisibility(View.GONE);
        searchResultsNullState.setVisibility(View.GONE);
        searchResultsEmptyState.setVisibility(View.VISIBLE);
    }
}
