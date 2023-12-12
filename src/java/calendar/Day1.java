package calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day1 {

    /**
     * Metoda pro výpočet výsledku pro zíslání první hvězdy
     */
    public static int getResultFirstStar() {
        int result = 0;
        String inputFile = "C:\\Users\\mmaty\\OneDrive\\Plocha\\adventofcode\\AdventOfCode2023\\src\\resources\\inputDay1.txt";
        List<String> lines = new AdventFileReader().getFileLines(inputFile);

        for (String line : lines) {
            result += getFirstAndLastNumber(line);
        }

        return result;
    }

    /**
     * Metoda pro výpočet výsledku pro zíslání druhé hvězdy
     */
    public static int getResultSecondStar() {
        int result = 0;
        String inputFile = "C:\\Users\\mmaty\\OneDrive\\Plocha\\adventofcode\\AdventOfCode2023\\src\\resources\\inputDay1.txt";
        List<String> lines = new AdventFileReader().getFileLines(inputFile);
        List<String> updateLines = getUpdateLinesForSecondStar(lines);

        for (String line : updateLines) {
            result += getFirstAndLastNumber(line);
        }

        return result;
    }

    /**
     * Metoda pro získání prvního a poslednního čísla ze stringu
     *
     * @param line - řádek zadaného textu
     * @return result - první a poslední číslo textu
     */
    private static int getFirstAndLastNumber(String line) {
        int result;
        String firstNumber = getFirstNumber(line);
        String lastNumber = getLastNumber(line);
        result = Integer.parseInt(firstNumber + lastNumber);

        return result;
    }

    /**
     * Metoda pro získání prvního čísla ze stringu
     *
     * @param line - řádek zadaného textu
     * @return vrací první nalezené číslo jako String
     */
    private static String getFirstNumber(String line) {
        for (char ch : line.toCharArray()) {
            if (Character.isDigit(ch)) {
                return String.valueOf(ch);
            }
        }
        throw new IllegalArgumentException("Řetězec neobsahuje číslovku");
    }

    /**
     * Metoda pro získání posledního čísla ze stringu
     *
     * @param line - řádek zadaného textu
     * @return vrací poslední nalezené číslo jako String
     */
    private static String getLastNumber(String line) {
        char numberAsChar = 'x';
        for (char ch : line.toCharArray()) {
            if (Character.isDigit(ch)) {
                numberAsChar = ch;
            }
        }
        if (numberAsChar != 'x') {
            return String.valueOf(numberAsChar);
        } else {
            throw new IllegalArgumentException("Řetězec neobsahuje číslovku");
        }
    }

    /**
     * Metoda pro převod čísel ze Stringu na číslo
     *
     * @param lines - původní vstup
     * @return vrátí původní vstup upravený tak, aby se před čísly zapsanými jako
     * textový řetězec vyskytovalo toto číslo jako číslovka v decimálním tvaru
     */
    private static List<String> getUpdateLinesForSecondStar(List<String> lines) {
        List<String> updateLines = new ArrayList<>();

        for (String line : lines) {
            updateLines.add(getUpdateLine(line));
        }

        return updateLines;
    }

    /**
     * Metoda vloží před první a poslední číslo zapsané textem jeho ekvivalent jako int
     *
     * @param line - řádek zadaného textu
     * @return vrátí původní vstup upravený tak, aby se před čísly zapsanými jako
     * textový řetězec vyskytovalo toto číslo jako číslovka v decimálním tvaru
     */
    private static String getUpdateLine(String line) {
        int numberCounter = 0;
        Map<Integer, Integer> foundNumbers = new HashMap<>();
        String[] numbersAsString = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        // projed string a zjisti jestli existuji vyskyty čísel jako textu
        for (int i = 0; i < numbersAsString.length; i++) {
            if (line.contains(numbersAsString[i])) {

                int first = line.indexOf(numbersAsString[i]);
                int last = line.lastIndexOf(numbersAsString[i]);
                if (first == last) {
                    foundNumbers.put(line.indexOf(numbersAsString[i]), i + 1);
                } else {
                    foundNumbers.put(first, i + 1);
                    foundNumbers.put(last, i + 1);
                }
            }
        }
        StringBuilder updateLine = new StringBuilder(line);

        for (Map.Entry<Integer, Integer> foundNumber : foundNumbers.entrySet()) {
            updateLine.insert(foundNumber.getKey() + numberCounter++, foundNumber.getValue());
        }

        return updateLine.toString();
    }
}
