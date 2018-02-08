package jacob.chat.austinplacesearch;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

import jacob.chat.austinplacesearch.models.FoursquareVenue;

public class SearchActivity extends AppCompatActivity {

    private FloatingActionButton showMapButton;

    private SearchPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SearchPresenter(this);

        setContentView(R.layout.activity_search);
        showMapButton = findViewById(R.id.showMapButton);
        showMapButton.setOnClickListener(view -> presenter.showMapButtonClicked());
        showMapButton.hide();

        presenter.search("pho");
    }

    public void updateSearchResults(List<FoursquareVenue> venues) {
        ((TextView) findViewById(R.id.fullscreenContent)).setText(
                venues.isEmpty() ? "Nothing here" : venues.get(0).getName()
        );
    }

    public void showNetworkError() {
        ((TextView) findViewById(R.id.fullscreenContent)).setText("Big time error");
    }
}
