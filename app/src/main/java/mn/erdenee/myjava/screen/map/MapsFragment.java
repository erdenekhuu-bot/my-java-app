package mn.erdenee.myjava.screen.map;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import mn.erdenee.myjava.R;
import mn.erdenee.myjava.databinding.FragmentMapsBinding;

public class MapsFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMapClickListener {

    private FragmentMapsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap googleMap;
    private Location currentLocation;

    private final int FINE_PERMISSION_CODE=1;


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setOnMapClickListener(this);
        LatLng ulaanbaatar = new LatLng(47.921230, 106.918556);
        googleMap.addMarker(new MarkerOptions().position(ulaanbaatar).title("Би энд байна"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ulaanbaatar, 15));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMapsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonCenterMap.setOnClickListener(this);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapfrags);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(requireContext());
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onClick(View v){
        if (v.getId() == R.id.buttonCenterMap) {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
            View bottomSheetView = getLayoutInflater().inflate(R.layout.layout_bottom_sheet, null);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        this.googleMap.clear();
        this.googleMap.addMarker(new MarkerOptions().position(latLng).title("Сонгосон байршил"));
        this.googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        Log.d("TAG", "Сонгосон цэг: " + latLng.latitude + ", " + latLng.longitude);
    }



    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}
