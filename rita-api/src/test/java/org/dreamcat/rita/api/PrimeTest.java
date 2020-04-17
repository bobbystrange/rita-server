package org.dreamcat.rita.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by tuke on 2020/3/25
 */
public class PrimeTest {
    public static void main2(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> nums = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            int n = Integer.parseInt(line);
            if (n == 0) break;
            nums.add(n);
        }

        nums.forEach(it -> {
            System.out.println(count(it));
        });
        System.out.println("end");
    }


    public static void main(String[] args) {
        //for (int i=2; i<100; i++){
        //    if (isPrime(i))
        //    System.out.printf("%d\n", i);
        //}

        int[] input = new int[]{2, 5, 10, 18};
        for (int i : input) {

            System.out.printf("%d\t%d\n", i, count(i));
        }

        //for (int i=0; i<1000; i++){
        //    System.out.printf("%d\t%d\n", i, count(i));
        //}
    }

    public static int count(int n) {
        int c = 0;
        int half = n / 2;
        for (int i = 2; i <= half; i++) {
            if (isPrime(i) && isPrime(n - i)) c++;
        }
        return c;
    }

    public static boolean isPrime(int n) {
        if (n == 2 || n == 3 || n == 5 || n == 7) return true;
        if (n == 4 || n == 6) return false;

        int bound = (int) (Math.floor(Math.pow(n, 1. / 3)) + 1);
        for (int i = 2; i <= bound; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }


}
