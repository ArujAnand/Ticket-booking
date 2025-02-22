package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TrainService {

    private List<Train> trainList;

    private ObjectMapper ObjectMapper = new ObjectMapper();

    private static final String TRAINS_PATH = "C:\\Users\\Dell\\Documents\\Java\\Projects\\IRCTC\\src\\main\\java\\ticket\\booking\\localDB\\trains.json";

    /**
     * loads train data from JSON database
     * @return a list of trains stored in the DB
     * @throws IOException If an error occurs while fetching the train list from DB
     */
    private List<Train> loadTrains() throws IOException {
        File trains = new File(TRAINS_PATH);

        System.out.println("Trains loaded using loadTrain function");

        return ObjectMapper.readValue(trains, new TypeReference<List<Train>>() {});
    }

    /**
     * default constructor, loads trains DB on object creation
     * @throws IOException If error occurs on loading train data from DB using function {@link #loadTrains()}
     */
    public TrainService() throws IOException {
        this.trainList = loadTrains();
//        to check data being read from JSON
//        String jsonString = ObjectMapper.writeValueAsString(trainList);
//        System.out.println("Serialized JSON:\n" + jsonString);
    }

    //similar to userBookingService

    /**
     * @param source source station
     * @param destination destination station
     * @return List of trains going from source to destination
     */
    public List<Train> searchTrains(String source, String destination) {
        return trainList.stream()
                .filter(train -> validTrain(train, source, destination))
                .collect(Collectors.toList());
    }

    /**
     * checks whether destination is after source
     * @param train train in consideration
     * @param source source station name
     * @param destination destination station name
     * @return Return TRUE if destination is after source else FALSE
     */
    private boolean validTrain(Train train, String source, String destination) {

        List<String> stationOrder = train.getStations();
        stationOrder.replaceAll(String::toLowerCase);
//        System.out.println(stationOrder);

        int sourceIndex = stationOrder.indexOf(source);
        int destinationIndex = stationOrder.indexOf(destination);

        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
    }
}
