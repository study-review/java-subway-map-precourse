package subway.view;

import java.util.Scanner;

public class InputView {

    private static final Scanner sc = new Scanner(System.in);

    private InputView() {
    }

    public static String getMainCommand() {
        String sb = "## 메인 화면" + System.lineSeparator()
                + "1. 역 관리" + System.lineSeparator()
                + "2. 노선 관리" + System.lineSeparator()
                + "3. 구간 관리" + System.lineSeparator()
                + "4. 지하철 노선도 출력" + System.lineSeparator()
                + "Q. 종료" + System.lineSeparator() + System.lineSeparator()
                + "## 원하는 기능을 선택하세요.";

        System.out.println(sb);

        return sc.nextLine().trim();
    }

    public static String getStationCommand() {
        OutputView.printMenu("역", true);

        return sc.nextLine().trim();
    }

    public static String getLineCommand() {
        OutputView.printMenu("노선", true);

        return sc.nextLine().trim();
    }

    public static String getSectionMenu() {
        OutputView.printMenu("구간", false);

        return sc.nextLine().trim();
    }

    public static String getTargetName() {
        do {
            String name = sc.nextLine().replaceAll(" ", "");
            try {
                validateNameLength(name, 2);
                return name;
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        } while (true);
    }

    private static void validateNameLength(String name, int length) {
        if (name.replaceAll(" ", "").length() < length) {
            throw new IllegalArgumentException(String.format("이름은 %d글자 이상이어야 합니다.", length));
        }
    }
}
