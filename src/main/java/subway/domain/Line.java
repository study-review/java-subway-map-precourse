package subway.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Line {
    private String name;
    private List<Station> stations;

    public Line(String name, Station firstStation, Station lastStation) {
        if (firstStation == lastStation) {
            throw new RuntimeException("서로 다른 종착역을 입력하세요.");
        }
        this.name = name;
        stations = new ArrayList<>(Arrays.asList(firstStation, lastStation));
    }

    public String getName() {
        return name;
    }

    // 추가 기능 구현
    public void insertStation(Station station, int order) {
        int index = Math.min(order - 1, stations.size());
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

    public boolean deleteStationByName(String name) {
        return stations.removeIf(station -> Objects.equals(station.getName(), name));
    }

    public void printStations() {
        for (Station station : stations) {
            System.out.print(station.getName() + " ");
        }
        System.out.println();
    }
}
