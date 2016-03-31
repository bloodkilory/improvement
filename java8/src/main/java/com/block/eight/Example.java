package com.block.eight;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by bloodkilory on 2016/3/30.
 */
public class Example {

    private List<People> people;

    /**
     * 根据条件筛选数字集合
     */
    public void nums() {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        nums.stream().filter(x -> x % 2 == 0).map(x -> x * x).forEach(System.out::println);
    }

    public void initPeople() {
        people = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            people.add(new People(i, "name_" + i, "job_" + i));
        }
    }

    /**
     * 从Collection中过滤出某字段组成集合
     */
    public void getPeopleNames() {
        initPeople();
        List<String> nlist = people.stream().map(People::getName).collect(Collectors.toCollection(ArrayList::new));
        Set<String> nSet = people.stream().map(People::getName).collect(Collectors.toCollection(TreeSet::new));
        String names = nlist.stream().map(Object::toString).collect(Collectors.joining(","));
        System.out.println(names);
    }

    /**
     * 欧拉级数加速公式Function
     */
    class EulerTransform implements Function<Double, Double> {

        double n1 = 0.0;
        double n2 = 0.0;
        double n3 = 0.0;

        @Override
        public Double apply(Double t) {
            n1 = n2;
            n2 = n3;
            n3 = t;
            if (n1 == 0.0) {
                return 0.0;
            }
            return calc();
        }

        double calc() {
            double d = n3 - n2;
            return n3 - d * d / (n1 - 2 * n2 + n3);
        }
    }

    public static void main(String[] args) {
        Example e = new Example();
        e.getPeopleNames();
    }
}
