package mn.erdenee.myjava.api;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface OSRMApi {
    @GET("route/v1/driving/{coords}")
    Call<String> getRoute(
            @Path("coords") String coords,
            @Query("overview") String overview,
            @Query("geometries") String geometries
    );

}
