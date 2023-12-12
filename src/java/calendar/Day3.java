package calendar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day3 {

    /**
     * Metoda pro výpočet výsledku pro zíslání první hvězdy
     */
    public static int getResultFirstStar() {
        int result = 0;
        List<String> lines = getInputAsLines();
        for (int i = 0; i < lines.size(); i++) {
            List<String> numbersWithIndexAsString = getNumbersAndIndex(lines.get(i));
            List<Integer> symbolsIndexLineBefore = new ArrayList<>();
            List<Integer> symbolsIndexLine = new ArrayList<>();
            List<Integer> symbolsIndexLineAfter = new ArrayList<>();
            if (i > 0) {
                symbolsIndexLineBefore = getIndexsOfSpecialsymbols(lines.get(i - 1));
            }
            symbolsIndexLine = getIndexsOfSpecialsymbols(lines.get(i));
            if (i + 1 < lines.size()) {
                symbolsIndexLineAfter = getIndexsOfSpecialsymbols(lines.get(i + 1));
            }
            result += getValidNumbers(numbersWithIndexAsString, symbolsIndexLineBefore, symbolsIndexLine, symbolsIndexLineAfter);

        }

        return result;
    }

    /**
     * Metoda pro výpočet výsledku pro zíslání druhé hvězdy
     */
    public static int getResultSecondStar() {
        int result = 0;
        List<String> lines = getInputAsLines();

        for (int i = 0; i < lines.size(); i++) {
            List<Integer> gearsIndex = findAllGearsIndex(lines.get(i));
            List<String> beforeLineNumbersAndIndex = i > 0 ? getNumbersAndIndex(lines.get(i - 1)) : new ArrayList<>();
            List<String> lineNumbersAndIndex = getNumbersAndIndex(lines.get(i));
            List<String> afterLineNumbersAndIndex = i + 1 < lines.size() ? getNumbersAndIndex(lines.get(i + 1)) : new ArrayList<>();
            for (int indexOfGear : gearsIndex) {
                result += getValidGearNumber(indexOfGear, beforeLineNumbersAndIndex, lineNumbersAndIndex, afterLineNumbersAndIndex);
            }
        }

        return result;
    }

    /**
     * Metoda najde platné převodovky, pokud jsou právě 2, vynásobý je a výsledek vrátí
     * @param indexOfGear - index převodovky
     * @param beforeLineNumbersAndIndex - čísla s indexy z předchozí lajny
     * @param lineNumbersAndIndex - čísla s indexy z aktuální lajny
     * @param afterLineNumbersAndIndex - čísla s indexy z následující lajny
     * @return vrátí násobek převodovek
     */
    private static int getValidGearNumber(int indexOfGear,
                                          List<String> beforeLineNumbersAndIndex,
                                          List<String> lineNumbersAndIndex,
                                          List<String> afterLineNumbersAndIndex) {
        int result = 0;
        List<Integer> numbersNearGear = new ArrayList<>();
        for (String numberAndIndexs : beforeLineNumbersAndIndex){
            int actualNumber = Integer.parseInt(numberAndIndexs.split(":")[1]);
            String[] indexsAsField = numberAndIndexs.split(":")[0].split(",");
            List<Integer> indexs = new ArrayList<>();
            for (String index : indexsAsField){
                indexs.add(Integer.valueOf(index));
            }
            indexs.add(indexs.get(indexs.size()-1)+1);
            if (indexs.get(0) != 0){
                indexs.add(indexs.get(0)-1);
            }
            if (indexs.contains(indexOfGear)){
                numbersNearGear.add(actualNumber);
            }
        }

        for (String numberAndIndexs : lineNumbersAndIndex){
            int actualNumber = Integer.parseInt(numberAndIndexs.split(":")[1]);
            String[] indexsAsField = numberAndIndexs.split(":")[0].split(",");
            List<Integer> indexs = new ArrayList<>();
            for (String index : indexsAsField){
                indexs.add(Integer.valueOf(index));
            }
            indexs.add(indexs.get(indexs.size()-1)+1);
            if (indexs.get(0) != 0){
                indexs.add(indexs.get(0)-1);
            }
            if (indexs.contains(indexOfGear)){
                numbersNearGear.add(actualNumber);
            }
        }
        for (String numberAndIndexs : afterLineNumbersAndIndex){
            int actualNumber = Integer.parseInt(numberAndIndexs.split(":")[1]);
            String[] indexsAsField = numberAndIndexs.split(":")[0].split(",");
            List<Integer> indexs = new ArrayList<>();
            for (String index : indexsAsField){
                indexs.add(Integer.valueOf(index));
            }
            indexs.add(indexs.get(indexs.size()-1)+1);
            if (indexs.get(0) != 0){
                indexs.add(indexs.get(0)-1);
            }
            if (indexs.contains(indexOfGear)){
                numbersNearGear.add(actualNumber);
            }
        }
        result = numbersNearGear.size() == 2 ? numbersNearGear.get(0) * numbersNearGear.get(1) : 0;

        return result;
    }
    /**
     * Metoda najde všechna kolečka v lajně a vrátí jejich index
     *
     * @param line - input line
     * @return list indexů koleček
     */
    private static List<Integer> findAllGearsIndex(String line) {

        List<Integer> gearsIndex = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '*') {
                gearsIndex.add(i);
            }
        }

        return gearsIndex;
    }

    /**
     * Metoda vrací součet platných čísel
     *
     * @param numbersWithIndexAsString - čísla v lajně
     * @param symbolsIndexLineBefore   - indexy specialnich symbolů v předchozý lajně
     * @param symbolsIndexLine         - indexy specialnich symbolů v aktualní lajně
     * @param symbolsIndexLineAfter    - indexy specialnich symbolů v následující lajně
     * @return součet platnýcxh čísel
     */
    private static int getValidNumbers(List<String> numbersWithIndexAsString,
                                       List<Integer> symbolsIndexLineBefore,
                                       List<Integer> symbolsIndexLine,
                                       List<Integer> symbolsIndexLineAfter) {
        int result = 0;

        for (String numberWithIndexAsString : numbersWithIndexAsString) {
            int numberFromInput = Integer.parseInt(numberWithIndexAsString.split(":")[1]);
            List<Integer> indexs = Arrays.stream(numberWithIndexAsString.split(":")[0].split(","))
                    .map(Integer::parseInt)
                    .toList();
            int beforeIndex = indexs.get(0) - 1;
            int afterIndex = indexs.get(indexs.size() - 1) + 1;
            if (symbolsIndexLine.contains(beforeIndex) || symbolsIndexLine.contains(afterIndex)) {
                result += numberFromInput;
            } else if (symbolsIndexLineBefore.contains(beforeIndex) || symbolsIndexLineBefore.contains(afterIndex) || isContain(symbolsIndexLineBefore, indexs)) {
                result += numberFromInput;
            } else if (symbolsIndexLineAfter.contains(beforeIndex) || symbolsIndexLineAfter.contains(afterIndex) || isContain(symbolsIndexLineAfter, indexs)) {
                result += numberFromInput;
            }
        }

        return result;
    }

    /**
     * Metoda příjímá 2 Listy a porovnává, jestli jsou nějaka čísla v nich shodná, pokud ano, vrati true, jinak vrati false
     *
     * @param symbolsIndex - indexi symbolů
     * @param numberIndexs - indexy čísla
     * @return boolean
     */
    private static boolean isContain(List<Integer> symbolsIndex, List<Integer> numberIndexs) {
        for (Integer symbolIndex : symbolsIndex) {
            if (numberIndexs.contains(symbolIndex)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Metoda najde pozice speciálních znaků a vrátí jejich index
     * @param line - prověřovaná lajna
     * @return - vrací list indexů speciálních znaků
     */
    private static List<Integer> getIndexsOfSpecialsymbols(String line) {
        List<Integer> indexsOfSpecialsymbols = new ArrayList<>();
        List<Character> specialsymbols = List.of('+', '-', '*', '/', '%', '&', '@', '$', '=', '#');
        for (int i = 0; i < line.length(); i++) {
            if (specialsymbols.contains(line.charAt(i))) {
                indexsOfSpecialsymbols.add(i);
            }
        }

        return indexsOfSpecialsymbols;
    }

    /**
     * Metoda najde všechny čísla v lajně a vrátí je i s jejich indexy
     * @param line - prověřovaná lajna
     * @return - list čísel i s jejich indexy
     */
    private static List<String> getNumbersAndIndex(String line) {
        List<String> numbersWithIndexAsString = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            if (Character.isDigit(line.charAt(i))) {
                StringBuilder indexs = new StringBuilder();
                int numbersCounter = 0;
                indexs.append(i+numbersCounter).append(",");
                boolean isNextCharNumber;
                do {
                    if (line.length() > i + numbersCounter + 1 && Character.isDigit(line.charAt(i + numbersCounter + 1))) {
                        numbersCounter++;
                        indexs.append(i+numbersCounter).append(",");
                        isNextCharNumber = true;
                    } else {
                        isNextCharNumber = false;
                        indexs.deleteCharAt(indexs.length() - 1);
                    }

                } while (isNextCharNumber);
                numbersWithIndexAsString.add(indexs + ":" + line.substring(i, i + numbersCounter + 1));
                i += numbersCounter;
            }
        }

        return numbersWithIndexAsString;
    }

    /**
     * Metoda vrací input jako pole řádků
     *
     * @return pole řádků
     */
    private static List<String> getInputAsLines() {
        String inputFile = "C:\\Users\\mmaty\\OneDrive\\Plocha\\adventofcode\\AdventOfCode2023\\src\\resources\\inputDay3.txt";

        return new AdventFileReader().getFileLines(inputFile);
    }
}