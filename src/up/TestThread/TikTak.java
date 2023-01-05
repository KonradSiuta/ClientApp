package up.TestThread;

import java.util.Random;

public class TikTak {

    public int value = new Random().nextInt(20);
    public synchronized void tik(boolean work) {
        if(!work) {
            notify();
            return;
        }
//        System.out.println("Wyswietlam Tik");
        System.out.println("wartosc: " + value);
        notify();
        try {
            wait();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void tak(boolean work) {
        if(!work) {
            notify();
            return;
        }
//        System.out.println("Wyswietlam Tak");
        System.out.println("wartosc: " + value);
        notify();

        try {
            wait();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
