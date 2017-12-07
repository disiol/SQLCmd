package ua.com.denisimusIT.SQLCmd.Integration;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class LogOutputStream extends OutputStream {
    String log;

    @Override
    public void write(int b) throws IOException {
        log += String.valueOf((char) b);
    }

    public String getData() {
        return log;
    }
}
