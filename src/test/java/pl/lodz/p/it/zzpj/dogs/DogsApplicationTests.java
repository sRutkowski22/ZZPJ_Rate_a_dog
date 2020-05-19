package pl.lodz.p.it.zzpj.dogs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestMongoConfiguration.class)
class DogsApplicationTests {

	@Test
	void contextLoads() {
	}

}
