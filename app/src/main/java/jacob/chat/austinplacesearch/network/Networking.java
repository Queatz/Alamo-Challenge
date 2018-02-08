package jacob.chat.austinplacesearch.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jacob on 2/7/18.
 */

public class Networking {

    private final FoursquareService foursquareService;

    public Networking() {
        foursquareService = new Retrofit.Builder()
                .baseUrl(FoursquareConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FoursquareService.class);
    }

    public FoursquareService getFoursquareService() {
        return foursquareService;
    }
}
