package ClientSocket;

import com.google.gson.annotations.SerializedName;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class account {

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("role")
    private String role;
    @SerializedName ("userVerified")
    private String userVerified;
    public account(String username, String password, String role,String userVerified) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.userVerified =userVerified;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String url) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserVerified() {
        return userVerified;
    }
}

