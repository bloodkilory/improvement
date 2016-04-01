package com.block.eight;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

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

    static class NumSupplier implements Supplier<Double> {
        double value = 0.0;
        @Override
        public Double get() {
            this.value = this.value + 5;
            return value;
        }
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
        // ony way to generate infinite seq
        DoubleStream doubles = DoubleStream.iterate(1, d -> d + 3D);
        // the other way to generate infinite seq
        Stream<Double> numStream = Stream.generate(new NumSupplier());
        List<Double> list = numStream.skip(10).limit(10000000).collect(Collectors.toList());
        Double[] pop = list.toArray(new Double[list.size()]);
        System.out.println(pop.length + ": " + pop[200]);
        long m1 = System.currentTimeMillis();
        System.out.println("Fork/Join: " + varianceImperative(pop));
        long m2 = System.currentTimeMillis();
        System.out.println("Fork/Join：" + (m2-m1));
        long n1 = System.currentTimeMillis();
        System.out.println("Imper: " + varianceForkJoin(pop));
        long n2 = System.currentTimeMillis();
        System.out.println("Imper：" + (n2-n1));
    }

    /**
     * 使用.Fork/Join方式计算方差
     * @param population
     * @return
     */
    public static Double varianceForkJoin(Double[] population) {
        final ForkJoinPool forkJoinPool = new ForkJoinPool();
        double total = forkJoinPool.invoke(new ForkJoinCalculator(population, new SequentialCalculator() {
            @Override
            public Double computeSequentially(Double[] numbers, int start, int end) {
                Double total = 0.0;
                for(int i = start;i<end;i++) {
                    total += numbers[i];
                }
                return total;
            }
        }));
        final Double average = total / population.length;
        double variance = forkJoinPool.invoke(new ForkJoinCalculator(population, new SequentialCalculator() {
            @Override
            public Double computeSequentially(Double[] numbers, int start, int end) {
                Double variance = 0.0;
                for(int i = start; i < end; i++) {
                    variance += (numbers[i] - average) * (numbers[i] - average);
                }
                return variance;
            }
        }));
        return variance / population.length;
    }

    /**
     * 使用命令式风格计算方差
     * @param population
     * @return
     */
    public static Double varianceImperative(Double[] population){
        Double average = 0.0;
        for(Double p: population){
            average += p;
        }
        average /= population.length;

        Double variance = 0.0;
        for(Double p: population){
            variance += (p - average) * (p - average);
        }
        return variance/population.length;
    }
}
