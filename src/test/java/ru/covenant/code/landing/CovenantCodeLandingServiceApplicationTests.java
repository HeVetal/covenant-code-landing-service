package ru.covenant.code.landing;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "admin.default.username=testadmin",
        "admin.default.password=testpassword"
})
class CovenantCodeLandingServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
