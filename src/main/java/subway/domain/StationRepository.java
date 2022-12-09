package subway.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class StationRepository {
    private static final List<Station> stations = new ArrayList<>();

    public static List<Station> stations() {
        return Collections.unmodifiableList(stations);
    }

    public static void addStation(Station station) {
        stations.add(station);
    }

    public static boolean deleteStation(String name) {
        return stations.removeIf(station -> Objects.equals(station.getName(), name));
    }

    public static void checkIfDuplicate(String name) {
        if (!Objects.isNull(findByName(name))) {
            throw new RuntimeException("이미 등록된 역 이름입니다.");
        }
    }

    public static List<String> getAllNames() {
        List<String> names = new ArrayList<>();
        for (Station s : stations()) {
            names.add(s.getName());
        }
        return names;
    }

    public static Station findByName(String name) {
        for (Station s : stations()) {
            if (name.equals(s.getName())) {
                return s;
            }
        }
        return null;
    }
}
