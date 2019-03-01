package android.booker.pulse;

/*
After a brief delay of the opening page, the user will see
this page which allows them to select Guest of Owner. They will then be
redirected to the appropriate activity.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Owner_Guest_Selection_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner__guest__page);
    }
}
