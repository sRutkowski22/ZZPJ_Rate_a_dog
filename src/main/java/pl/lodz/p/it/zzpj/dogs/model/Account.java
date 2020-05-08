package pl.lodz.p.it.zzpj.dogs.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("accounts")
@Data
public class Account {

    @Id
    private String id;
    private String name;
    private String surname;
    private String login;
    private String password;

    public Account() {
        this.id = UUID.randomUUID().toString();
    }
}
