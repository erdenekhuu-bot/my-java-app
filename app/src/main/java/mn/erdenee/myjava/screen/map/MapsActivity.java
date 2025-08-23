package mn.erdenee.myjava.screen.map;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import mn.erdenee.myjava.R;
import mn.erdenee.myjava.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng ulaanbaatar = new LatLng(47.921230, 106.918556);
        mMap.addMarker(new MarkerOptions().position(ulaanbaatar).title("Би энд байна"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ulaanbaatar, 15));
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
}

}