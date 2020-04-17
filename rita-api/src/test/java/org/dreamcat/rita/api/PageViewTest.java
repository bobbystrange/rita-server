package org.dreamcat.rita.api;

import org.dreamcat.common.util.PrintUtil;
import org.dreamcat.common.util.RandomUtil;
import org.dreamcat.common.web.core.PageView;
import org.dreamcat.common.web.util.JacksonUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by tuke on 2020/3/2
 */
public class PageViewTest {
    @Test
    public void test() {
        int total = RandomUtil.randi(4, 64);
        List<Integer> items = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            items.add(i * i);
        }

        for (int page = 1; page < total; page++) {
            for (int size = 1; size < total; size++) {
                PageView<Integer> view = new PageView<>(items, total, page, size);
                PrintUtil.printf("page=%d, size=%d:\t%s\n", page, size, JacksonUtil.toJson(view));
            }
        }
    }
}
