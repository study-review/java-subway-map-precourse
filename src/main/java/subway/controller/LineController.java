package subway.controller;

import subway.domain.Line;
import subway.domain.LineRepository;
import subway.domain.Station;
import subway.domain.StationRepository;
import subway.view.InputView;
import subway.view.OutputView;

public class LineController {

    private LineController() {}

    public static void registerLine() {
        String lineName = InputView.getTargetName("## 등록할 노선 이름을 입력하세요.");
        String firstStationName = InputView.getTargetName("## 등록할 노선의 상행 종점역 이름을 입력하세요.");
        String lastStationName = InputView.getTargetName("## 등록할 노선의 하행 종점역 이름을 입력하세요.");

        Station firstStation = StationRepository.getOrCreateStation(firstStationName);
        Station lastStation = StationRepository.getOrCreateStation(lastStationName);

        try {
            LineRepository.checkIfDuplicate(lineName);
            LineRepository.addLine(new Line(lineName, firstStation, lastStation));
            OutputView.printSuccess("지하철 노선", "등록");
        } catch (RuntimeException e) {
            OutputView.printError(e.getMessage());
        }
    }

    public static void deleteLine() {
        String lineName = InputView.getTargetName("## 삭제할 노선 이름을 입력하세요.");
        try {
            LineRepository.deleteLineByName(lineName);
            OutputView.printSuccess("지하철 노선", "삭제");
        } catch (RuntimeException e) {
            OutputView.printError(e.getMessage());
        }
    }

    public static void showAllLines() {
        OutputView.printInquiry("노선", LineRepository.getAllNames());
    }
}
