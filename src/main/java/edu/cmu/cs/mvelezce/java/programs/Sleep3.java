package edu.cmu.cs.mvelezce.java.programs;

/**
 * Created by mvelezce on 4/21/17.
 */
public class Sleep3 {

    public static final String FILENAME = Sleep3.class.getCanonicalName();
    public static final String PACKAGE = Sleep3.class.getPackage().getName();
    public static final String CLASS = Sleep3.class.getSimpleName();
    public static final String MAIN_METHOD = "main";
    public static final String METHOD_1 = "method1";
    public static final String METHOD_2 = "method2";

    public static void main(String[] args) throws InterruptedException {
        // Region program start
        System.out.println("main");
        boolean a = Boolean.valueOf(args[0]);
        boolean b = Boolean.valueOf(args[1]);
        Thread.sleep(200);
        if(a) {
            // Region A start 31
            Thread.sleep(600);
            Sleep3.method1(a);
            // Region A end 36
        }
        Thread.sleep(100);
        if(b) {
            // Region B start 48
            Thread.sleep(600);
            Sleep3.method2(b);
            // Region B start 53
        }
        // Region program end
    }

    public static void method1(boolean A) throws InterruptedException {
        System.out.println("method1");
        boolean a = A;
        Thread.sleep(200);
        if(a) {
            // Region A start 19
            Thread.sleep(600);
            // Region A end 20
        }
        Thread.sleep(100);
    }

    public static void method2(boolean B) throws InterruptedException {
        System.out.println("method2");
        boolean b = B;
        Thread.sleep(300);
        if(b) {
            // Region B start 19
            Thread.sleep(600);
            // Region B end 20
        }
        Thread.sleep(200);
    }

}
