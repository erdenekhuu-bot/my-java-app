package mn.erdenee.myjava;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;

public class StateManagement {
    private List<LatLng> locations = new ArrayList<>();
    String address="";

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public void addLocation(LatLng latLng) {
        if (locations.size() >= 2) {
            locations.clear();
        }
        locations.add(latLng);
    }

    public List<LatLng> getLocations() {
        return locations;
    }

    public void clearLocations() {
        locations.clear();
    }

}
