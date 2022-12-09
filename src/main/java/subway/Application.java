package subway;

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
                // 구간 등록
                // 구간 삭제
                System.out.println(InputView.getSectionMenu());
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
                boolean isDeleted = StationRepository.deleteStation(InputView.getTargetName());
                if (isDeleted) {
                    OutputView.printSuccess("지하철 역", "삭제");
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
                if (firstStation == null) {
                    firstStation = new Station(firstStationName);
                    StationRepository.addStation(firstStation);
                }
                Station lastStation = StationRepository.findByName(lastStationName);
                if (lastStation == null) {
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
                boolean isDeleted = LineRepository.deleteLineByName(InputView.getTargetName());
                if (isDeleted) {
                    OutputView.printSuccess("지하철 노선", "삭제");
                }
                continue;
            }
            // 노선 조회
            if (Integer.parseInt(command) == INQUIRY) {
                OutputView.printInquiry("노선", LineRepository.getAllNames());
            }
        }
    }
}
