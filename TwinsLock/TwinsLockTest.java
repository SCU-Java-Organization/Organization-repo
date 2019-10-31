package thread.Lock;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author ShaoJiale
 * @create 2019/10/28
 * @function 测试TwinsLock组件
 */
public class TwinsLockTest {
    @Test
    public void test(){
        final Lock lock = new TwinsLock();

        class Worker extends Thread{
            public void run(){
                while (true){
                    lock.lock();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println(Thread.currentThread().getName());
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }

        for (int i = 0; i < 10; i++){
            Worker worker = new Worker();
            /**
             * 使用了@Test注解来进行测试
             * 将线程都设置为Daemon防止测试无法结束
             */
            worker.setDaemon(true);
            worker.start();
        }

        for (int i = 0; i < 10; i++){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println();
        }
    }
}
