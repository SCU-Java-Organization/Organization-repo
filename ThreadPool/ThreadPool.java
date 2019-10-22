package thread.ThreadPool;

/**
 * @author ShaoJiale
 * @create 2019/10/22
 * @function 简单线程池接口
 */
public interface ThreadPool<Job extends Runnable> {

    // 执行一个Job, Job实现了Runnable
    void execute(Job job);

    // 关闭线程池
    void shutdown();

    // 增加工作线程
    void addWorkers(int num);

    // 减少工作线程
    void removeWorkers(int num);

    // 得到等待的任务数量
    int getJobSize();
}
