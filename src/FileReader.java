import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileReader implements AutoCloseable {
    private FileChannel readChannel;
    private ByteBuffer byteBuffer;
    private CharBuffer charBuffer;
    private CharsetDecoder decoder;

    public FileReader(Path input) {
        try {
            this.readChannel = FileChannel.open(input, StandardOpenOption.READ);
            this.byteBuffer = ByteBuffer.allocateDirect(8192);
            this.charBuffer = CharBuffer.allocate(8192);
            this.decoder = StandardCharsets.UTF_8.newDecoder();
        } catch (NoSuchFileException e) {
            System.err.println("File not found" + e.getMessage());
        } catch (IOException e) {
            System.err.println("FileReader constructor error" + e.getMessage());
        }
    }

    public int read() {
        int result = -1;
        try {
            result = readChannel.read(byteBuffer);
        } catch (IOException e) {
            System.err.println("Read error" + e.getMessage());
        }
        return result;
    }

    public CharBuffer getCharBuffer(Boolean endOfInput) {
        charBuffer.clear();
        byteBuffer.flip();
        decoder.decode(byteBuffer, charBuffer, endOfInput);
        charBuffer.flip();
        byteBuffer.compact();
        return charBuffer;
    }

    @Override
    public void close() {
        try {
            readChannel.close();
        } catch (IOException e) {
            System.err.println("CloseReadChannel error" + e.getMessage());
        }
    }
}
