package subway.view;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class OutputView {

    private static final String SUCCESS_PREFIX = "[INFO] ";
    private static final String ERROR_PREFIX = "[ERROR] ";

    private OutputView() {
    }

    public static void customPrint(String content) {
        System.out.println(System.lineSeparator() + content);
    }

    public static void printMenu(String topic, boolean fullInquiryNeeded) {

        List<String> subMenu = new LinkedList<>(Arrays.asList(System.lineSeparator() + "## %s 관리 화면", "1. %s 등록", "2. %s 삭제", "3. %s 조회", "B. 돌아가기" + System.lineSeparator(), "## 원하는 기능을 선택하세요."));
        if (!fullInquiryNeeded) {
            subMenu.remove(3);
        }

        for (String menu : subMenu) {
            System.out.printf(menu + System.lineSeparator(), topic);
        }
    }

    public static void printError(String message) {
        customPrint(ERROR_PREFIX + message);
    }

    public static void printSuccess(String target, String operation) {
        System.out.printf(System.lineSeparator() + SUCCESS_PREFIX + "%s이 %s되었습니다" + System.lineSeparator(), target, operation);
    }

    public static void printInquiry(String target, List<String> names) {
        System.out.printf(System.lineSeparator() + "##%s 목록" + System.lineSeparator(), target);
        for (String name : names) {
            System.out.println(SUCCESS_PREFIX + name);
        }
    }
}
