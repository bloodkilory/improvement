package com.block.eight;

/**
 * Created by bloodkilory on 2016/6/16.
 */
    import java.util.ArrayList;
    import java.util.List;
    import java.util.concurrent.atomic.AtomicInteger;

    /**
     * @author yangkun
     *         <p>测试基于自旋CAS原子操作的线程安全计数器方法和普通非线程安全计数器方法</p>
     *         generate on 16/5/5
     */
    public class CountDemo {
        private AtomicInteger atomicI = new AtomicInteger(0);
        private AtomicInteger atomicJ = new AtomicInteger(0);
        private int i = 0;
        private volatile int vi = 0;

        public static void main(String[] args) {
            final CountDemo cas = new CountDemo();
            List<Thread> ts = new ArrayList<>(600);
            long start = System.currentTimeMillis();
            for(int j = 0; j < 100; j++) {
                Thread t = new Thread(() -> {
                    for(int i1 = 0; i1 < 10000; i1++) {
                        cas.count();
                        cas.safeCount();
                        cas.vcount();
                        cas.count2();
                    }
                });
                ts.add(t);
            }
            ts.forEach(Thread::start);

            // 将所有线程join到main线程中,防止mian方法结束时计算线程还未结束
            for(Thread t : ts) {
                try {
                    t.join();
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("int: " + cas.i);
            System.out.println("cas: " + cas.atomicI.get());
            System.out.println("getAndIncr: " + cas.atomicJ.get());
            System.out.println("volatile: " + cas.vi);
            System.out.println(String.format("Time: %d ms", System.currentTimeMillis() - start));
        }

        private void safeCount() {
            for(; ; ) {
                int i = atomicI.get();
                boolean suc = atomicI.compareAndSet(i, ++i);
                if(suc) {
                    break;
                }
            }
        }

        /**
         * 实际上就是封装了safeCount里的永真循环
         */
        private void count2() {
            atomicJ.getAndIncrement();
        }

        private void count() {
            i++;
        }

        private void vcount() {
            vi++;
        }
    }
