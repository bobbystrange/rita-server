package org.dreamcat.rita.api;

import lombok.extern.slf4j.Slf4j;
import org.dreamcat.common.web.jwt.Jwt;
import org.dreamcat.common.web.util.JacksonUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.dreamcat.common.util.RandomUtil.*;

/**
 * Create by tuke on 2020/2/26
 */
@Slf4j
public class JWTTest {
    public static void main2(String[] args) {
        Object lock = new Object();

        new Thread(() -> {
            int i = 1;
            while (i < 100) {
                System.out.println(i);
                lock.notify();
                try {
                    lock.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
                i = i + 2;
            }
        }).start();

        new Thread(() -> {
            int i = 2;
            while (i < 100) {
                try {
                    lock.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
                System.out.println(i);
                i = i + 2;
                lock.notifyAll();
                ;

            }
        }).start();
    }

    @Test
    public void test() {
        Jwt jwt = new Jwt();
        jwt.setSubject(uuid());
        jwt.setPermissions(Arrays.asList(
                choose72(randi(1, 10)),
                choose72(randi(1, 10)),
                choose72(randi(1, 10))));
        jwt.setExpiredAt(System.currentTimeMillis() - randi(2 << 8, 2 << 16));
        jwt.setIssuedAt(System.currentTimeMillis());
        String json = JacksonUtil.toJson(jwt);
        log.info("\n\n{}\n\n", json);

    }


}
