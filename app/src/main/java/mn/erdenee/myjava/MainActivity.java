package mn.erdenee.myjava;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import mn.erdenee.myjava.screen.map.MapsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new MapsFragment()).commit();
    }
}
