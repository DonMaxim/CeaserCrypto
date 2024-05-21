import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class Application {
    private static int shift = 3;
    public static void main(String[] args) {
        encryptFile(Constants.sourceEncrypt, Constants.destinationEncrypt, shift);
        encryptFile(Constants.destinationEncrypt, Constants.destinationDecrypt, shift * -1);
    }

    private static void encryptFile(Path input, Path output, int shift) {
        try (FileChannel inputChannel = FileChannel.open(input, StandardOpenOption.READ);
             FileChannel outputChannel = FileChannel.open(output, StandardOpenOption.WRITE)) {
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

        } catch (IOException e) {
            e.printStackTrace();
        }
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