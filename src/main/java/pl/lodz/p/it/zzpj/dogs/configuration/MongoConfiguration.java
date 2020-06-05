package pl.lodz.p.it.zzpj.dogs.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Objects;

@Configuration
@AllArgsConstructor
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    private final Environment env;

    @Override
    protected String getDatabaseName() {
        return Objects.requireNonNull(env.getProperty("DATABASENAME"));
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(Objects.requireNonNull(env.getProperty("CONNECTIONSTRING")));
    }
}
