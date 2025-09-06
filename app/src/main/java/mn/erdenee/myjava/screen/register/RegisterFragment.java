package mn.erdenee.myjava.screen.register;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import mn.erdenee.myjava.R;
import mn.erdenee.myjava.api.ApiService;
import mn.erdenee.myjava.api.RetrofitClient;
import mn.erdenee.myjava.api.binding.User;
import mn.erdenee.myjava.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment implements View.OnClickListener, Callback<User> {


    private FragmentRegisterBinding binding;

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        binding.loginbtn.setOnClickListener(this);
        binding.checkout.setOnClickListener(this);
        return binding.getRoot();
    }

    private void registerUser(){
        String phone=binding.phoneInput.getText().toString().trim();
        String usertype="PASSENGER".trim();
        User user = new User(phone, usertype);
        ApiService api= RetrofitClient.getApiService();
        api.register(user).enqueue(this);
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response){
        if(response.isSuccessful()){
            Log.d("response", response.toString());
            getParentFragmentManager().beginTransaction()
                            .replace(R.id.main_container, new LoginFragment())
                                    .addToBackStack(null)
                                            .commit();
            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        Log.d("TAG", t.getMessage());
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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

        } else if(v.getId()==R.id.checkout){
            registerUser();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
