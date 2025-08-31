package mn.erdenee.myjava.screen.map;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import mn.erdenee.myjava.R;
import mn.erdenee.myjava.databinding.FragmentMapsBinding;

public class MapsFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    private FragmentMapsBinding binding;
    @Override
    public void onMapReady(GoogleMap googleMap) {
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
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

    }

    @Override
    public void onClick(View v){
        if (v.getId() == R.id.buttonCenterMap) {
//            getParentFragmentManager().beginTransaction()
//                    .replace(R.id.main_container, new MapsFragment())
//                    .addToBackStack(null)
//                    .commit();
        }
    }



    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}