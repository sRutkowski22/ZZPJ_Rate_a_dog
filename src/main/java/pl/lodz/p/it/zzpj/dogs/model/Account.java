package pl.lodz.p.it.zzpj.dogs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("Accounts")
@Data
public class Account {

    @Id
    private String id;
    private String name;
    private String surname;
    private String login;
    private String password;

    public Account(){
        this.id =UUID.randomUUID().toString();
    }
}
