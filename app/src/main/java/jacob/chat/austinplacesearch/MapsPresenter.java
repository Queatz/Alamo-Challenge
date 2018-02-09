package jacob.chat.austinplacesearch;

/**
 * Created by jacob on 2/8/18.
 */

public class MapsPresenter {

    private final MapsActivity view;

    public MapsPresenter(MapsActivity view) {
        this.view = view;
    }

    public void backPressed() {
        view.goBack();
    }
}
