package subway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import subway.domain.Line;
import subway.domain.LineRepository;
import subway.domain.Station;
import subway.domain.StationRepository;
import subway.util.Validator;

public class MapTest {

    @BeforeEach
    void resetAll() {
        List<String> lineNames = LineRepository.getAllNames();
        for (String name : lineNames) {
            LineRepository.deleteLineByName(name);
        }

        List<String> stationNames = StationRepository.getAllNames();
        for (String name : stationNames) {
            StationRepository.deleteStation(name);
        }
    }

    @Nested
    @DisplayName("유틸성 클래스인 Validator 의 isNumeric 에 대한 테스트")
    class isNumeric {

        @Test
        @DisplayName("문자열 '1'을 입력하면 true 반환")
        void isNumeric_ShouldReturnTrueForStringOne() {
            // given
            String input = "1";
            // when
            boolean result = Validator.isNumeric(input);
            // then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("문자열 'q'를 입력하면 false 반환")
        void isNumeric_ShouldReturnFalseForStringQ() {
            // given
            String input = "q";
            // when
            boolean result = Validator.isNumeric(input);
            // then
            assertThat(result).isFalse();
        }
    }

    @Nested
    @DisplayName("노선 객체의 findStationByName 에 대한 테스트")
    class FindStationByName {

        @Test
        @DisplayName("강남역을 입력하면 강남역이라는 이름을 가진 Station 인스턴스 반환")
        void findStationByName_ShouldReturnStationForGangNam() {
            // given
            Station station1 = new Station("강남역");
            Station station2 = new Station("양재역");
            Line line = new Line("신분당선", station1, station2);
            String name = "강남역";
            // when
            Station result = line.findStationByName(name);
            Station expectedResult = new Station("강남역");
            expectedResult.addLineName("신분당선");
            // then
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
        }

        @Test
        @DisplayName("정자역을 입력하면 null 반환")
        void findStationByName_ShouldReturnNullForJungJa() {
            // given
            Station station1 = new Station("강남역");
            Station station2 = new Station("양재역");
            Line line = new Line("신분당선", station1, station2);
            String name = "정자역";
            // when
            Station result = line.findStationByName(name);
            // then
            assertThat(result).isNull();
        }
    }

    @Nested
    @DisplayName("노선 객체에 이미 등록된 역인지 확인하는 메소드(checkDuplicateStationByName)에 대한 테스트")
    class CheckDuplicateStationByName {

        @Test
        @DisplayName("강남역을 입력하면 RunTimeException 발생")
        void checkDuplicateStationByName_ShouldReturnRunTimeExceptionForJungJaStation() {
            // given
            Station station1 = new Station("강남역");
            Station station2 = new Station("양재역");
            Line line = new Line("신분당선", station1, station2);
            // when
            String name = "강남역";
            // then
            assertThatThrownBy(() -> line.checkDuplicateStationByName(name)).isInstanceOf(RuntimeException.class);
        }

        @Test
        @DisplayName("강남역을 입력하면 '해당 역이 이미 노선에 등록되어 있습니다.' 라는 매시지를 가진 예외 발생")
        void checkDuplicateStationByName_ShouldReturnExceptionWithMessageForJungJaStation() {
            // given
            Station station1 = new Station("강남역");
            Station station2 = new Station("양재역");
            Line line = new Line("신분당선", station1, station2);
            // when
            String name = "강남역";
            // then
            Throwable exception = assertThrows(RuntimeException.class, () -> {
                line.checkDuplicateStationByName(name);
            });
            assertEquals("해당 역이 이미 노선에 등록되어 있습니다.", exception.getMessage());
        }
    }

    @Test
    @DisplayName("정자역, 2를 입력하면 stations 에 정자역이 추가됨")
    void insertStation_ShouldReturnStationsWithTheStationForGangNamStation() {
        // given
        Station station1 = new Station("강남역");
        Station station2 = new Station("양재역");
        Line line = new Line("신분당선", station1, station2);
        // when
        Station jungJa = new Station("정자역");
        line.insertStation(jungJa, 2);
        jungJa.addLineName("신분당선");
        Station result = line.findStationByName("정자역");
        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(jungJa);
    }

    @Nested
    @DisplayName("노선 객체의 역을 삭제할 수 있는지 확인하는 메소드(checkIfStationRemovable) 에 대한 테스트")
    class CheckIfStationRemovable {

        @Test
        @DisplayName("강남역, 양재역만 있을때 false 반환")
        void checkIfStationRemovable_ShouldReturnFalseForOnlyTwoStations() {
            // given
            Station station1 = new Station("강남역");
            Station station2 = new Station("양재역");
            Line line = new Line("신분당선", station1, station2);
            // when
            boolean result = line.checkIfStationRemovable();
            // then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("강남역, 양재역, 정자역 있을때 true 반환")
        void checkIfStationRemovable_ShouldReturnFalseForThreeStations() {
            // given
            Station station1 = new Station("강남역");
            Station station2 = new Station("양재역");
            Line line = new Line("신분당선", station1, station2);
            // when
            Station station3 = new Station("정자역");
            line.insertStation(station3, 2);
            boolean result = line.checkIfStationRemovable();
            // then
            assertThat(result).isTrue();
        }
    }

    @Test
    @DisplayName("정자역 입력하면 '해당 노선에 등록된 역의 이름을 입력하세요.'라는 메시지를 가진 RunTimeException 발생")
    void checkRegisteredStationByName_ShouldReturnRunTimeExceptionWithMessageForJungJaStation() {
        // given
        Station station1 = new Station("강남역");
        Station station2 = new Station("양재역");
        Line line = new Line("신분당선", station1, station2);
        // when

        // then
        Throwable exception = assertThrows(RuntimeException.class, () -> line.checkRegisteredStationByName("정자역"));
        assertEquals("해당 노선에 등록된 역의 이름을 입력하세요.", exception.getMessage());
    }

    @Test
    @DisplayName("정자역 입력하면 stations 에 정자역이 제거됨")
    void deleteStationByName_ShouldReturnStationsWithOutJungJaForJungJaStation() {
        // given
        Station station1 = new Station("강남역");
        Station station2 = new Station("양재역");
        Station station3 = new Station("정자역");
        Line line = new Line("신분당선", station1, station2, station3);
        String name = "정자역";
        // when
        line.deleteStationByName(name);
        Station result = line.findStationByName(name);
        // then
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("실행하면 [강남역, 양재역] 문자열 리스트를 반환")
    void getAllStationNames_ShouldReturnStringList() {
        // given
        Station station1 = new Station("강남역");
        Station station2 = new Station("양재역");
        Line line = new Line("신분당선", station1, station2);
        // when
        List<String> result = line.getAllStationNames();
        List<String> expectedResult = new ArrayList<>(Arrays.asList("강남역", "양재역"));
        // then
        assertThat(result).containsExactlyElementsOf(expectedResult);
    }

    @Test
    @DisplayName("신분당선 객체 입력하면 lines 에 추가됨")
    void addLine_ShouldReturnLinesWithNewLineForTheLine() {
        // given
        Line line = new Line("신분당선", new Station("강남역"), new Station("양재역"));
        // when
        LineRepository.addLine(line);
        List<Line> result = LineRepository.lines();
        // then
        assertThat(result).containsExactly(line);
    }

    @Test
    @DisplayName("신분당선 입력하면 lines 에서 제거됨")
    void deleteLineByName_ShouldReturnLinesWithOutLineForInsertedLineName() {
        // given
        String name = "신분당선";
        Line line = new Line(name, new Station("강남역"), new Station("양재역"));
        LineRepository.addLine(line);
        // when
        LineRepository.deleteLineByName(name);
        List<Line> result = LineRepository.lines();
        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("신분당선 입력하면 '등록된 노선의 이름을 입력하세요.' 라는 메시지를 가진 RunTimeException 발생")
    void deleteLineByName_ShouldReturnRunTimeExceptionForUnknownLineName() {
        // given
        String name = "신분당선";
        // when

        // then
        Throwable exception = assertThrows(RuntimeException.class, () -> LineRepository.deleteLineByName(name));
        assertEquals("등록된 노선의 이름을 입력하세요.", exception.getMessage());
    }

    @Test
    @DisplayName("신분당선 입력하면 '이미 등록된 노선 이름입니다.' 라는 메시지를 가진 RunTimeException 발생")
    void checkIfDuplicate_ShouldReturnRunTimeExceptionForKnownLineName() {
        // given
        String name = "신분당선";
        Line line = new Line(name, new Station("강남역"), new Station("양재역"));
        LineRepository.addLine(line);
        // when

        // then
        Throwable exception = assertThrows(RuntimeException.class, () -> LineRepository.checkIfDuplicate(name));
        assertEquals("이미 등록된 노선 이름입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("신분당선 입력하면 신분당선 객체 반환")
    void findByName_ShouldReturnLineForInsertedName() {
        // given
        String name = "신분당선";
        Line line = new Line(name, new Station("강남역"), new Station("양재역"));
        LineRepository.addLine(line);
        // when
        Line result = LineRepository.findByName(name);
        Line expectedResult = new Line(name, new Station("강남역"), new Station("양재역"));
        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("2호선 입력하면 Null 반환")
    void findByName_ShouldReturnNullForInsertedName() {
        // given
        String name = "신분당선";
        Line line = new Line(name, new Station("강남역"), new Station("양재역"));
        LineRepository.addLine(line);
        // when
        Line result = LineRepository.findByName("2호선");
        // then
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("모든 노선 이름을 담은 리스트 반환")
    void getAllNames_ShouldReturnAllLineNameList() {
        // given
        Line line1 = new Line("신분당선", new Station("강남역"), new Station("양재역"));
        Line line2 = new Line("2호선", new Station("강남역"), new Station("잠실"));
        Line line3 = new Line("수인분당선", new Station("정자역"), new Station("기흥역"));
        LineRepository.addLine(line1);
        LineRepository.addLine(line2);
        LineRepository.addLine(line3);
        // when
        List<String> result = LineRepository.getAllNames();
        List<String> expectedResult = new ArrayList<>(Arrays.asList("신분당선", "2호선", "수인분당선"));
        // then
        assertThat(result).containsExactlyElementsOf(expectedResult);
    }

    @Test
    @DisplayName("강남역 객체 입력시 stations 리스트에 추가")
    void addStation_ShouldReturnListWithTheStationForGangNamStation() {
        // given
        Station station = new Station("강남역");
        // when
        StationRepository.addStation(station);
        List<Station> result = StationRepository.stations();
        List<Station> expectedResult = new ArrayList<>();
        expectedResult.add(station);
        // then
        assertThat(result).containsExactlyElementsOf(expectedResult);
    }

    @Nested
    @DisplayName("역 레포지토리의 역 제거 메소드(deleteStation)에 대한 테스트")
    class DeleteStation {

        @Test
        @DisplayName("등록되지 않은 정자역 입력시 RuntimeException 반환")
        void deleteStation_ShouldReturnRuntimeExceptionForUnknownStationName() {
            // given
            StationRepository.addStation(new Station("강남역"));
            String name = "정자역";
            // when

            // then
            Throwable exception = assertThrows(RuntimeException.class, () -> StationRepository.deleteStation(name));
            assertEquals("등록된 역의 이름을 입력해주세요.", exception.getMessage());
        }

        @Test
        @DisplayName("2호선에 등록된 강남역 입력시 RuntimeException 반환")
        void deleteStation_ShouldReturnRuntimeExceptionForStationNameInLine() {
            // given
            String name = "강남역";
            Station station1 = new Station(name);
            Station station2 = new Station("잠실");
            Line line = new Line("2호선", station1, station2);
            StationRepository.addStation(station1);
            LineRepository.addLine(line);
            // when

            // then
            Throwable exception = assertThrows(RuntimeException.class, () -> StationRepository.deleteStation(name));
            assertEquals("노선에 등록된 역은 삭제할 수 없습니다.", exception.getMessage());
        }

        @Test
        @DisplayName("노선에 등록되지 않은 강남역 입력시 빈 stations 리스트 반환")
        void deleteStation_ShouldReturnEmptyListForRegisteredStationName() {
            // given
            String name = "양재역";
            StationRepository.addStation(new Station(name));
            // when
            StationRepository.deleteStation(name);
            List<Station> result = StationRepository.stations();
            // then
            assertThat(result).isEmpty();
        }
    }

    @Test
    @DisplayName("등록된 강남역 입력시 RuntimeException 반환")
    void checkIfDuplicate_ShouldReturnRuntimeExceptionForRegisteredStationName() {
        // given
        String name = "강남역";
        StationRepository.addStation(new Station(name));
        // when

        // then
        Throwable exception = assertThrows(RuntimeException.class, () -> StationRepository.checkIfDuplicate(name));
        assertEquals("이미 등록된 역 이름입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("모든 역 이름을 담은 리스트 반환")
    void getAllNames_ShouldReturnAllStationNameList() {
        // given
        String[] names = new String[]{"강남역", "양재역", "잠실"};
        for (String name : names) {
            StationRepository.addStation(new Station(name));
        }
        // when
        List<String> result = StationRepository.getAllNames();
        List<String> expectedResult = new ArrayList<>(Arrays.asList(names));
        // then
        assertThat(result).containsExactlyElementsOf(expectedResult);
    }

    @Nested
    @DisplayName("이름으로 저장된 역 객체를 찾는 findByName 에 대한 테스트")
    class FindByName {

        @Test
        @DisplayName("저장된 강남역 입력하면 강남역 객체 반환")
        void findByName_ShouldReturnStationForInsertedName() {
            // given
            String name = "강남역";
            StationRepository.addStation(new Station(name));
            // when
            Station result = StationRepository.findByName(name);
            Station expectedResult = new Station(name);
            // then
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
        }

        @Test
        @DisplayName("저장되지 않은 정자역 입력하면 Null 반환")
        void findByName_ShouldReturnNullForNotAddedStationName() {
            // given
            StationRepository.addStation(new Station("강남역"));
            // when
            String name = "정자역";
            Station result = StationRepository.findByName(name);
            // then
            assertThat(result).isNull();
        }

    }

    @Nested
    @DisplayName("이름으로 기존의 역 객체 또는 새로운 객체 반환하는 메소드(getOrCreateStation) 에 대한 테스트")
    class GetOrCreateStation {

        @Test
        @DisplayName("저장된 강남역 입력하면 기존의 강남역 객체 반환")
        void getOrCreateStation_ShouldReturnOriginalGangNamStationForName() {
            // given
            Station station = new Station("강남역");
            StationRepository.addStation(station);
            // when
            Station result = StationRepository.getOrCreateStation("강남역");
            // then
            assertThat(result).isEqualTo(station);
        }

        @Test
        @DisplayName("저장되지 않은 강남역 입력하면 새로운 강남역 객체 반환")
        void getOrCreateStation_ShouldReturnNewGangNamStationForName() {
            // given
            String name = "강남역";
            // when
            Station result = StationRepository.getOrCreateStation(name);
            Station expectedResult = new Station(name);
            // then
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
        }
    }
}
