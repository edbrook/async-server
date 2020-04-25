package uk.co.edbrook.async;

import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
class AsyncServerApplicationTests {

//    @Autowired
    private ApplicationContext context;

//    @Test
    void contextLoads() {
        assertThat(context).isNotNull();
    }

}
