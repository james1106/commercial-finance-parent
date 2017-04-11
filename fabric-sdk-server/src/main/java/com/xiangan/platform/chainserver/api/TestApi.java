package com.xiangan.platform.chainserver.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * test
 *
 * @Creater liuzhudong
 * @Date 2017/4/10 09:40
 * @Version 1.0
 * @Copyright
 */
@RestController
public class TestApi {

    final Logger logger = LoggerFactory.getLogger(TestApi.class);

    @Value("${test.message}")
    private String message;

    @RequestMapping("/test/get/{id}/{action}")
    public String test(@PathVariable int id, @PathVariable String action) {
        logger.info("tets api params:id={},action={}", id, action);

        return String.format(message + " id = %s , action = %s .", id, action);
    }
}
