package JDBC.SimpleConnectionPool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ShaoJiale
 * @create 2019/10/21
 * @function 测试数据库连接池
 */
public class ConnectionPoolTest {
    static ConnectionPool pool = new ConnectionPool(10);
    // 保证所有ConnectionRunner同时开始
    static CountDownLatch start = new CountDownLatch(1);
    // main线程会等待所有ConnectionRunner结束后再开始
    static CountDownLatch end;

    /**
     * @author ShaoJiale
     * @create 2019/10/21
     * @function 用于测试数据库连接池的抢占器
     */
    static class ConnectionRunner implements Runnable{
        int count;
        AtomicInteger got;
        AtomicInteger noGot;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger noGot){
            this.count = count;
            this.got = got;
            this.noGot = noGot;
        }

        public void run(){
            /**
             * 进入等待状态
             * 等待其他线程调用对应的notify() / notifyAll()方法
             */
            try {
                start.await();
            } catch (Exception e){
                e.printStackTrace();
            }

            /**
             * 每个抢占器可以进行count次抢占
             * 每次抢占成功后调用commit方法
             * 代理类会让抢占器暂停100ms, 从而实现抢占器对连接的持有
             */
            while (count > 0){
                try {
                    Connection connection = pool.fetchConnection(1000);
                    if(connection != null){
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    } else {
                        noGot.incrementAndGet();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    count--;
                }
            }
            end.countDown();
        }
    }


    public static void main(String[] args) throws Exception{
        // 线程数量
        int threadCount = 20;
        end = new CountDownLatch(threadCount);

        // 重复获取次数
        int count = 20;

        // 初始化获取成功和获取失败的次数
        AtomicInteger got = new AtomicInteger(0);
        AtomicInteger noGot = new AtomicInteger(0);

        for(int i = 0; i < threadCount; i++){
            Thread thread = new Thread(new ConnectionRunner(count, got, noGot), "ConnectionRunnerThread");
            thread.start();
        }
        // start值为1，调用countDown()后，所有线程同时开始
        start.countDown();
        // 挂起main线程，直到所有线程结束
        end.await();

        System.out.println("Total invoke = " + threadCount * count);
        System.out.println("Got connection = " + got);
        System.out.println("No got connection = " + noGot);
    }
}
