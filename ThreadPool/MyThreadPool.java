package thread.ThreadPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ShaoJiale
 * @create 2019/10/22
 * @function 简易线程池的实现类
 */
public class MyThreadPool<Job extends Runnable> implements ThreadPool<Job>{
    // 线程池的三种状态
    private static final int MAX = 10;

    private static final int DEFAULT = 5;

    private static final int MIN = 1;

    // 任务列表
    private final LinkedList<Job> jobs = new LinkedList<>();

    // 工作线程列表
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());

    // 工作线程数量
    private int workNum = DEFAULT;

    // 线程编号生成
    private AtomicLong threadID = new AtomicLong();

    /**
     * @function 初始化工作线程
     * @Thinking 构建Worker对象，加入到工作线程列表后立即启动该线程
     * @param num 初始化工作线程数量
     */
    private void initializeWorkers(int num){
        for(int i = 0; i < num; i++){
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker, "ThreadPool-Worker-" + threadID.incrementAndGet());
            thread.start();
        }
    }

    public MyThreadPool(){
        initializeWorkers(DEFAULT);
    }

    public MyThreadPool(int num) {
        workNum = num > MAX ? MAX : num < MIN ? MIN : num;
        initializeWorkers(num);
    }

    /**
     * @function 执行某个任务线程
     * @Step 1.将任务加入任务列表中
     * @Step 2.通知所有正在竞争任务的工作线程
     * @Step 3.释放任务锁，由工作线程竞争执行
     * @param job extends Runnable
     */
    @Override
    public void execute(Job job) {
        if(job != null){
            synchronized (jobs){
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    /**
     * @function 关闭线程池
     * @Thinking 依次关闭工作线程列表中的Worker线程
     */
    @Override
    public void shutdown() {
        for (Worker worker : workers)
            worker.shutdown();
    }

    /**
     * @function 增加线程池的工作线程数量
     * @Step 1.新增数量合法化
     * @Step 2.调用Worker初始化方法
     * @see private void initializeWorkers(int num)
     * @param num
     */
    @Override
    public void addWorkers(int num) {
        synchronized (jobs){
            if(num + this.workNum > MAX)
                num = MAX - this.workNum;

            initializeWorkers(num);
            this.workNum += num;
        }
    }

    /**
     * @function 移除部分工作线程
     * @lock 移除时不允许其他线程持有任务列表
     * @step 1.移除数量合法化
     * @step 2.从工作线程列表中移除
     * @step 3.调用被移除Worker的shutdown()方法
     * @see Worker
     * @param num 移除数量
     */
    @Override
    public void removeWorkers(int num) {
        synchronized (jobs){
            if(num >= this.workNum)
                throw new IllegalArgumentException("beyond workNum");
            int count = 0;
            while(count < num){
                Worker worker = workers.get(count);
                if(workers.remove(worker)){
                    worker.shutdown();
                    count++;
                }
            }
            this.workNum -= count;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }

    /**
     * 工作线程的实现
     */
    class Worker implements Runnable{
        private volatile boolean running = true;

        public void run() {
            while (running) {
                Job job = null;

                // 尝试从任务列表中获取任务
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            // 没有任务时，进入等待并释放持有的jobs
                            jobs.wait();
                        } catch (InterruptedException e) {
                            // 感知外部对WorkerThread的中断操作，返回
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    job = jobs.removeFirst();
                }

                // 获取到任务则执行任务
                if (job != null) {
                    try {
                        job.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void shutdown(){
            this.running = false;
        }
    }
}
