package jacob.chat.austinplacesearch.util;

import android.app.Service;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * Created by jacob on 2/7/18.
 */

public class ViewUtil {
    public static int metersToMiles(double meters) {
        return (int) Math.round(meters / 1609.344);
    }

    public static void showSoftInputKeyboard(TextView view, boolean show) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext()
                .getSystemService(Service.INPUT_METHOD_SERVICE);

        if(show)
            inputMethodManager.showSoftInput(view, 0);
        else
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
