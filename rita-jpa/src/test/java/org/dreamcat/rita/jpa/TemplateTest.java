package org.dreamcat.rita.jpa;

import lombok.extern.slf4j.Slf4j;
import org.dreamcat.common.web.template.ThymeleafProcesser;
import org.junit.jupiter.api.Test;
import org.thymeleaf.context.Context;

/**
 * Create by tuke on 2020/3/12
 */
@Slf4j
public class TemplateTest {

    @Test
    public void test() {
        ThymeleafProcesser processer = new ThymeleafProcesser();
        Context context = new Context();
        context.setVariable("url", "some url");
        log.info("\n{}", processer.process("templates/register-confirm", context));
    }
}
