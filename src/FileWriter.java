import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileWriter implements AutoCloseable {
    private FileChannel writeChannel;
    private CharBuffer charBuffer;

    public FileWriter(Path output) {
        try {
            this.writeChannel = FileChannel.open(output, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            this.charBuffer = CharBuffer.allocate(8192);
        } catch (IOException e) {
            System.err.println("FileReader constructor error" + e.getMessage());
        }
    }

    public CharBuffer getCharBuffer() {
        charBuffer.clear();
        return charBuffer;
    }

    public void write() {
        try {
            charBuffer.flip();
            writeChannel.write(Charset.defaultCharset().encode(charBuffer));
            charBuffer.clear();
        } catch (IOException e) {
            System.err.println("Write error" + e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            writeChannel.close();
        } catch (IOException e) {
            System.err.println("CloseWriteChannel error" + e.getMessage());
        }
    }
}
