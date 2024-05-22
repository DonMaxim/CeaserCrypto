import java.nio.CharBuffer;
import java.nio.file.Path;
import java.util.Arrays;

public class Cipher {
    static void encryptFile(Path input, Path output, int shift) {
        if (shift % Constants.getAlphabet().length == 0) {
            System.out.println("There will be no changes with this shift");
            return;
        }

        try (FileReader inputFile = new FileReader(input); FileWriter outputFile = new FileWriter(output)) {
            while (inputFile.read() != -1) {
                encrypt(inputFile.getCharBuffer(false), outputFile.getCharBuffer(), shift);
                outputFile.write();
            }

            encrypt(inputFile.getCharBuffer(true), outputFile.getCharBuffer(), shift);
            outputFile.write();
        }
    }

    static void decryptFile(Path input, Path output, int shift) {
        encryptFile(input, output, shift * -1);
    }

    private static Character encrypt(char c, int shift) {
        char[] alphabet = Constants.getAlphabet();
        int index = Arrays.binarySearch(alphabet, c);
        if (index < 0) {
            return null;
        } else {
            int shiftedIndex = (index + shift) % alphabet.length;
            if (shiftedIndex < 0) {
                shiftedIndex += alphabet.length;
            }
            return alphabet[shiftedIndex];
        }
    }

    static void encrypt(CharBuffer input, CharBuffer output, int shift) {
        while (input.hasRemaining()) {
            char c = input.get();
            Character encryptedC = encrypt(c, shift);
            if (encryptedC == null) {
                continue;
            }
            output.put(encryptedC);
        }
    }
}
