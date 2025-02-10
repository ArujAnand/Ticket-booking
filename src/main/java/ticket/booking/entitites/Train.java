package ticket.booking.entitites;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Map;

public class Train {
    private String trainId;
    private int trainNo;
    private List<List<Integer>> seats;
    private Map <String, String> stationTimes;
    private List <String> stations;

    public Train(String trainId, int trainNo, List<List<Integer>> seats, Map<String, String> stationTimes, List<String> stations) {
        this.trainId = trainId;
        this.trainNo = trainNo;
        this.seats = seats;
        this.stationTimes = stationTimes;
        this.stations = stations;
    }

    public Train() {
    }
    //getters

    public String getTrainId() {
        return trainId;
    }

    public int getTrainNo() {
        return trainNo;
    }

    public List<List<Integer>> getSeats() {
        return seats;
    }

    public Map<String, String> getStationTimes() {
        return stationTimes;
    }

    public List<String> getStations() {
        return stations;
    }

    //setters
    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public void setTrainNo(int trainNo) {
        this.trainNo = trainNo;
    }

    public void setSeats(List<List<Integer>> seats) {
        this.seats = seats;
    }

    public void setStationTimes(Map<String, String> stationTimes) {
        this.stationTimes = stationTimes;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    @JsonIgnore
    public String getTrainInfo() {
        return String.format("Train ID: %s  Train No: %d", this.trainId, this.trainNo);
    }
}
