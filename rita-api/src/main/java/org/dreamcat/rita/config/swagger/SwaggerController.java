package org.dreamcat.rita.config.swagger;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Create by tuke on 2020/2/14
 */
@Profile({"dev"})
@Controller
public class SwaggerController {
    @RequestMapping(value = "/swagger")
    public String index() {
        return "redirect:swagger-ui.html";
    }
}
