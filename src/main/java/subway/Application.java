package subway;

import java.io.IOException;
import java.util.List;
import subway.controller.LineController;
import subway.controller.SectionController;
import subway.controller.StationController;
import subway.domain.Line;
import subway.domain.LineRepository;
import subway.domain.Station;
import subway.domain.StationRepository;
import subway.util.Check;
import subway.view.InputView;
import subway.view.OutputView;

public class Application {

    static final int STATION = 1;
    static final int LINE = 2;
    static final int SECTION = 3;
    static final int MAP = 4;
    static final int REGISTRATION = 1;
    static final int ELIMINATION = 2;
    static final int INQUIRY = 3;
    static final String QUIT = "Q";
    static final String BACK = "B";

    public static void main(String[] args) throws IOException {

        setInitialStations();
        setInitialLines();

        while (true) {
            String mainCommand = InputView.getMainCommand();

            int command = Integer.parseInt(mainCommand);

            if (mainCommand.equalsIgnoreCase(QUIT)) {
                return;
            }
            if (!Check.isNumeric(mainCommand)) {
                continue;
            }
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

    private static void setInitialLines() {
        LineRepository.addLine(new Line("2호선",
            StationRepository.getOrCreateStation("교대역"),
            StationRepository.getOrCreateStation("강남역"),
            StationRepository.getOrCreateStation("역삼역")
        ));
        LineRepository.addLine(new Line("3호선",
            StationRepository.getOrCreateStation("교대역"),
            StationRepository.getOrCreateStation("남부터미널역"),
            StationRepository.getOrCreateStation("양재역"),
            StationRepository.getOrCreateStation("매봉역")
        ));
        LineRepository.addLine(new Line("신분당선",
            StationRepository.getOrCreateStation("강남역"),
            StationRepository.getOrCreateStation("양재역"),
            StationRepository.getOrCreateStation("양재시민의숲역")
        ));
    }

    private static void setInitialStations() {
        List<String> names = List.of("교대역", "강남역", "역삼역", "남부터미널역", "양재역", "매봉역", "양재시민의숲역");

        for (String name : names) {
            StationRepository.addStation(new Station(name));
        }
    }

    private static void serveStationMenu() throws IOException {
        String command;
        int stationCommand;

        do {
            command = InputView.getStationCommand();
            stationCommand = Integer.parseInt(command);

            if (!Check.isNumeric(command)) {
                continue;
            }

            if (stationCommand == REGISTRATION) {
                StationController.registerStation();
                continue;
            }

            if (stationCommand == ELIMINATION) {
                StationController.deleteStation();
                continue;
            }

            if (stationCommand == INQUIRY) {
                StationController.showAllStations();
            }
        } while (!command.equalsIgnoreCase(BACK));
    }

    private static void serveLineMenu() throws IOException {

        String command;
        int lineCommand;

        //문자열로 비교하는게 실수로 문제가 발생할 여지가 적다
        do {
            command = InputView.getLineCommand();
            lineCommand = Integer.parseInt(command);

            if (!Check.isNumeric(command)) {
                continue;
            }

            if (lineCommand == REGISTRATION) {
                LineController.registerLine();
                continue;
            }

            if (lineCommand == ELIMINATION) {
                LineController.deleteLine();
                continue;
            }

            if (lineCommand == INQUIRY) {
                LineController.showAllLines();
            }
        } while (!command.equalsIgnoreCase(BACK));
    }

    //test

    private static void serveSectionMenu() throws IOException {
        String command;
        int sectionCommand;

        do {
            command = InputView.getSectionCommand();
            sectionCommand = Integer.parseInt(command);

            if (!Check.isNumeric(command)) {
                continue;
            }

            if (sectionCommand == REGISTRATION) {
                SectionController.registerSection();
                continue;
            }

            if (sectionCommand == ELIMINATION) {
                SectionController.deleteSection();
            }
        } while (!command.equalsIgnoreCase(BACK));
    }
}