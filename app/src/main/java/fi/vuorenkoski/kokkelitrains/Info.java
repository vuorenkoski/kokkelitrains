package fi.vuorenkoski.kokkelitrains;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        TextView versionText = findViewById(R.id.versioView);
        versionText.setText("Kökkelitrains, v. 0.9.5");
    }
}
