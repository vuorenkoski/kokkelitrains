package fi.vuorenkoski.kokkelitrains;

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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerDepartureStation;
    private Spinner spinnerDestinationStation;
    private RecyclerView recyclerView;
    private TrainAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Train> trains;

    public static final String EXTRA_MESSAGE_NUMBER ="fi.vuorenkoski.kokkelitrains.extra.MESSAGE_NUMBER";
    public static final String EXTRA_MESSAGE_ID ="fi.vuorenkoski.kokkelitrains.extra.MESSAGE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) // Tarvitaan nettiyhteyttä varten
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        spinnerDepartureStation = findViewById(R.id.departureStation);
        ArrayAdapter<CharSequence> adapterDepartureStation = ArrayAdapter.createFromResource(this,
                R.array.selectableStations, android.R.layout.simple_spinner_item);
        adapterDepartureStation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartureStation.setAdapter(adapterDepartureStation);

        spinnerDestinationStation = findViewById(R.id.destinationStation);
        ArrayAdapter<CharSequence> adapterDestinationStation = ArrayAdapter.createFromResource(this,
                R.array.selectableStations, android.R.layout.simple_spinner_item);
        adapterDestinationStation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDestinationStation.setAdapter(adapterDestinationStation);

        recyclerView = findViewById(R.id.trainList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void updateData(View view) {
        Station departure=new Station(String.valueOf(spinnerDepartureStation.getSelectedItem()));
        Station destination=new Station(String.valueOf(spinnerDestinationStation.getSelectedItem()));

        if (!departure.getShortCode().equals(destination.getShortCode())) {
            try {
                trains = DataSearch.getTrains(departure.getShortCode(), destination.getShortCode());
                mAdapter = new TrainAdapter(this, trains);
                recyclerView.setAdapter(mAdapter);
            } catch (Exception e) {
                Toast.makeText(this, "Datan hakeminen ei onnistunut: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
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
