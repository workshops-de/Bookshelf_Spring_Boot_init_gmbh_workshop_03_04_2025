package de.workshops.bookshelf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("prod")
class EnvironmentTest {

    @Value("${server.port}")
    int port;

    @Test
    @EnabledIf(expression = "#{environment.acceptsProfiles('prod')}", loadContext = true)
    void testActiveProfile() {
        assertThat(port).isEqualTo(9090);
    }

}


// you have to start this test with commpand-line option -Dspring.profiles.active=prod
// or adjust the run-configuration accordingly.
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class EnvironmentTest2 {

    @Value("${server.port}")
    int port;

    @Test
    @EnabledIf(expression = "#{environment['spring.profiles.active'] == 'prod'}", loadContext = true)
    void testPort() {
        assertThat(port).isEqualTo(9090);
    }
}
