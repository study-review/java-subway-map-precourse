package subway.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import subway.view.InputView;
import subway.view.OutputView;

public class StationRepository {
    private static final List<Station> stations = new ArrayList<>();

    public static List<Station> stations() {
        return Collections.unmodifiableList(stations);
    }

    public static void addStation(Station station) {
        try {
            checkIfDuplicate(station);
            stations.add(station);
            OutputView.printSuccess("지하철 역", "등록");
        } catch(RuntimeException e) {
            OutputView.printError(e.getMessage());
        }
    }

    public static boolean deleteStation(String name) {
        return stations.removeIf(station -> Objects.equals(station.getName(), name));
    }

    private static void checkIfDuplicate(Station station) {
        for (Station s : stations()) {
            if (station.getName().equals(s.getName())) {
                throw new RuntimeException("이미 등록된 역 이름입니다.");
            }
        }
    }

    public static List<String> getAllNames() {
        List<String> names = new ArrayList<>();
        for (Station s : stations) {
            names.add(s.getName());
        }
        return names;
    }
}
