package fi.vuorenkoski.kokkelitrains;

import org.json.JSONArray;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class DataSearch {

    // hakee yhdeltä asemalta lähtevät junat, parametreina lahtoasema ja maaraasema, palauttaa listan Junista
    public static ArrayList<Train> getTrains (String station, String destination) throws Exception {
        ArrayList<Train> trains=new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date currentTime = new Date(System.currentTimeMillis());

        //  URL urli=new URL("https://rata.digitraffic.fi/api/v1/live-trains?station="+asema+"&departing_trains=70&train");
        // haku aikamäärällä, mutta palauttaa paljon junia
        // URL urli=new URL("https://rata.digitraffic.fi/api/v1//live-trains/station/"+asema+"?minutes_before_departure=15&minutes_after_departure=15&minutes_before_arrival=240&minutes_after_arrival=15&train_categories=Commuter");

        URL url=new URL("https://rata.digitraffic.fi/api/v1/live-trains/station/"+station+"?departing_trains=50&train_categories=Commuter");
        Scanner fileReader = new Scanner(url.openStream());
        JSONArray data=new JSONArray(fileReader.nextLine());

        for (int i=0;i<data.length();i++)
        {
            String lineIdStr=data.getJSONObject(i).getString("commuterLineID");
            if (lineIdStr.length()==1) { // kauko ym. junilla tämä koodi on tyhjä
                char lineId=lineIdStr.charAt(0);
                JSONArray timeTableRows=data.getJSONObject(i).getJSONArray("timeTableRows"); // Aikautalutiedot

                int number=data.getJSONObject(i).getInt("trainNumber");

                // Junan koko reitin aikataulutiedot on asemittain, erikseen DEPARTURE ja ARRIVAL
                // ensin etsitään asema jolta halutaan lähteä
                boolean departureStationFound=false;
                boolean arrivalStationFound=false;
                boolean estimateExists=false;
                Date estimateTime=new Date();
                Date departureTime=new Date();
                Date arrivalTime=new Date();
                String finalStation="";
                boolean cancelled=false;
                String track="";
                String causes=null;

                for (int j=0;j<timeTableRows.length();j++) {
                    if (timeTableRows.getJSONObject(j).getString("stationShortCode").equals(station) &&
                            timeTableRows.getJSONObject(j).getString("type").equals("DEPARTURE") &&
                            timeTableRows.getJSONObject(j).getBoolean("trainStopping")) {
                        departureStationFound=true;

                        departureTime=dateFormat.parse(timeTableRows.getJSONObject(j).getString("scheduledTime"));
                        if (timeTableRows.getJSONObject(j).has("liveEstimateTime")) {
                            estimateTime=dateFormat.parse(timeTableRows.getJSONObject(j).getString("liveEstimateTime"));
                            estimateExists=true;
                        }
                        cancelled=timeTableRows.getJSONObject(j).getBoolean("cancelled");
                        track=timeTableRows.getJSONObject(j).getString("commercialTrack");
                    }
                    if (departureStationFound && timeTableRows.getJSONObject(j).getString("stationShortCode").equals(destination) &&
                            timeTableRows.getJSONObject(j).getString("type").equals("ARRIVAL") &&
                            timeTableRows.getJSONObject(j).getBoolean("trainStopping")) {
                        arrivalStationFound=true;
                        arrivalTime=dateFormat.parse(timeTableRows.getJSONObject(j).getString("scheduledTime"));
                    }
                    finalStation=timeTableRows.getJSONObject(j).getString("stationShortCode");
                    causes=timeTableRows.getJSONObject(j).getJSONArray("causes").toString();
                }

                if (!estimateExists) estimateTime=departureTime;

                if (arrivalStationFound && estimateTime.compareTo(currentTime)>0) {
                    trains.add(new Train(number,station,lineId, track, departureTime, cancelled, estimateExists, estimateTime, arrivalTime, destination, finalStation, causes));
                }
            }
        }
        return trains.stream().sorted().collect(Collectors.toCollection(ArrayList::new));
    }

}
