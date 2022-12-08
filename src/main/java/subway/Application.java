package subway;

import java.util.Scanner;
import subway.domain.Station;
import subway.domain.StationRepository;
import subway.view.InputView;
import subway.view.OutputView;

public class Application {
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
                // 노선 등록
                // 노선 삭제
                // 노선 조회
                System.out.println(InputView.getLineMenu());
                continue;
            }
            if (Integer.parseInt(mainCommand) == SECTION) {
                // 구간 등록
                // 구간 삭제
                System.out.println(InputView.getSectionMenu());
            }
        }
    }

    public static void serveStationMenu() {
        final int REGISTRATION = 1;
        final int ELIMINATION = 2;
        final int INQUIRY = 3;
        while (true) {
            String command = InputView.getStationCommand();
            if (command.equals("B")) {
                return;
            }
            System.out.println();
            // 역 등록
            if (Integer.parseInt(command) == REGISTRATION) {
                System.out.println("## 등록할 역 이름을 입력하세요.");
                StationRepository.addStation(new Station(InputView.getTargetName()));
                continue;
            }
            // 역 삭제
            if (Integer.parseInt(command) == ELIMINATION) {
                System.out.println("## 삭제할 역 이름을 입력하세요.");
                StationRepository.deleteStation(InputView.getTargetName());
                OutputView.printSuccess("지하철 역", "삭제");
                continue;
            }
            // 역 조회
            if (Integer.parseInt(command) == INQUIRY) {
                OutputView.printInquiry("역", StationRepository.getAllNames());
            }
        }
    }
}
