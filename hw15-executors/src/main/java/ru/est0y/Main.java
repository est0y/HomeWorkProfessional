package ru.est0y;

import java.util.concurrent.Executors;

public class Main {
    private static final int START_VALUE = 1;
    private static final int END_VALUE = 10;
    private String last = "pool-1-thread-2";

    public static void main(String[] args) {
        var main = new Main();
        var ex = Executors.newFixedThreadPool(2);
        ex.submit(main::run);
        ex.submit(main::run);
    }

    private synchronized void run() {
        int i = START_VALUE - 1;
        boolean isReverse = false;
        String threadName = Thread.currentThread().getName();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
                while (threadName.equals(last)) {
                    this.wait();
                }
                if (i == START_VALUE) {
                    isReverse = false;
                } else if (i == END_VALUE) {
                    isReverse = true;
                }
                System.out.println(threadName + " : " + (isReverse ? --i : ++i));
                last = threadName;
                notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
