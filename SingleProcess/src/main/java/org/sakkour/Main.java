package org.sakkour;

public class Main {

    public static void main(String[] args) {

        // Start our demo.
        Demo.show();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Here, we can execute cleanup code if needed.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("finished.");
        }));

    }
}