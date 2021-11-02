package auto;

import core.utils.ContextUtil;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"api.*","core.*","ui.*"})
public class AutomateApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        SpringApplication application = builder.sources(AutomateApplication.class)
                .web(WebApplicationType.SERVLET)
                .bannerMode(Banner.Mode.LOG)
                .build();
        ConfigurableApplicationContext context = application.run(args);
        ContextUtil.setContext(context);
    }
}
