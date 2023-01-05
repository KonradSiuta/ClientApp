package up.TestThread;

import com.sun.tools.javac.Main;
import up.App;

import java.util.Random;

public class TestRunnable implements Runnable {

    private int nrTask;

    public TestRunnable(int task){
        nrTask = task;
    }

    @Override
    public void run() {
        showNumber();
    }

    private void showNumber(){
        String nameThread = Thread.currentThread().getName();
        System.out.println(nameThread + " " + nrTask);
//        int idTh = Integer.parseInt( nameThread.split("-")[3] );
        Random rand = new Random();
        int number, count = 0;

        while (true) {
            number = rand.nextInt(230);
            synchronized (this) {
                if (!App.numberList.contains(number)) {
                    App.numberList.add(number);
                    System.out.println("Wątek " + nameThread + " wartość " + number);
                    break;
                }
                count++;
            }

//        App.tableInt[rand.nextInt(2000)] = idTh;
            try {
                int w = rand.nextInt(1000);
                Thread.sleep(w);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count > 6){
                System.out.println("Wątek " + nameThread + " przerywa losowanie");
                break;
            }
        }
    }
}
