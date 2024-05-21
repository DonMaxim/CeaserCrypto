import java.time.LocalDateTime;

public class Application {
    private static int shift = 1;
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        Cipher.encryptFile(Constants.sourceEncrypt, Constants.destinationEncrypt, shift);
        long finishEncrypt = System.nanoTime();
        System.out.println("Encrypt time: " + (finishEncrypt - startTime));
        Cipher.decryptFile(Constants.destinationEncrypt, Constants.destinationDecrypt, shift);
        long finishDecrypt = System.nanoTime();
        System.out.println("Decrypt time: " + (finishDecrypt - finishEncrypt));
    }

}