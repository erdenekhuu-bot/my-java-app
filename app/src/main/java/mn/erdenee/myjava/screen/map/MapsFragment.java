package mn.erdenee.myjava.screen.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.location.Address;
import android.location.Geocoder;
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
import com.google.android.material.textview.MaterialTextView;
import mn.erdenee.myjava.StateManagement;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import mn.erdenee.myjava.R;
import mn.erdenee.myjava.databinding.FragmentMapsBinding;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private StateManagement state;
    private FragmentMapsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap googleMap;
    private String addressText = "";

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMapsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        state = new StateManagement();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapfrags);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
        if (mapFragment != null) mapFragment.getMapAsync(this);

    }
//    @Override
//    public void onMapClick(LatLng latLng) {
//        try {
//
//            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
//            View bottomSheetView = getLayoutInflater().inflate(R.layout.layout_bottom_sheet, null);
//            MaterialTextView textView = bottomSheetView.findViewById(R.id.bottomSheetText);
//            Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
//            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
//            if (addresses != null && !addresses.isEmpty()) {
//                Address address = addresses.get(0);
//                String fullAddress = address.getAddressLine(0);
//
//                if (fullAddress != null && !fullAddress.isEmpty()) {
//                    state.setAddress(fullAddress.toString());
//                    addressText = fullAddress.split(",")[0].trim();
//                }
//            }
//
//            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.custommarker);
//            textView.setText(state.getAddress());
//
//            googleMap.clear();
//            googleMap.addMarker(new MarkerOptions()
//                        .position(latLng)
//                        .title(addressText)
//                        .icon(bitmapDescriptor));
//
//            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//            bottomSheetDialog.setContentView(bottomSheetView);
//            bottomSheetDialog.show();
//
//
////            Toast.makeText(requireContext(), addressText, Toast.LENGTH_SHORT).show();
//
//        } catch (Exception e) {
//            Log.e("TAG", "Geocoder алдаа: " + e.getMessage());
//            Toast.makeText(requireContext(), "Хаяг авахад алдаа гарлаа", Toast.LENGTH_SHORT).show();
//        }
//
//    }

@Override
public void onMapClick(LatLng latLng) {
    try {
        state.addLocation(latLng);
        List<LatLng> locations = state.getLocations();
        googleMap.clear();
        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

        if (addresses != null && !addresses.isEmpty()) {
            Address address = addresses.get(0);
            String fullAddress = address.getAddressLine(0);
            if (fullAddress != null) {
                state.setAddress(fullAddress);
                addressText = fullAddress.split(",")[0].trim();
            }
        }

        for (int i = 0; i < locations.size(); i++) {
            LatLng pos = locations.get(i);
            String title = (i == 0) ? "Эхлэх цэг (Source)" : "Очих цэг (Destination)";

            float markerColor = (i == 0)
                    ? BitmapDescriptorFactory.HUE_GREEN
                    : BitmapDescriptorFactory.HUE_RED;

            googleMap.addMarker(new MarkerOptions()
                    .position(pos)
                    .title(title)
                    .icon(BitmapDescriptorFactory.defaultMarker(markerColor)));
        }

        if (locations.size() == 2) {
            googleMap.addPolyline(new com.google.android.gms.maps.model.PolylineOptions()
                    .add(locations.get(0), locations.get(1))
                    .width(10)
                    .color(android.graphics.Color.BLUE)
                    .geodesic(true));

            com.google.android.gms.maps.model.LatLngBounds bounds = new com.google.android.gms.maps.model.LatLngBounds.Builder()
                    .include(locations.get(0))
                    .include(locations.get(1))
                    .build();

            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
        } else {
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }

        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View bottomSheetView = getLayoutInflater().inflate(R.layout.layout_bottom_sheet, null);
        MaterialTextView textView = bottomSheetView.findViewById(R.id.bottomSheetText);

        textView.setText(state.getAddress());

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

    } catch (Exception e) {
        Log.e("TAG", "Алдаа: " + e.getMessage());
        Toast.makeText(requireContext(), "Алдаа гарлаа", Toast.LENGTH_SHORT).show();
    }
}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
