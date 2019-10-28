package thread.Lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author ShaoJiale
 * @create 2019/10/28
 * @function 同步工具——在同一时刻至多允许两个线程同时访问，超出部分被阻塞
 */
public class TwinsLock implements Lock {

    private static final class Sync extends AbstractQueuedSynchronizer{

        Sync(int count){
            if(count <= 0)
                throw new IllegalArgumentException("count must larger than zero");
            setState(count);
        }

        /**
         * @function 与底层同步队列进行连接
         * @function 当newCount >= 0时，同步队列的acquireShared方法才能获取锁
         * @param reduceCount
         * @return newCount -- state
         */
        @Override
        public int tryAcquireShared(int reduceCount){
            for (;;){
                int current = getState();
                int newCount = current - reduceCount;
                if(newCount < 0 || compareAndSetState(current, newCount))
                    return newCount;
            }
        }

        /**
         * @function 与底层同步队列进行连接
         * @function 自旋归还锁
         * @param returnCount
         * @return release successfully or failed
         */
        @Override
        protected boolean tryReleaseShared(int returnCount) {
            for (;;){
                int current = getState();
                int newCount = current + returnCount;
                if(compareAndSetState(current, newCount))
                    return true;
            }
        }
    }

    private final Sync sync = new Sync(2);


    public void lock() {
        sync.acquireShared(1);
    }

    public void unlock() {
        sync.releaseShared(1);
    }

    /**
     * 下面的Lock接口方法均未实现
     * 只实现了lock与unlock方法
     */
    public void lockInterruptibly() throws InterruptedException {

    }

    public boolean tryLock() {
        return false;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public Condition newCondition() {
        return null;
    }

}
