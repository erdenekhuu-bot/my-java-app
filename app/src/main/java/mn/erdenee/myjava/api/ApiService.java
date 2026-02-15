package mn.erdenee.myjava.api;

import mn.erdenee.myjava.api.binding.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
public interface ApiService {
    @POST("/api/auth")
    @Headers("Content-Type: application/json")
    Call<User> register(@Body User user);

    @POST("/api/login")
    @Headers("Content-Type: application/json")
    Call<User> login(@Body User user);
}
