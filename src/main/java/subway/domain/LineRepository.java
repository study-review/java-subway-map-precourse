package subway.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class LineRepository {
    private static final List<Line> lines = new ArrayList<>();

    public static List<Line> lines() {
        return Collections.unmodifiableList(lines);
    }

    public static void addLine(Line line) {
        lines.add(line);
    }

    public static boolean deleteLineByName(String name) {
        Line line = findByName(name);
        if (Objects.isNull(line)) {
            throw new RuntimeException("등록된 노선의 이름을 입력하세요.");
        }
        line.dismissAllStations();
        return lines.remove(line);
    }

    public static void checkIfDuplicate(String lineName) {
        if (!Objects.isNull(findByName(lineName))) {
            throw new RuntimeException("이미 등록된 노선 이름입니다.");
        }
    }

    public static Line findByName(String name) {
        for (Line line : lines()) {
            if (name.equals(line.getName())) {
                return line;
            }
        }
        return null;
    }

    public static List<String> getAllNames() {
        List<String> names = new ArrayList<>();
        for (Line line : lines()) {
            names.add(line.getName());
        }
        return names;
    }
}
