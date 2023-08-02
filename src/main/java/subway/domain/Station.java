package subway.domain;

import java.util.LinkedList;
import java.util.List;

public class Station {
    private final String name;
    private final List<String> lineNames = new LinkedList<>();

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addLineName(String lineName) {
        lineNames.add(lineName);
    }

    public void deleteLineNames(String lineName) {
        lineNames.removeIf(lineName::equals);
    }

    public boolean checkIfRegisteredInLine() {
        return !lineNames.isEmpty();
    }

}