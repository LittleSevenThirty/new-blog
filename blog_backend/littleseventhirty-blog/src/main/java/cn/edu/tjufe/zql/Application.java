package cn.edu.tjufe.zql;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.edu.tjufe.zql.mapper")  // 有了这个就不用给每个mapper接口写@Mapper注解，当然写了也没啥
@Slf4j
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info(
                "------------------------------------------------------------------------------" +
                        "\n" +
                        "          ╔══════════════════════════════════════════════════╗\n" +
                        "          ║           恭喜成功启动后端 —— 小七博客                ║\n" +
                        "          ╚══════════════════════════════════════════════════╝\n" +
                        "\n" +
                        "         _    _    _     _  \n" +
                        "        | |  | |  | |   | | \n" +
                        "        | |__| |  | |   | | \n" +
                        "        |  __  |  | |   | | \n" +
                        "        | |  | |  | |___| | \n" +
                        "        |_|  |_|  |_.___|_| \n" +
                        "\n"
        );
    }
}
