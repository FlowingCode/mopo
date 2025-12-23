package firitin;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This annotation tells Spring to start up and look for Views and AppShell
@SpringBootApplication
public class TestRunner {

  public static void main(String[] args) {
    // This launches the web server on localhost:9998
    SpringApplication app = new SpringApplication(TestRunner.class);

    // Force port 9998
    app.setDefaultProperties(Collections.singletonMap("server.port", "9998"));

    app.run(args);

    System.out.println("----------------------------------------------");
    System.out.println("  Test Server Running at http://localhost:9998");
    System.out.println("----------------------------------------------");
  }
}
