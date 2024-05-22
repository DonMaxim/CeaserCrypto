import java.nio.CharBuffer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {
    private static int shift = 1;

    public static void main(String[] args) {
//        long startTime = System.nanoTime();
//        Cipher.encryptFile(Constants.sourceEncrypt, Constants.destinationEncrypt, shift);
//        long finishEncrypt = System.nanoTime();
//        System.out.println("Encrypt time: " + (finishEncrypt - startTime));
//        Cipher.decryptFile(Constants.destinationEncrypt, Constants.destinationDecrypt, shift);
//        long finishDecrypt = System.nanoTime();
//        System.out.println("Decrypt time: " + (finishDecrypt - finishEncrypt));
        Map<Character, Integer> dictionary = new HashMap<>();
        for (char c : Constants.getAlphabet()) {
            dictionary.put(c, 0);
        }
        try(FileReader inputFile = new FileReader(Constants.sourceEncrypt)) {
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
        System.out.println(entries);
        System.out.println(result);
    }

}