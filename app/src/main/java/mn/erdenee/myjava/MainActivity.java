package mn.erdenee.myjava;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import mn.erdenee.myjava.screen.map.MapsActivity;
import mn.erdenee.myjava.screen.register.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent page = new Intent();
        page.setClass(MainActivity.this, MapsActivity.class);
        startActivity(page);
//
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.main_container, new LoginFragment())
//                .commit();
    }
}
