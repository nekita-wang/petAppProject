package com.petlife.platform.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 宠爱APP启动程序
 *
 * @author petlife
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan("com.petlife.platform.app.mapper")
public class PetLifeAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetLifeAppApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  宠爱APP启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
