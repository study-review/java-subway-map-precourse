package subway;

import java.util.Scanner;
import subway.controller.LineController;
import subway.controller.SectionController;
import subway.controller.StationController;
import subway.util.Validator;
import subway.view.InputView;
import subway.view.OutputView;

public class Application {

    static final int REGISTRATION = 1;
    static final int ELIMINATION = 2;
    static final int INQUIRY = 3;
    static final String BACK = "B";
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int STATION = 1;
        final int LINE = 2;
        final int SECTION = 3;
        final int MAP = 4;

        // TODO: 프로그램 구현
        while (true) {
            String mainCommand = InputView.getMainCommand();
            if (mainCommand.equalsIgnoreCase("Q")) {
                return;
            }
            if (!Validator.isNumeric(mainCommand)) {
                continue;
            }
            int command = Integer.parseInt(mainCommand);
            if (command == STATION) {
                serveStationMenu();
                continue;
            }
            if (command == LINE) {
                serveLineMenu();
                continue;
            }
            if (command == SECTION) {
                serveSectionMenu();
                continue;
            }
            if (command == MAP) {
                OutputView.printSubwayMap();
            }
        }
    }

    private static void serveStationMenu() {
        String command;
        int stationCommand;
        do {
            command = InputView.getStationCommand();
            if (!Validator.isNumeric(command)) {
                continue;
            }
            stationCommand = Integer.parseInt(command);
            // 역 등록
            if (stationCommand == REGISTRATION) {
                StationController.registerStation();
                continue;
            }
            // 역 삭제
            if (stationCommand == ELIMINATION) {
                StationController.deleteStation();
                continue;
            }
            // 역 조회
            if (stationCommand == INQUIRY) {
                StationController.showAllStations();
            }
        } while(!command.equalsIgnoreCase(BACK));
    }

    private static void serveLineMenu() {
        String command;
        int lineCommand;
        do {
            command = InputView.getLineCommand();
            if (!Validator.isNumeric(command)) {
                continue;
            }
            lineCommand = Integer.parseInt(command);
            // 노선 등록
            if (lineCommand == REGISTRATION) {
                LineController.registerLine();
                continue;
            }
            // 노선 삭제
            if (lineCommand == ELIMINATION) {
                LineController.deleteLine();
                continue;
            }
            // 노선 조회
            if (lineCommand == INQUIRY) {
                LineController.showAllLines();
            }
        } while (!command.equalsIgnoreCase(BACK));
    }

    private static void serveSectionMenu() {
        String command;
        int sectionCommand;
        do {
            command = InputView.getSectionCommand();
            if (!Validator.isNumeric(command)) {
                continue;
            }
            sectionCommand = Integer.parseInt(command);
            // 구간 등록
            if (sectionCommand == REGISTRATION) {
                SectionController.registerSection();
                continue;
            }
            // 구간 삭제
            if (sectionCommand == ELIMINATION) {
                SectionController.deleteSection();
            }
        } while (!command.equalsIgnoreCase(BACK));
    }
}
