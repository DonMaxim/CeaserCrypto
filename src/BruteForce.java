import java.nio.CharBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BruteForce {
    private List<Character> getStandardAlphabet(Path input) {
        // вычисление эталонного алфавита, отсортированного от наименее частого к наиболее
        Map<Character, Integer> dictionary = new HashMap<>();
        for (char c : Constants.getAlphabet()) {
            dictionary.put(c, 0);
        }
        try(FileReader inputFile = new FileReader(input)) {
            while (inputFile.read() != -1) {
                CharBuffer charBuffer = inputFile.getCharBuffer(false);
                while (charBuffer.hasRemaining()) {
                    char c = charBuffer.get();
                    if (dictionary.containsKey(c)) {
                        dictionary.put(c, dictionary.get(c) + 1);
                    }
                }
            }
            CharBuffer charBuffer = inputFile.getCharBuffer(true);
            while (charBuffer.hasRemaining()) {
                char c = charBuffer.get();
                if (dictionary.containsKey(c)) {
                    dictionary.put(c, dictionary.get(c) + 1);
                }
            }
        }

        List<Map.Entry<Character, Integer>> entries = new ArrayList<>(dictionary.entrySet());
        entries.sort(Map.Entry.comparingByValue());
        List<Character> result = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : entries) {
            result.add(entry.getKey());
        }
        return result;
    }

    private int calculateShift() {
        // вычисление перебором сдвига
        return 0;
    }
}
