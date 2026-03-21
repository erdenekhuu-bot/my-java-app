package mn.erdenee.myjava;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import mn.erdenee.myjava.databinding.ActivityMainBinding;
import mn.erdenee.myjava.screen.map.MapsFragment;
import mn.erdenee.myjava.screen.register.LoginFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            showFragment(new LoginFragment());
        }

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.location) {
                showFragment(new MapsFragment());
                return true;
            } else if (id == R.id.travel) {
                return true;
            }
            return false;
        });
    }

    public void showFragment(Fragment fragment) {
        if (fragment instanceof LoginFragment) {
            binding.bottomNavigation.setVisibility(View.GONE);
        } else {
            binding.bottomNavigation.setVisibility(View.VISIBLE);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }
}
