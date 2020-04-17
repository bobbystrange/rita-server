package org.dreamcat.rita.jpa;

import org.dreamcat.common.web.util.BeanCopierUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Create by tuke on 2020/3/10
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"org.dreamcat.rita"})
public class RitaJpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(RitaJpaApplication.class, args);
    }

    @PostConstruct
    public void sweep() {
        BeanCopierUtil.runSweeper(10, TimeUnit.MINUTES, 10, TimeUnit.MINUTES);
    }
}
