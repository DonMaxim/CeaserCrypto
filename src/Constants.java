import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Constants {
    private static final String rus = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final String eng = "abcdefghijklmnopqrstuvwxyz";
    private static final String cypher = "0123456789";
    private static final String symbols = " .,<>/;:'\"!@#$%^&*()-_[]{}`~№?=+\n\t";
    public static String fullAlphabet = rus + eng + rus.toUpperCase() + eng.toUpperCase() + cypher + symbols;
    private static char[] alphabet;

    public static char[] getAlphabet() {
        if (alphabet == null) {
            initializeAlphabet();
        }
        return Arrays.copyOf(alphabet, alphabet.length);
    }

    private static char[] initializeAlphabet() {
        Set<Character> chars = new HashSet<>();
        for (char c : fullAlphabet.toCharArray()) {
            chars.add(c);
        }
        alphabet = new char[chars.size()];
        int i = 0;
        for (Character c : chars) {
            alphabet[i++] = c;
        }
        Arrays.sort(alphabet);
        return alphabet;
    }

    public static Path sourceEncrypt = Path.of("E:\\Java\\CeaserCrypto\\resources\\input\\encryption\\sourceText.txt");
    public static Path destinationEncrypt = Path.of("E:\\Java\\CeaserCrypto\\resources\\output\\encryption\\destinationText.txt");
    public static Path sourceDecrypt = Path.of("E:\\Java\\CeaserCrypto\\resources\\output\\encryption\\destinationText.txt");
    public static Path destinationDecrypt = Path.of("E:\\Java\\CeaserCrypto\\resources\\output\\decryption\\destinationText.txt");
}
