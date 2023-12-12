package calendar;

import java.util.List;

public class Day2 {
    private static final String RED = "red";
    private static final String GREEN = "green";
    private static final String BLUE = "blue";

    /**
     * Metoda pro výpočet výsledku pro zíslání první hvězdy
     */
    public static int getResultFirstStar() {
        int result = 0;
        List<String> lines = getInputAsLines();

        for (String line : lines) {
            if (isGamePossilbe(line)) {
                result += Integer.parseInt(line.split(":")[0].split(" ")[1]);
            }
        }

        return result;
    }

    /**
     * Metoda pro výpočet výsledku pro zíslání druhé hvězdy
     */
    public static int getResultSecondStar() {
        int result = 0;
        List<String> lines = getInputAsLines();

        for (String line : lines) {
            result += getMaxCubeMultiple(line);
        }

        return result;
    }

    /**
     * Metoda zjistí největší počet vytažených kostek jednotlivých barev a vrátí jejich násobek
     *
     * @param line - řádek zadání
     * @return vrátí násobek nejvýšího počtu kostek každé barvy
     */
    private static int getMaxCubeMultiple(String line) {
        int redCubes = 1;
        int greenCubes = 1;
        int blueCubes = 1;
        String[] turns = line.split(": ")[1].split("; ");

        for (String turn : turns) {
            String[] draws = turn.split(", ");
            for (String draw : draws) {
                int number = Integer.parseInt(draw.split(" ")[0]);
                String color = draw.split(" ")[1];
                switch (color) {
                    case RED:
                        if (number > redCubes) {
                            redCubes = number;
                        }
                        break;
                    case GREEN:
                        if (number > greenCubes) {
                            greenCubes = number;
                        }
                        break;
                    case BLUE:
                        if (number > blueCubes) {
                            blueCubes = number;
                        }
                        break;

                    default:
                        throw new IllegalArgumentException("Zadal jsi špatnou barvu: " + color);
                }
            }
        }

        return redCubes * greenCubes * blueCubes;
    }

    /**
     * Metoda kontroluje, jestli je hra možná
     *
     * @param line - jeden řádek zadání
     * @return vrací true pokud je hra možná, false pokud gra možná není
     */
    private static boolean isGamePossilbe(String line) {
        int redCubes = 12;
        int greenCubes = 13;
        int blueCubes = 14;

        String[] turns = line.split(": ")[1].split("; ");
        for (String turn : turns) {
            //Rozděl tah na vytažení jedné kostky
            String[] diceDraws = turn.split(", ");
            for (String diceDraw : diceDraws) {
                int number = Integer.parseInt(diceDraw.split(" ")[0]);
                String color = diceDraw.split(" ")[1];
                switch (color) {
                    case RED:
                        if (number > redCubes) {
                            return false;
                        }
                        break;
                    case GREEN:
                        if (number > greenCubes) {
                            return false;
                        }
                        break;
                    case BLUE:
                        if (number > blueCubes) {
                            return false;
                        }
                        break;

                    default:
                        throw new IllegalArgumentException("Zadal jsi špatnou barvu: " + color);
                }
            }
        }

        return true;
    }

    /**
     * Metoda vrací input jako pole řádků
     *
     * @return pole řádků
     */
    private static List<String> getInputAsLines() {
        String inputFile = "C:\\Users\\mmaty\\OneDrive\\Plocha\\adventofcode\\AdventOfCode2023\\src\\resources\\inputDay2.txt";

        return new AdventFileReader().getFileLines(inputFile);
    }
}