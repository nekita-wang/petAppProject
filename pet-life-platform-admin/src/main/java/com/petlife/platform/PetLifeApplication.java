package com.petlife.platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author pet-life
 */
@MapperScan({"com.petlife.platform.app.mapper", "com.petlife.platform.admin.mapper", "com.petlife.platform.system.mapper"})
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class }, 
                      scanBasePackages = {"com.petlife.platform"})
public class PetLifeApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(PetLifeApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  宠物一站式服务平台启动成功   ლ(´ڡ`ლ)ﾞ  \n"+
                "            _   \n" +
                " _ __   ___| |_ \n" +
                "| '_ \\ / _ \\ __|\n" +
                "| |_) |  __/ |_ \n" +
                "| .__/ \\___|\\__|\n" +
                "|_|             \n" );
    }
}
