package subway.controller;

import subway.domain.Station;
import subway.domain.StationRepository;
import subway.view.InputView;
import subway.view.OutputView;

public class StationController {

    private StationController() {};

    public static void registerStation() {
        String stationName = InputView.getTargetName("## 등록할 역 이름을 입력하세요.");
        try {
            StationRepository.checkIfDuplicate(stationName);
            StationRepository.addStation(new Station(stationName));
            OutputView.printSuccess("지하철 역", "등록");
        } catch(RuntimeException e) {
            OutputView.printError(e.getMessage());
        }
    }

    public static void deleteStation() {
        String stationName = InputView.getTargetName("## 삭제할 역 이름을 입력하세요.");
        try {
            StationRepository.deleteStation(stationName);
            OutputView.printSuccess("지하철 역", "삭제");
        } catch (RuntimeException e) {
            OutputView.printError(e.getMessage());
        }
    }

    public static void showAllStations() {
        OutputView.printInquiry("역", StationRepository.getAllNames());
    }
}
