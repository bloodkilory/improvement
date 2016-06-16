package com.block.eight;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bloodkilory on 2016/6/6.
 */
public class NewExample {

    private static AtomicInteger atomI = new AtomicInteger(0);
    public static void main(String[] args) {
        List<String> li = new ArrayList<>();
        li.add("b");
        li.add("a");
        li.add("c");
        li.add("d");
        li.add("a");

        // 虽然这样也能实现，但不要这样用，时间复杂度最差会为O(n2)
        for(int i = 0;i < li.size(); i++) {
            li.remove("a");
        }

        // 时间复杂度只有O(n)
        for(int i = 0; i< li.size();i++) {
            String a = li.get(i);
            if (a.equals("a")) {
                li.remove(i);
            }
        }

        Iterator it = li.iterator();
        while (it.hasNext()) {
            String s = (String) it.next();
            if (s.equals("a")) {
                it.remove();
            }
        }
        System.out.println(li);
    }

    private void safeCount() {

        while (true) {
            int i = atomI.get();
            boolean suc = atomI.compareAndSet(i, ++i);
            if (suc) {
                break;
            }
        }
    }
}
