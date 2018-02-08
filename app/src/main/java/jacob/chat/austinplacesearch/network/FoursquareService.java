package jacob.chat.austinplacesearch.network;

import jacob.chat.austinplacesearch.models.FoursquareResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jacob on 2/7/18.
 */

public interface FoursquareService {
    @GET("venues/search")
    Call<FoursquareResponse> venuesSearch(@Query("client_id") String clientId,
                                          @Query("client_secret") String clientSecret,
                                          @Query("v") String version,
                                          @Query("near") String near,
                                          @Query("query") String query,
                                          @Query("limit") Integer limit);
}
