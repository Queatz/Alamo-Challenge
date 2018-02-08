package jacob.chat.austinplacesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jacob.chat.austinplacesearch.models.FoursquareVenue;
import jacob.chat.austinplacesearch.util.ViewUtil;
import jacob.chat.austinplacesearch.views.SearchResultsAdapter;

public class SearchActivity extends AppCompatActivity {

    private FloatingActionButton showMapButton;
    private View searchResultsNullState;
    private View searchResultsEmptyState;
    private View networkError;
    private RecyclerView searchResultsRecycler;
    private SearchResultsAdapter searchResultsAdapter;
    private EditText searchQuery;
    private View loading;

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
        searchQuery = findViewById(R.id.searchQuery);
        loading = findViewById(R.id.loading);

        searchResultsRecycler.setLayoutManager(new LinearLayoutManager(this));

        showMapButton.setOnClickListener(view -> presenter.showMapButtonClicked());
        showMapButton.hide();

        searchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.searchQueryChanged(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    presenter.search(searchQuery.getText().toString());
                    return true;
                }

                return false;
            }
        });

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

        presenter = new SearchPresenter(this);
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
        searchResultsAdapter.setVenues(venues);
        searchResultsRecycler.scrollToPosition(0);
    }

    public void showSearchResultsNullState() {
        searchResultsAdapter.setVenues(null);
        networkError.setVisibility(View.GONE);
        searchResultsEmptyState.setVisibility(View.GONE);
        searchResultsNullState.setVisibility(View.VISIBLE);
    }

    public void showNetworkError() {
        searchResultsAdapter.setVenues(null);
        searchResultsNullState.setVisibility(View.GONE);
        searchResultsEmptyState.setVisibility(View.GONE);
        networkError.setVisibility(View.VISIBLE);
    }

    public void showSearchResultsEmpty() {
        searchResultsAdapter.setVenues(null);
        networkError.setVisibility(View.GONE);
        searchResultsNullState.setVisibility(View.GONE);
        searchResultsEmptyState.setVisibility(View.VISIBLE);
    }

    public void hideKeyboard() {
        ViewUtil.showSoftInputKeyboard(searchQuery, false);
    }

    public void showLoading(boolean show) {
        loading.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showMap() {
        Intent intent = new Intent(this, MapsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(MapsActivity.EXTRA_PLACES, new ArrayList<>());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
