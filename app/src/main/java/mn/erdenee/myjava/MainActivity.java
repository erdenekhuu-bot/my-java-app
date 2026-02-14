package mn.erdenee.myjava;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.appdistribution.FirebaseAppDistribution;
import com.google.firebase.appdistribution.InterruptionLevel;
import mn.erdenee.myjava.screen.register.LoginFragment;
import mn.erdenee.myjava.screen.map.MapsFragment;

public class MainActivity extends AppCompatActivity {

    FirebaseAppDistribution firebaseAppDistribution = FirebaseAppDistribution.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        firebaseAppDistribution.showFeedbackNotification(
//                R.string.additionalFormText,
//                InterruptionLevel.HIGH);

//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.main_container, new LoginFragment())
//                .commit();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, new MapsFragment())
                .commit();
    }
}
