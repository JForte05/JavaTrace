package utilities.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Logger {
    private static Logger instance;
    
    private PrintStream output;
    private Logger(PrintStream output){
        this.output = output;
    }

    public static void start(){
        start(System.out);
    }
    public static void start(PrintStream output){
        instance = new Logger(output);
    }
    public static void start(File file) throws FileNotFoundException{
        start(new PrintStream(file));
    }

    public static void close() throws UnstartedLoggerException{
        instance.closeOutput();
    }

    public void closeOutput(){
        output.close();
    }

    public void log(String s){
        output.println(s);
    }

    public class UnstartedLoggerException extends RuntimeException{
        public UnstartedLoggerException(){
            super();
        }
    }
}
