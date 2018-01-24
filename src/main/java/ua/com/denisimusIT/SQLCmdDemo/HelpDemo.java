package ua.com.denisimusIT.SQLCmdDemo;

import ua.com.denisimusIT.SQLCmd.controller.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;


public class HelpDemo {
    private static final String NEWLINE = System.lineSeparator();

    private static ConfigurableInputStream in;
    private static ByteArrayOutputStream out;


    public static void main(String [] args) throws UnsupportedEncodingException {
        System.out.println("HelpDemo" + NEWLINE);
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();
        System.setIn(in);


        in.add("help");
        in.add("exit");
        Main.main(new String[0]);
        String actual = out.toString();
        System.out.println(new PrintStream(out).toString());


    }



}
