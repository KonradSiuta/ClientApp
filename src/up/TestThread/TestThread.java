package up.TestThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TestThread {

    public void startRunnable(int maxThread){
        ExecutorService service = Executors.newCachedThreadPool();
        ThreadPoolExecutor thredPool = (ThreadPoolExecutor) service;
        int numberTask = 0;
        while (numberTask < 2000){
            if (thredPool.getActiveCount() < maxThread){
                service.submit(new TestRunnable(numberTask));
                numberTask++;
            }
        }
        service.shutdown();
    }

    public void startRunnambleFixed(int maxThread){
        ExecutorService service = Executors.newFixedThreadPool(maxThread);
        for (int i = 0; i< 200; i++){
            service.submit(new TestRunnable( i ));
        }
        service.shutdown();
    }

    public void startCallable(int maxThread){
        ExecutorService service = Executors.newFixedThreadPool(maxThread);
        List<Future<String>> futureList = new ArrayList<>();
        //do zrobienia - futureList na futureDeque
        BlockingDeque<Future<String>> futureDeque = new LinkedBlockingDeque<>();
        System.out.println("Dodawanie elementów do wykonania");

        for (int i = 0; i < 200; i++){
            TestCallable tc = new TestCallable(i);
            futureList.add(service.submit(tc));
        }
        System.out.println("Odbieranie wyników");
        for (int i = 0; i < 200; i++) {
            Future<String> f = futureList.get(i);
            try {
                String message = f.get(2, TimeUnit.SECONDS);
                System.out.println(message);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }

        }
        service.shutdown();
    }
}
