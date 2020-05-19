package pl.lodz.p.it.zzpj.dogs;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@TestConfiguration
public class TestMongoConfiguration extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "test";
    }
}
