package cn.edu.tjufe.zql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
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
