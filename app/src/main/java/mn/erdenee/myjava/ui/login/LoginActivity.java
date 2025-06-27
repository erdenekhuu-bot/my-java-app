package mn.erdenee.myjava.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import mn.erdenee.myjava.databinding.ActivityLoginBinding;
import mn.erdenee.myjava.ui.map.MapsActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityLoginBinding binding;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.loginButton.setOnClickListener(this);
        binding.registerText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.loginButton) {
            intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        } else if (v == binding.registerText) {
            Toast.makeText(this, "Reigster button clicked", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}