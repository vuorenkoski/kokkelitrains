package fi.vuorenkoski.kokkelitrains;

public class Station {
    private int station;
    private String shortCode;
    private String name;

    public Station(String station) {
        this.station=0;
        if (station.equals("HKI") || station.equals("Helsinki")) {
            this.station=1;
            this.shortCode="HKI";
            this.name="Helsinki";
        }
        if (station.equals("PSL") || station.equals("Pasila")) {
            this.station=2;
            this.shortCode="PSL";
            this.name="Pasila";
        }
        if (station.equals("ILA") || station.equals("Ilmala")) {
            this.station=3;
            this.shortCode="ILA";
            this.name="Ilmala";
        }
        if (station.equals("HPL") || station.equals("Huopalahti")) {
            this.station=4;
            this.shortCode="HPL";
            this.name="Huopalahti";
        }
        if (station.equals("VMO") || station.equals("Valimo")) {
            this.station=5;
            this.shortCode="VMO";
            this.name="Valimo";
        }
        if (station.equals("PJM") || station.equals("Pitäjänmäki")) {
            this.station=6;
            this.shortCode="PJM";
            this.name="Pitäjänmäki";
        }
        if (station.equals("MÄK") || station.equals("Mäkkylä")) {
            this.station=7;
            this.shortCode="MÄK";
            this.name="Mäkkylä";
        }
        if (station.equals("LPV") || station.equals("Leppävaara")) {
            this.station=8;
            this.shortCode="LPV";
            this.name="Leppävaara";
        }
        if (station.equals("KIL") || station.equals("Kilo")) {
            this.station=9;
            this.shortCode="KIL";
            this.name="Kilo";
        }
        if (station.equals("KEA") || station.equals("Kera")) {
            this.station=10;
            this.shortCode="KEA";
            this.name="Kera";
        }
        if (station.equals("KNI") || station.equals("Kauniainen")) {
            this.station=11;
            this.shortCode="KNI";
            this.name="Kauniainen";
        }
        if (station.equals("KVH") || station.equals("Koivuhovi")) {
            this.station = 12;
            this.shortCode = "KVH";
            this.name = "Koivuhovi";
        }
        if (station.equals("TRL") || station.equals("Tuomarila")) {
            this.station=13;
            this.shortCode="TRL";
            this.name="Tuomarila";
        }
        if (station.equals("EPO") || station.equals("Espoo")) {
            this.station=14;
            this.shortCode="EPO";
            this.name="Espoo";
        }
        if (station.equals("KLH") || station.equals("Kauklahti")) {
            this.station=15;
            this.shortCode="KLH";
            this.name="Kauklahti";
        }

        if (station.equals("MAS") || station.equals("Masala")) {
            this.station=16;
            this.shortCode="MAS";
            this.name="Masala";
        }

        if (station.equals("JRS") || station.equals("Jorvas")) {
            this.station=17;
            this.shortCode="JRS";
            this.name="Jorvas";
        }

        if (station.equals("TOL") || station.equals("Tolsa")) {
            this.station=18;
            this.shortCode="TOL";
            this.name="Tolsa";
        }

        if (station.equals("KKN") || station.equals("Kirkkonummi")) {
            this.station=19;
            this.shortCode="KKN";
            this.name="Kirkkonummi";
        }

        if (station.equals("STI") || station.equals("Siuntio")) {
            this.station=20;
            this.shortCode="STI";
            this.name="Siuntio";
        }

    }

    public String getShortCode() {
        return shortCode;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

