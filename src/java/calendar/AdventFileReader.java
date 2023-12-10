package calendar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdventFileReader {


    /**
     * Metoda pro získání řádku souboru
     *
     * @Param @String fileName - název souboru
     *
     * @Return @List<String> lines - list řádků souboru
     */
    public List<String> getFileLines(String fileName) {
        String path = this.getClass().getName();
        List<String> lines = new ArrayList<>();
        lines.add(path);
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            lines = bufferedReader.lines().toList();

            bufferedReader.close();
        }
        catch(IOException ex) {
            System.out.println("Soubor '" + fileName + "' nebyl nalezen");
        }

        return lines;
    }
}
