package mn.erdenee.myjava.screen.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import mn.erdenee.myjava.databinding.FragmentOTPBinding;
import mn.erdenee.myjava.R;


public class OTPFragment extends Fragment implements View.OnClickListener{

    private FragmentOTPBinding binding;


    public OTPFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentOTPBinding.inflate(inflater, container, false);
        binding.verify.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v){
        Toast.makeText(getActivity(), "Дарагдлаа", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}