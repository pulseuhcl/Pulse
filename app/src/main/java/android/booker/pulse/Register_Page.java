package android.booker.pulse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Register_Page extends AppCompatActivity {
    private ImageView gearIcon;
    private ImageView userIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__page);
        gearIcon = findViewById(R.id.gearIcon);
        userIcon = findViewById(R.id.userIcon);

        Glide
                .with(this)
                .load(getDrawable(R.drawable.user))
                .into(userIcon);
        Glide
                .with(this)
                .load(getDrawable(R.drawable.gear))
                .into(gearIcon);
    }
}
