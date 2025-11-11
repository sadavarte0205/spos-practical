import java.util.concurrent.Semaphore;

public class ReaderWriterProblem1 {

    static Semaphore readLock = new Semaphore(1);
    static Semaphore writeLock = new Semaphore(1);
    static int readCount = 0;

    static class Read implements Runnable {
        @Override
        public void run() {
            try {
                readLock.acquire();
                readCount++;
                if (readCount == 1) {
                    writeLock.acquire();
                }
                readLock.release();

                System.out.println("Thread " + Thread.currentThread().getName() + " is READING.");
                Thread.sleep(1500);
                System.out.println("Thread " + Thread.currentThread().getName() + " finished READING.");

                readLock.acquire();
                readCount--;
                if (readCount == 0) {
                    writeLock.release();
                }
                readLock.release();

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static class Write implements Runnable {
        @Override
        public void run() {
            try {
                writeLock.acquire();

                System.out.println("Thread " + Thread.currentThread().getName() + " is WRITING.");
                Thread.sleep(2000);
                System.out.println("Thread " + Thread.currentThread().getName() + " finished WRITING.");

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } finally {
                writeLock.release();
            }
        }
    }

    public static void main(String[] args) throws Exception {

        Read readTask = new Read();
        Write writeTask = new Write();

        Thread t1 = new Thread(writeTask);
        t1.setName("Writer-1");

        Thread t2 = new Thread(readTask);
        t2.setName("Reader-1");

        Thread t3 = new Thread(readTask);
        t3.setName("Reader-2");

        Thread t4 = new Thread(writeTask);
        t4.setName("Writer-2");

        Thread t5 = new Thread(readTask);
        t5.setName("Reader-3");
        
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}