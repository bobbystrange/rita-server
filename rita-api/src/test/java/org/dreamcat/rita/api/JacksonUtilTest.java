package org.dreamcat.rita.api;

import lombok.extern.slf4j.Slf4j;
import org.dreamcat.common.core.Pair;
import org.dreamcat.common.web.util.JacksonUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JacksonUtilTest {
    private ConcurrentMap<String, Pair<String, Long>> cache = new ConcurrentHashMap<>();

    @Test
    public void test() {
        log.warn("cache is {}", JacksonUtil.toJson(cache));
        cache.put("foo", new Pair<String, Long>("bar", 0L));
        log.warn("cache is {}", JacksonUtil.toJson(cache));
    }

    @Test
    public void test2() throws Exception {
        log.info("{}", Arrays.toString("abc".split("")));
        log.info("{}", wordPattern("", "北京 杭州 杭州 北京"));
        log.info("{}", wordPattern("abba", "北京 杭州 杭州 北京"));
        log.info("{}", wordPattern("aabb", "北京 杭州 杭州 北京"));
        log.info("{}", wordPattern("abcd", "北京 杭州 杭州 南京"));
        log.info("{}", wordPattern("acac", "北京 杭州 北京 广州"));
        log.info("{}", wordPattern("acabc", "北京 杭州 北京 广州 杭州"));

        //print();
    }

    //  1.  pattern = "abba", str="北京 杭州 杭州 北京" 返回 ture
    //  2.  pattern = "aabb", str="北京 杭州 杭州 北京" 返回 false
    //  3.  pattern = "abc", str="北京 杭州 杭州 南京" 返回 false
    //  4.  pattern = "acac", str="北京 杭州 北京 广州" 返回 false
    public boolean wordPattern(String pattern, String str) {
        String[] chars = pattern.split("");
        String[] strs = str.split(" ");
        int size = chars.length;
        if (size != strs.length) return false;
        int maxIndex = 1;
        Map<String, Integer> charMap = new HashMap<>();
        Map<String, Integer> strMap = new HashMap<>();
        for (int i = 0; i < size; i++) {
            String c = chars[i];
            String s = strs[i];

            Integer ci = charMap.get(c);
            Integer si = strMap.get(s);
            if (!Objects.equals(ci, si)) return false;

            if (ci == null) {
                charMap.put(c, maxIndex);
                strMap.put(s, maxIndex++);
            } else if (!ci.equals(si)) {
                return false;
            }
        }
        return true;
    }

    public boolean wordPattern2(String pattern, String str) {
        String[] chars = pattern.split("");
        String[] strs = str.split(" ");
        int size = chars.length;
        if (size != strs.length) return false;

        String s = String.join(" ", chars);

        for (int i = 0; i < size; i++) {
            String replaced = s.replaceAll(chars[i], strs[i]);
            if (!replaced.equals(s)) return false;
            //log.info(s);
        }
        return s.equals(str);
    }

    public void print() throws Exception {

        ArrayBlockingQueue<Integer> queue1 = new ArrayBlockingQueue<>(1);
        ArrayBlockingQueue<Integer> queue2 = new ArrayBlockingQueue<>(1);

        queue1.put(1);

        new Thread(() -> {
            while (true) {
                int i = 0;
                try {
                    i = queue1.take();
                    if (i > 100) break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(i);
                try {
                    queue1.put(i + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                int i = 0;
                try {
                    i = queue2.take();
                    if (i > 100) break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(i);
                try {
                    queue1.put(i + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Test
    public void car() throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(100);
        for (int i = 1; i <= 100; i++) {
            es.submit(new Car(i));
        }

        es.awaitTermination(1 << 12, TimeUnit.SECONDS);

    }

    public static class Car implements Runnable {
        private int i;
        private long distance;

        public Car(int i) {
            this.i = i;
        }

        public static void main(String[] args) throws InterruptedException {
            ExecutorService es = Executors.newFixedThreadPool(100);
            for (int i = 1; i <= 100; i++) {
                es.submit(new Car(i));
            }

            es.awaitTermination(1 << 12, TimeUnit.SECONDS);
        }

        @Override
        public void run() {
            int v = (int) Math.floor(i * i * Math.random());
            while (distance < 1000) {
                this.distance += v;
                System.out.printf("Car %d's distance: %s\n", i, distance);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
            System.out.printf("Car %d's finish the tour\n", i);
        }
    }

}
