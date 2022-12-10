package subway.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Line {
    private final String name;
    private final List<Station> stations = new LinkedList<>();

    public Line(String name, Station ...stations) {
        this.name = name;
        for (Station station : stations) {
            station.addLineName(name);
            this.stations.add(station);
        }
    }

    public String getName() {
        return name;
    }

    // 추가 기능 구현
    public void insertStation(Station station, int order) {
        int index = Math.min(order - 1, stations.size());
        station.addLineName(name);
        stations.add(index, station);
    }

    public void checkDuplicateStationByName(String name) {
        if (!Objects.isNull(findStationByName(name))) {
            throw new RuntimeException("해당 역이 이미 노선에 등록되어 있습니다.");
        }
    }

    public Station findStationByName(String name) {
        for (Station station : stations) {
            if (name.equals(station.getName())) {
                return station;
            }
        }
        return null;
    }

    public boolean checkIfStationRemovable() {
        return stations.size() > 2;
    }

    public void deleteStationByName(String name) {
        Station station = findStationByName(name);
        if (Objects.isNull(station)) {
            throw new RuntimeException("해당 노선에 등록된 역의 이름을 입력하세요.");
        }
        station.deleteLineNames(this.name);
        stations.remove(station);
    }

    public void dismissAllStations() {
        for (Station station : stations) {
            station.deleteLineNames(this.name);
        }
    }

    public List<String> getAllStationNames() {
        List<String> names = new ArrayList<>();
        for (Station station : stations) {
            names.add(station.getName());
        }
        return names;
    }
}
