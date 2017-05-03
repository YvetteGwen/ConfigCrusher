package edu.cmu.cs.mvelezce.java.programs;

/**
 * Created by mvelezce on 4/21/17.
 */
public class Sleep7 {

    public static final String FILENAME = Sleep7.class.getCanonicalName();
    public static final String PACKAGE = Sleep7.class.getPackage().getName();
    public static final String CLASS = Sleep7.class.getSimpleName();
    public static final String MAIN_METHOD = "main";

    public static void main(String[] args) throws InterruptedException {
        // Region program start
        System.out.println("main");
        boolean a = Boolean.valueOf(args[0]);
        Thread.sleep(200);

        int repeat;
        if(a) {
            repeat = 5;
        }
        else {
            repeat = 10;
        }

        for(int i=0; i < repeat; i++) {
            Thread.sleep(100);
        }
        // Region program end
    }

}
