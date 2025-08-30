package fi.vuorenkoski.kokkelitrains;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        TextView versionText = findViewById(R.id.versioView);
        versionText.setText("Kökkelitrains, ver. 0.9.8 release");
//        versionText.setText("Kökkelitrains, ver. "+BuildConfig.VERSION_NAME+" "+BuildConfig.BUILD_TYPE);

        View backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
