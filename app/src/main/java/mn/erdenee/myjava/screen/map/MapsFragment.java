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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import mn.erdenee.myjava.R;
import mn.erdenee.myjava.api.OSRMApi;
import mn.erdenee.myjava.databinding.FragmentMapsBinding;
import retrofit2.Call;
import retrofit2.Retrofit;

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

//        if (locations.size() == 2) {
//            googleMap.addPolyline(new com.google.android.gms.maps.model.PolylineOptions()
//                    .add(locations.get(0), locations.get(1))
//                    .width(10)
//                    .color(android.graphics.Color.BLUE)
//                    .geodesic(true));
//
//            com.google.android.gms.maps.model.LatLngBounds bounds = new com.google.android.gms.maps.model.LatLngBounds.Builder()
//                    .include(locations.get(0))
//                    .include(locations.get(1))
//                    .build();
//
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
//        } else {
//            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//        }
        if (locations.size() == 2) {
            // OSRM-ээс бодит замын өгөгдөл татаж зурах функцийг дуудах
            getRoute(locations.get(0), locations.get(1));

            // Хоёр цэгийг дэлгэцэнд багтааж харуулах
            com.google.android.gms.maps.model.LatLngBounds bounds = new com.google.android.gms.maps.model.LatLngBounds.Builder()
                    .include(locations.get(1))
                    .build();
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
        } else {
            // Зөвхөн нэг цэг байгаа бол тухайн цэг рүү камер шилжинэ
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


    private void getRoute(LatLng source, LatLng destination) {
        //1. Координатуудыг OSRM форматад оруулах: longitude,latitude;longitude,latitude
        String coords = source.longitude + "," + source.latitude + ";" +
                destination.longitude + "," + destination.latitude;

        // 2. Retrofit тохиргоо (ScalarsConverter ашиглана)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://router.project-osrm.org/")
                .addConverterFactory(retrofit2.converter.scalars.ScalarsConverterFactory.create())
                .build();

        OSRMApi osrmApi = retrofit.create(OSRMApi.class);

        // 3. Хүсэлт илгээх
        osrmApi.getRoute(coords, "full", "polyline").enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        // JSON-оос "geometry" (Encoded Polyline) утгыг салгаж авах
                        org.json.JSONObject jsonResponse = new org.json.JSONObject(response.body());
                        org.json.JSONArray routes = jsonResponse.getJSONArray("routes");
                        if (routes.length() > 0) {
                            String encodedPolyline = routes.getJSONObject(0).getString("geometry");

                            // Polyline-ийг Decode хийж газрын зураг дээр зурах
                            drawPolyline(encodedPolyline);
                        }
                    } catch (Exception e) {
                        Log.e("OSRM", "JSON задлахад алдаа: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("OSRM", "Сүлжээний алдаа: " + t.getMessage());
            }
        });
    }


    private void drawPolyline(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0; result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            poly.add(new LatLng((double) lat / 1E5, (double) lng / 1E5));
        }

        // Газрын зураг дээр зам татах
        googleMap.addPolyline(new com.google.android.gms.maps.model.PolylineOptions()
                .addAll(poly)
                .width(12)
                .color(android.graphics.Color.BLUE)
                .geodesic(true));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
