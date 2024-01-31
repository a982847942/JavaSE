/**
 * 线程池的优点：
 *  1.控制线程数量，防止OOM
 *  2.避免线程重复创建销毁的开销
 *  3.更好的管理线程的生命周期
 */

/**
 ThreadPoolExecutor类总共为我们提供了四个构造方法，前面三个构造方法都是调用最后一个全参的构造函数来完成工作的，
 最后一个全参的构造方法需要我们传递7个参数，这七个参数的具体含义如下：

 构造函数参数列表：
 corePoolSize： 核心线程池的大小，如果核心线程池有空闲位置，这时新的任务就会被核心线程池新建一个线程执行，执行完毕后不会销毁线程，线程会进入缓存队列等待再次被运行。
 maximunPoolSize： 线程池能创建最大的线程数量。如果核心线程池和缓存队列都已经满了，新的任务进来就会创建新的线程来执行。但是数量不能超过maximunPoolSize，否侧会采取拒绝接受任务策略，我们下面会具体分析。
 keepAliveTime： 非核心线程能够空闲的最长时间，超过时间，线程终止。这个参数默认只有在线程数量超过核心线程池大小时才会起作用。只要线程数量不超过核心线程大小，就不会起作用（当然如果设置了allowCoreThreadTimeOut(true)线程池中的核心线程也受该参数的影响）。
 unit： 时间单位，和keepAliveTime配合使用，可选择项如下：
 TimeUnit.DAYS：天
 TimeUnit.HOURS：小时
 TimeUnit.MINUTES：分钟
 TimeUnit.SECONDS：秒
 TimeUnit.MILLISECONDS：毫秒
 TimeUnit.MICROSECONDS：微妙
 TimeUnit.NANOSECONDS：纳秒

 workQueue： 任务队列，用来存放等待被执行的任务，一般为阻塞队列（BlockingQueue）三种常用为：（可自定义阻塞队列）。
 ArrayBlockingQueue：基于数组的先进先出队列，此队列创建时必须指定大小；
 LinkedBlockingQueue：基于链表的先进先出队列，如果创建时没有指定此队列大小，则默认为Integer.MAX_VALUE；
 SynchronousQueue：这个队列比较特殊，它不会保存提交的任务，而是将直接新建一个线程来执行新来的任务。

 threadFactory： 线程工厂，用来创建线程，一般有三种选择策略（可自定义）。

 handler： 任务拒绝策略，线程数量大于最大线程数就会采用拒绝处理策略。ThreadPoolExecutor中为我们提供了四种默认策略可选择（可自定义）：
 ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
 ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
 ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
 ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务

 要了解线程池，我们首先要了解的线程池里面的状态控制的参数 ctl，这个线程池的状态控制参数是一个原子操作的 AtomicInteger，这个ctl包含两个参数 ：
 runState：当前线程池的状态
 workerCount：激活（工作）的线程数
 它的低29位用于存放当前的线程数, 因此一个线程池在理论上最大的线程数是 536870911; 高 3 位是用于表示当前线程池的状态, 其中高三位的值和状态对应如下：
 111: RUNNING：线程池初始化（创建出来之后）处于此状态，能够接收新任务，以及对已添加的任务进行处理。
 000: SHUTDOWN：当调用shutdown()方法时改为此状态，在此状态时，不接收新任务，但能处理已添加的任务。
 001: STOP：调用shutdownNow()方法时处于此状态，在此状态时，不接收新任务，不处理已添加的任务，并且会尝试中断正在处理的任务。
 010: TIDYING：当线程池在SHUTDOWN状态下，阻塞队列为空并且线程池中执行的任务也为空时，就会由 SHUTDOWN -> TIDYING。|| 当所有的任务已终止，
 ctl记录的”任务数量”为0，线程池会变为TIDYING状态。当线程池变为TIDYING状态时，会执行钩子函数terminated()。
 terminated()在ThreadPoolExecutor类中是空的，若用户想在线程池变为TIDYING时，进行相应的处理；可以通过重载terminated()函数来实现。
 011: TERMINATED：线程池处在TIDYING状态时，执行完terminated()之后，就会由 TIDYING -> TERMINATED。线程池彻底终止，就变成TERMINATED状态。

*/

