import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class Cipher {
    static void encryptFile(Path input, Path output, int shift) {
        if (shift % Constants.getAlphabet().length == 0) {
            System.out.println("There will be no changes with this shift");
            return;
        }

        try (FileChannel inputChannel = FileChannel.open(input, StandardOpenOption.READ);
             FileChannel outputChannel = FileChannel.open(output, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(8192);
            CharBuffer inputCharBuffer = CharBuffer.allocate(8192);
            CharBuffer outputCharBuffer = CharBuffer.allocate(8192);
            CharsetDecoder inputDecoder = StandardCharsets.UTF_8.newDecoder();

            while (inputChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                inputDecoder.decode(byteBuffer, inputCharBuffer, false);
                inputCharBuffer.flip();

                encrypt(inputCharBuffer, outputCharBuffer, shift);
                outputCharBuffer.flip();
                inputCharBuffer.clear();

                while (outputCharBuffer.hasRemaining()) {
                    outputChannel.write(Charset.defaultCharset().encode(outputCharBuffer));
                }
                byteBuffer.compact();
                outputCharBuffer.clear();
            }
            byteBuffer.flip();
            inputDecoder.decode(byteBuffer, inputCharBuffer, true);
            inputCharBuffer.flip();

            encrypt(inputCharBuffer, outputCharBuffer, shift);
            outputCharBuffer.flip();
            inputCharBuffer.clear();

            while (outputCharBuffer.hasRemaining()) {
                outputChannel.write(Charset.defaultCharset().encode(outputCharBuffer));
            }
            outputCharBuffer.clear();

        } catch (NoSuchFileException e) {
            System.err.println("File not found" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
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

    private static void encrypt(CharBuffer input, CharBuffer output, int shift) {
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
