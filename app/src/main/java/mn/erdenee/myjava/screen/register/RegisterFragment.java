package mn.erdenee.myjava.screen.register;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mn.erdenee.myjava.R;
import mn.erdenee.myjava.databinding.FragmentRegisterBinding;
import mn.erdenee.myjava.screen.register.LoginFragment;

public class RegisterFragment extends Fragment implements View.OnClickListener {


    private FragmentRegisterBinding binding;

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        binding.loginbtn.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v){
        if (v.getId() == R.id.loginbtn) {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            LoginFragment loginFragment = new LoginFragment();
            fragmentTransaction.replace(R.id.main_container, loginFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
