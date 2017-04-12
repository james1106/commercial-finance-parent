package com.xiangan.platform.chainserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 单元测试
 *
 * @Creater liuzhudong
 * @Date 2017/4/12 14:05
 * @Version 1.0
 * @Copyright
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {
    protected final Logger logger = LoggerFactory.getLogger(super.getClass());

    @Test
    public void configTest() throws Exception {
        logger.info("config test | OK ..............");

    }
}