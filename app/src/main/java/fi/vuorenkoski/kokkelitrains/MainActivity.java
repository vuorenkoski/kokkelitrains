package fi.vuorenkoski.kokkelitrains;

import static fi.vuorenkoski.kokkelitrains.R.color.colorPrimaryLight;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    public static final String EXTRA_MESSAGE_NUMBER ="fi.vuorenkoski.kokkelitrains.extra.MESSAGE_NUMBER";
    public static final String EXTRA_MESSAGE_ID ="fi.vuorenkoski.kokkelitrains.extra.MESSAGE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AdapterView.OnItemSelectedListener eventListener = new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Station departure=new Station(String.valueOf(departureStationSpinner.getSelectedItem()));
                Station destination=new Station(String.valueOf(destinationStationSpinner.getSelectedItem()));
                trains=new ArrayList<>();
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

        setContentView(R.layout.activity_main);
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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.info) {
            startActivity(new Intent(this, Info.class));
        }
        return true;
    }

}
