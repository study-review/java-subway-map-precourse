package subway;

import java.util.Objects;
import java.util.Scanner;
import subway.domain.Line;
import subway.domain.LineRepository;
import subway.domain.Station;
import subway.domain.StationRepository;
import subway.view.InputView;
import subway.view.OutputView;

public class Application {

    static final int REGISTRATION = 1;
    static final int ELIMINATION = 2;
    static final int INQUIRY = 3;
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int STATION = 1;
        final int LINE = 2;
        final int SECTION = 3;

        // TODO: 프로그램 구현
        while (true) {
            String mainCommand = InputView.getMainCommand();
            if (mainCommand.equals("Q")) {
                return;
            }
            if (Integer.parseInt(mainCommand) == STATION) {
                serveStationMenu();
                continue;
            }
            if (Integer.parseInt(mainCommand) == LINE) {
                serveLineMenu();
                continue;
            }
            if (Integer.parseInt(mainCommand) == SECTION) {
                serveSectionMenu();
            }
        }
    }

    private static void serveStationMenu() {
        while (true) {
            String command = InputView.getStationCommand();
            if (command.equals("B")) {
                return;
            }
            // 역 등록
            if (Integer.parseInt(command) == REGISTRATION) {
                OutputView.customPrint("## 등록할 역 이름을 입력하세요.");
                String stationName = InputView.getTargetName();
                try {
                    StationRepository.checkIfDuplicate(stationName);
                    StationRepository.addStation(new Station(stationName));
                    OutputView.printSuccess("지하철 역", "등록");
                } catch(RuntimeException e) {
                    OutputView.printError(e.getMessage());
                }
                continue;
            }
            // 역 삭제
            if (Integer.parseInt(command) == ELIMINATION) {
                OutputView.customPrint("## 삭제할 역 이름을 입력하세요.");
                try {
                    StationRepository.deleteStation(InputView.getTargetName());
                    OutputView.printSuccess("지하철 역", "삭제");
                } catch (RuntimeException e) {
                    OutputView.printError(e.getMessage());
                }
                continue;
            }
            // 역 조회
            if (Integer.parseInt(command) == INQUIRY) {
                OutputView.printInquiry("역", StationRepository.getAllNames());
            }
        }
    }

    private static void serveLineMenu() {
        while (true) {
            String command = InputView.getLineCommand();
            if (command.equals("B")) {
                return;
            }
            // 노선 등록
            if (Integer.parseInt(command) == REGISTRATION) {
                OutputView.customPrint("## 등록할 노선 이름을 입력하세요.");
                String lineName = InputView.getTargetName();
                OutputView.customPrint("## 등록할 노선의 상행 종점역 이름을 입력하세요.");
                String firstStationName = InputView.getTargetName();
                OutputView.customPrint("## 등록할 노선의 하행 종점역 이름을 입력하세요.");
                String lastStationName = InputView.getTargetName();

                Station firstStation = StationRepository.findByName(firstStationName);
                if (Objects.isNull(firstStation)) {
                    firstStation = new Station(firstStationName);
                    StationRepository.addStation(firstStation);
                }
                Station lastStation = StationRepository.findByName(lastStationName);
                if (Objects.isNull(lastStation)) {
                    lastStation = new Station(lastStationName);
                    StationRepository.addStation(lastStation);
                }

                try {
                    LineRepository.checkIfDuplicate(lineName);
                    LineRepository.addLine(new Line(lineName, firstStation, lastStation));
                    OutputView.printSuccess("지하철 노선", "등록");
                } catch (RuntimeException e) {
                    OutputView.printError(e.getMessage());
                }
                continue;
            }
            // 노선 삭제
            if (Integer.parseInt(command) == ELIMINATION) {
                OutputView.customPrint("## 삭제할 노선 이름을 입력하세요.");
                try {
                    LineRepository.deleteLineByName(InputView.getTargetName());
                    OutputView.printSuccess("지하철 노선", "삭제");
                } catch (RuntimeException e) {
                    OutputView.printError(e.getMessage());
                }
                continue;
            }
            // 노선 조회
            if (Integer.parseInt(command) == INQUIRY) {
                OutputView.printInquiry("노선", LineRepository.getAllNames());
            }
        }
    }

    private static void serveSectionMenu() {
        while (true) {
            String command = InputView.getSectionCommand();
            if (command.equals("B")) {
                return;
            }
            // 구간 등록
            if (Integer.parseInt(command) == REGISTRATION) {
                OutputView.customPrint("## 노선을 입력하세요.");
                String lineName = InputView.getTargetName();
                Line line = LineRepository.findByName(lineName);
                if (Objects.isNull(line)) {
                    OutputView.printError("등록된 노선의 이름을 입력하세요.");
                    continue;
                }

                OutputView.customPrint("## 역이름을 입력하세요.");
                String stationName = InputView.getTargetName();
                try {
                    line.checkDuplicateStationByName(stationName);
                } catch (RuntimeException e) {
                    OutputView.printError(e.getMessage());
                    continue;
                }
                Station station = StationRepository.findByName(stationName);
                if (Objects.isNull(station)) {
                    station = new Station(stationName);
                    StationRepository.addStation(station);
                }

                OutputView.customPrint("## 순서를 입력하세요.");
                int stationOrder = InputView.getStationOrder();
                line.insertStation(station, stationOrder);
                continue;
            }
            // 구간 삭제
            if (Integer.parseInt(command) == ELIMINATION) {
                OutputView.customPrint("## 삭제할 구간의 노선을 입력하세요.");
                String lineName = InputView.getTargetName();
                Line line = LineRepository.findByName(lineName);
                if (Objects.isNull(line)) {
                    OutputView.printError("등록된 노선의 이름을 입력하세요.");
                    continue;
                }

                if (!line.checkIfStationRemovable()) {
                    OutputView.printError("노선에 포함된 역이 두 개 이하일 때는 구간을 제거할 수 없습니다.");
                    continue;
                }

                OutputView.customPrint("## 삭제할 구간의 역을 입력하세요.");
                String stationName = InputView.getTargetName();
                try {
                    line.deleteStationByName(stationName);
                    OutputView.printSuccess("구간", "삭제");
                } catch (RuntimeException e) {
                    OutputView.printError(e.getMessage());
                }
            }
        }
    }
}
