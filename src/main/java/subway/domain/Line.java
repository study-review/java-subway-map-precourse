package subway.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<Station> getStations() {
        return stations;
    }
}
