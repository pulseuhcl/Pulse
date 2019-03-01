package android.booker.pulse;

// I'm thinking this page should include two buttons: One for owner PIN setup
// and one for guest PIN setup.

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Owner_InitialSetup_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner__initial_setup__page);
    }
}
