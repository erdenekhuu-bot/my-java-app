package mn.erdenee.myjava.screen.register;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import mn.erdenee.myjava.api.ApiService;
import mn.erdenee.myjava.api.RetrofitClient;
import mn.erdenee.myjava.api.binding.User;
import mn.erdenee.myjava.databinding.FragmentLoginBinding;
import mn.erdenee.myjava.R;
import mn.erdenee.myjava.screen.map.MapsFragment;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener, Callback<User> {

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

    private void loginUser(){
        String phone=binding.userphone.getText().toString().trim();
        String usertype="PASSENGER".trim();
        User user = new User(phone, usertype);
        ApiService api= RetrofitClient.getApiService();
        api.login(user).enqueue(this);


    }

    @Override
    public void onResponse(Call<User> call, Response<User> response){
        if(response.isSuccessful()){
            Log.d("response", response.toString());
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new MapsFragment())
                    .addToBackStack(null)
                    .commit();
            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Did not registered", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        Log.d("TAG", t.getMessage());
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v){

        if (v.getId() == R.id.signupbtn) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new RegisterFragment())
                    .addToBackStack(null)
                    .commit();
        } else if(v.getId()==R.id.enter){
            loginUser();

        }
    }

}