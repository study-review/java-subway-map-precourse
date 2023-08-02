package subway.controller;

import java.io.IOException;
import java.util.Objects;
import subway.domain.Line;
import subway.domain.LineRepository;
import subway.domain.Station;
import subway.domain.StationRepository;
import subway.view.InputView;
import subway.view.OutputView;

public class SectionController {

    private SectionController() {}

    public static void registerSection() throws IOException {
        String lineName = InputView.getTargetName("## 노선을 입력하세요.");
        Line line = LineRepository.findByName(lineName);
        if (Objects.isNull(line)) {
            OutputView.printError("등록된 노선의 이름을 입력하세요.");
            return;
        }

        String stationName = InputView.getTargetName("## 역이름을 입력하세요.");
        try {
            line.checkDuplicateStationByName(stationName);
        } catch (RuntimeException e) {
            OutputView.printError(e.getMessage());
            return;
        }

        Station station = StationRepository.getOrCreateStation(stationName);
        int stationOrder = InputView.getStationOrder("## 순서를 입력하세요.");
        line.insertStation(station, stationOrder);
    }

    public static void deleteSection() throws IOException {
        String lineName = InputView.getTargetName("## 삭제할 구간의 노선을 입력하세요.");
        Line line = LineRepository.findByName(lineName);
        if (Objects.isNull(line)) {
            OutputView.printError("등록된 노선의 이름을 입력하세요.");
            return;
        }

        if (!line.checkIfStationRemovable()) {
            OutputView.printError("노선에 포함된 역이 두 개 이하일 때는 구간을 제거할 수 없습니다.");
            return;
        }

        String stationName = InputView.getTargetName("## 삭제할 구간의 역을 입력하세요.");
        try {
            line.checkRegisteredStationByName(stationName);
            line.deleteStationByName(stationName);
            OutputView.printSuccess("구간", "삭제");
        } catch (RuntimeException e) {
            OutputView.printError(e.getMessage());
        }
    }
}