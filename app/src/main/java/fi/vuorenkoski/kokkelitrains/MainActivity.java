package fi.vuorenkoski.kokkelitrains;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import static fi.vuorenkoski.kokkelitrains.R.color.colorPrimaryLight;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.StrictMode;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fi.vuorenkoski.kokkelitrains.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Spinner departureStationSpinner;
    private Spinner destinationStationSpinner;
    private RecyclerView recyclerView;
    private TrainAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Train> trains;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view =getSupportActionBar().getCustomView();

        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_info);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Info.class));
            }
        });

        AdapterView.OnItemSelectedListener eventListener = new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Station departure=new Station(String.valueOf(departureStationSpinner.getSelectedItem()));
                Station destination=new Station(String.valueOf(destinationStationSpinner.getSelectedItem()));
                trains = new ArrayList<>();
                mAdapter = new TrainAdapter(MainActivity.this, trains);
                recyclerView.setAdapter(mAdapter);
                if (!departure.getShortCode().equals(destination.getShortCode())) {
                    try {
                        trains = DataSearch.getTrains(departure.getShortCode(), destination.getShortCode());
                        mAdapter = new TrainAdapter(MainActivity.this, trains);
                        recyclerView.setAdapter(mAdapter);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Datan hakeminen ei onnistunut: "+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        if (android.os.Build.VERSION.SDK_INT > 9) // Tarvitaan nettiyhteyttä varten
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        departureStationSpinner = findViewById(R.id.departureStation);
        ArrayAdapter<CharSequence> adapterDepartureStation = ArrayAdapter.createFromResource(this,
                R.array.selectableStations, android.R.layout.simple_spinner_item);
        adapterDepartureStation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departureStationSpinner.setAdapter(adapterDepartureStation);
        departureStationSpinner.setOnItemSelectedListener(eventListener);
        departureStationSpinner.setPopupBackgroundResource(colorPrimaryLight);

        destinationStationSpinner = findViewById(R.id.destinationStation);
        ArrayAdapter<CharSequence> adapterDestinationStation = ArrayAdapter.createFromResource(this,
                R.array.selectableStations, android.R.layout.simple_spinner_item);
        adapterDestinationStation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destinationStationSpinner.setAdapter(adapterDestinationStation);
        destinationStationSpinner.setOnItemSelectedListener(eventListener);
        destinationStationSpinner.setPopupBackgroundResource(colorPrimaryLight);

        recyclerView = findViewById(R.id.trainList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}