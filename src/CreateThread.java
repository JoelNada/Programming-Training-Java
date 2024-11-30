import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateThread {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        MyFirstThread myFirstThread = new MyFirstThread();
        myFirstThread.start();
        MySecondThread mySecondThread = new MySecondThread();
        Thread thread = new Thread(mySecondThread);
        thread.start();
        Runnable runnable = ()->{
            System.out.println("My third thread");
        };
        Thread thread3 = new Thread(runnable);
        thread3.start();
        Runnable runnable2 = ()->{
            System.out.println("My fourth thread which is running in a different thread by ExecutorService");
        };
        Runnable runnable3 = ()->{
            System.out.println("My fifth thread which is running in a different thread by ExecutorService");
        };
        executorService.execute(runnable2);
        executorService.execute(runnable3);
        executorService.shutdown();
    }
}

class MyFirstThread extends Thread{
    public void run(){
        System.out.println("My first thread");
    }
}

class MySecondThread implements Runnable{
    public void run(){
        System.out.println("My second thread");
    }
}