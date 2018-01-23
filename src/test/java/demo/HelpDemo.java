package demo;

import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.Integration.ConfigurableInputStream;
import ua.com.denisimusIT.SQLCmd.controller.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;


public class HelpDemo {
    private static final String NEWLINE = System.lineSeparator();

    private static ConfigurableInputStream in;
    private static ByteArrayOutputStream out;

    @Test
    public void main() throws UnsupportedEncodingException {
        System.err.println("HelpDemo" + NEWLINE);
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));

        in.add("help");
        in.add("exit");
        Main.main(new String[0]);
        String actual = out.toString();
        System.err.println(actual);
        System.err.println("end HelpDemo");


    }



}
