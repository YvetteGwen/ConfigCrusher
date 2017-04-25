package edu.cmu.cs.mvelezce.tool.instrumentation.java.programs;

/**
 * Created by mvelezce on 4/21/17.
 */
public class Sleep1 {

    public static final String FILENAME = Sleep1.class.getCanonicalName();
    public static final String PACKAGE = Sleep1.class.getPackage().getName();
    public static final String CLASS = Sleep1.class.getSimpleName();
    public static final String MAIN_METHOD = "main";
    public static final String METHOD_1 = "method1";

    public static void main(String[] args) throws InterruptedException, CloneNotSupportedException {
        // Region program start
        System.out.println("main");
        boolean a = Boolean.valueOf(args[0]);
        Thread.sleep(200);
        if(a) {
//             Region A start
            Thread.sleep(300);
            // Region A end
        }
        Thread.sleep(100);
        // Region program end
    }

}
