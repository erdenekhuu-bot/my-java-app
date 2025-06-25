package mn.erdenee.myjava.ui.login;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import mn.erdenee.myjava.databinding.ActivityLoginBinding;
import mn.erdenee.myjava.R;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}