package com.xhsoft.demo.authorization;


import com.xhsoft.demo.authorization.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import javax.inject.Inject;
import java.net.InetAddress;
import java.net.UnknownHostException;


@SpringBootApplication
public class AuthorizationWebApplication {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationWebApplication.class);

    @Inject
    private Environment env;

    /**
     * Main method, used to run the application.
     */
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(AuthorizationWebApplication.class);
        SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
        addDefaultProfile(app, source);

        Environment env = app.run(args).getEnvironment();
        log.info("Access URLs:\n----------------------------------------------------------\n\t" +
                        "Local: \t\thttp://localhost:{}{}\n\t" +
                        "External: \thttp://{}:{}{}\n----------------------------------------------------------",
                env.getProperty("server.port"),
                env.getProperty("server.contextPath"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                env.getProperty("server.contextPath"));

    }

    /**
     * If no profile has been configured, set by default the "dev" profile.
     */
    private static void addDefaultProfile(SpringApplication app, SimpleCommandLinePropertySource source) {
        if (!source.containsProperty("spring.profiles.active") &&
                !System.getenv().containsKey("SPRING_PROFILES_ACTIVE")) {

            app.setAdditionalProfiles(Constants.SPRING_PROFILE_DEVELOPMENT);
        }
    }

//    @Bean
//    RequestDumperFilter requestDumperFilter() {
//        return new RequestDumperFilter();
//    }
}
