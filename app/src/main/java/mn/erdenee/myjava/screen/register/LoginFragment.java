package mn.erdenee.myjava.screen.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import mn.erdenee.myjava.databinding.FragmentLoginBinding;
import mn.erdenee.myjava.R;
import mn.erdenee.myjava.screen.map.MapsFragment;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private FragmentLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentLoginBinding.bind(view);
        binding.enter.setOnClickListener(this);
        binding.signupbtn.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v){

        if (v.getId() == R.id.signupbtn) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new RegisterFragment())
                    .addToBackStack(null)
                    .commit();
        } else if(v.getId()==R.id.enter){
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new MapsFragment())
                    .addToBackStack(null)
                    .commit();

        }
    }

}