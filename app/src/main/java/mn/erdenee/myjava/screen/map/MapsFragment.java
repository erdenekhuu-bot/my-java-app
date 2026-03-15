package mn.erdenee.myjava.screen.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.location.Address;
import android.location.Geocoder;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;
import java.util.Locale;

import mn.erdenee.myjava.R;
import mn.erdenee.myjava.databinding.FragmentMapsBinding;

public class MapsFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMapClickListener {

    private FragmentMapsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap googleMap;
    private Location currentLocation;

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
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonCenterMap) {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
            View bottomSheetView = getLayoutInflater().inflate(R.layout.layout_bottom_sheet, null);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        try {
            Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            String addressText = "Хаяг тодорхойгүй";

            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String fullAddress = address.getAddressLine(0);

                if (fullAddress != null && !fullAddress.isEmpty()) {
                    addressText = fullAddress.split(",")[0].trim();

                }
            }

            BitmapDescriptor bitmapDescriptor =
                    BitmapDescriptorFactory.fromResource(R.drawable.custommarker);

            if (googleMap != null) {
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(addressText)
                        .icon(bitmapDescriptor));

                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }

            Log.d("TAG", "Сонгосон товч хаяг: " + addressText);
            Toast.makeText(requireContext(), addressText, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e("TAG", "Geocoder алдаа: " + e.getMessage());
            Toast.makeText(requireContext(), "Хаяг авахад алдаа гарлаа", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
