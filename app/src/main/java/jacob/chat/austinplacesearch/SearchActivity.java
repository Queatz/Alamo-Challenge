package jacob.chat.austinplacesearch;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import jacob.chat.austinplacesearch.models.FoursquareVenue;

public class SearchActivity extends AppCompatActivity {

    private FloatingActionButton showMapButton;
    private View searchResultsNullState;
    private View searchResultsEmptyState;
    private View networkError;
    private RecyclerView searchResultsRecycler;

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

        showMapButton.setOnClickListener(view -> presenter.showMapButtonClicked());
        showMapButton.hide();
    }

    public void showSearchResults(List<FoursquareVenue> venues) {
        networkError.setVisibility(View.GONE);
        searchResultsNullState.setVisibility(View.GONE);
        searchResultsEmptyState.setVisibility(View.GONE);
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
