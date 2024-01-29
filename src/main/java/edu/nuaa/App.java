package edu.nuaa;

/**
 * Hello world!
 */
public class App {
    private Object object = new Object();

    public void test() throws InterruptedException {
        synchronized (object) {
            object.wait();
        }
    }

    public static void main(String[] args) {
        App app = new App();
        Thread threadA = new Thread(() -> {
            try {
                app.test();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        threadA.start();
        threadA.interrupt();
    }
}
