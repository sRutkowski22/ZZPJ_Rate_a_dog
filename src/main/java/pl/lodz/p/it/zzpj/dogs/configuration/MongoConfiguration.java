package pl.lodz.p.it.zzpj.dogs.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.lang.NonNull;

import java.util.Objects;

@Configuration
@AllArgsConstructor
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    private final Environment env;

    @Override
    @NonNull
    protected String getDatabaseName() {
        return Objects.requireNonNull(env.getProperty("DATABASENAME"));
    }

    @Override
    @NonNull
    public MongoClient mongoClient() {
        return MongoClients.create(Objects.requireNonNull(env.getProperty("CONNECTIONSTRING")));
    }
}
