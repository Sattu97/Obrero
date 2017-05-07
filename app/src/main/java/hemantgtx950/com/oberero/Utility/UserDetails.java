package hemantgtx950.com.oberero.Utility;

/**
 * Created by hemba on 5/7/2017.
 */

public class UserDetails {
    private String username,locality;

    public UserDetails(String username, String locality) {
        this.username = username;
        this.locality = locality;
    }

    public String getUsername() {
        return username;
    }

    public String getLocality() {
        return locality;
    }
}
