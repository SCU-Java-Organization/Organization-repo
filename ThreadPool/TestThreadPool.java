package thread.ThreadPool;

/**
 * @author ShaoJiale
 * @create 2019/10/22
 * @function 测试MyThreadPool
 */
public class TestThreadPool {
    public static void main(String[] args){
        MyThreadPool threadPool = new MyThreadPool(3);

        for(int i = 1; i <= 5; i++){
            Rocket rocket = new Rocket(i);
            threadPool.execute(rocket);
        }
        /**
         * FixMe 如果不调用shutdown()，则程序无法结束；
         * FixMe 调用shutdown()，可能导致任务未运行完就退出程序
         * FixMe 思考如何使得所有线程结束后才调用shutdown()?
         * FixMe 即"threadPool.join()"问题
         */
        //threadPool.shutdown();
    }
}
