package fi.vuorenkoski.kokkelitrains;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Train implements Comparable<Train> {
    private int number;
    private Station departureStation;
    private char lineId;
    private String track;
    private Date departureTime;
    private boolean cancelled;
    private boolean estimateExists;
    private Date estimatedTime;
    private Date arrivalTime;
    private Station arrivalStation;
    private Station finalStation;
    private String causes;

    private String speed;

    private int late;

    public Train(int number, String departureStation, char lineId, String track, Date departureTime, boolean cancelled, boolean estimateExists, Date estimatedTime, Date arrivalTime, String arrivalStation, String finalStation, String causes) {
        this.number=number;
        this.departureStation=new Station(departureStation);
        this.lineId = lineId;
        this.track = track;
        this.departureTime = departureTime;
        this.cancelled = cancelled;
        this.estimateExists = estimateExists;
        this.estimatedTime = estimatedTime;
        this.arrivalTime = arrivalTime;
        this.arrivalStation=new Station(arrivalStation);
        this.finalStation = new Station(finalStation);
        this.late = (int)((estimatedTime.getTime()-departureTime.getTime())/1000);
        if (this.late<60) this.late=0;
        this.causes=causes;

        this.speed=null;
    }

    public String getDepartureTimeStr() {
        return timeString(departureTime);
    }

    public String getEstimatedTimeStr() {
        return timeString(estimatedTime);
    }

    public String getArrivalTimeStr() {
        return timeString(arrivalTime);
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public char getLineId() {
        return lineId;
    }


    public String getSpeed() {
        return speed;
    }

    public String getTrack() {
        return track;
    }

    public String getCorrectedDepartureTimeStr() {
        if (this.late!=0) {
            return timeString(departureTime)+"-->"+timeString(estimatedTime);
        } else return timeString(departureTime);
    }

    public String getCauses() {
        return causes;
    }

    public String getNotification() {
        if (this.cancelled) return "Peruttu";
        if (this.estimateExists && !this.getDepartureTimeStr().equals(this.getEstimatedTimeStr())) return "->"+this.getEstimatedTimeStr();
        return "";
    }

    private String timeString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        if (this.cancelled) return ""+lineId+" "+this.getDepartureTimeStr()+" PERUTTU";
        if (this.late!=0) return ""+lineId+" "+track+" "+this.getDepartureTimeStr()+"-->"+this.getEstimatedTimeStr()+"  ("+this.arrivalStation+" "+this.getArrivalTimeStr()+")";
        return ""+lineId+" "+track+" "+this.getEstimatedTimeStr()+"  ("+this.arrivalStation+" "+this.getArrivalTimeStr()+")";
    }

    @Override
    public int compareTo(Train t) {
        return (int) (this.departureTime.getTime()-t.getDepartureTime().getTime());
    }

}
