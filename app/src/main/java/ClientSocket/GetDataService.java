package ClientSocket;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {
    @GET("username")
    Call<List<account>> loadUser(@Query("username") String tags);
}
