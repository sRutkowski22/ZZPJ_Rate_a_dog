package pl.lodz.p.it.zzpj.dogs.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document("accounts")
@Builder
public @Data class Account {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    @Builder.Default
    private List<String> favoriteDogs = new ArrayList<>();

    @Builder.Default
    private Map<String, Double> breedPreferences = new HashMap<>() {{
        put("affenpinscher", 1.0);
        put("african", 1.0);
        put("airedale", 1.0);
        put("akita", 1.0);
        put("appenzeller", 1.0);
        put("basenji", 1.0);
        put("beagle", 1.0);
        put("bluetick", 1.0);
        put("borzoi", 1.0);
        put("bouvier", 1.0);
        put("boxer", 1.0);
        put("brabancon", 1.0);
        put("briard", 1.0);
        put("cairn", 1.0);
        put("chihuahua", 1.0);
        put("chow", 1.0);
        put("clumber", 1.0);
        put("cockapoo", 1.0);
        put("coonhound", 1.0);
        put("cotondetulear", 1.0);
        put("dachshund", 1.0);
        put("dalmatian", 1.0);
        put("dhole", 1.0);
        put("dingo", 1.0);
        put("doberman", 1.0);
        put("entlebucher", 1.0);
        put("eskimo", 1.0);
        put("germanshepherd", 1.0);
        put("groenendael", 1.0);
        put("havanese", 1.0);
        put("husky", 1.0);
        put("keeshond", 1.0);
        put("kelpie", 1.0);
        put("komondor", 1.0);
        put("kuvasz", 1.0);
        put("labrador", 1.0);
        put("leonberg", 1.0);
        put("lhasa", 1.0);
        put("malamute", 1.0);
        put("malinois", 1.0);
        put("maltese", 1.0);
        put("mexicanhairless", 1.0);
        put("mix", 1.0);
        put("newfoundland", 1.0);
        put("otterhound", 1.0);
        put("papillon", 1.0);
        put("pekinese", 1.0);
        put("pembroke", 1.0);
        put("pitbull", 1.0);
        put("pomeranian", 1.0);
        put("pug", 1.0);
        put("puggle", 1.0);
        put("pyrenees", 1.0);
        put("redbone", 1.0);
        put("rottweiler", 1.0);
        put("saluki", 1.0);
        put("samoyed", 1.0);
        put("schipperke", 1.0);
        put("shiba", 1.0);
        put("shihtzu", 1.0);
        put("stbernard", 1.0);
        put("vizsla", 1.0);
        put("weimaraner", 1.0);
        put("whippet", 1.0);
        put("australian", 1.0);
        put("buhund", 1.0);
        put("bulldog", 1.0);
        put("bullterrier", 1.0);
        put("cattledog", 1.0);
        put("collie", 1.0);
        put("corgi", 1.0);
        put("dane", 1.0);
        put("deerhound", 1.0);
        put("elkhound", 1.0);
        put("finnish", 1.0);
        put("frise", 1.0);
        put("greyhound", 1.0);
        put("hound", 1.0);
        put("mastiff", 1.0);
        put("mountain", 1.0);
        put("ovcharka", 1.0);
        put("pinscher", 1.0);
        put("pointer", 1.0);
        put("poodle", 1.0);
        put("retriever", 1.0);
        put("ridgeback", 1.0);
        put("schnauzer", 1.0);
        put("setter", 1.0);
        put("sheepdog", 1.0);
        put("spaniel", 1.0);
        put("springer", 1.0);
        put("terrier", 1.0);
        put("waterdog", 1.0);
        put("wolfhound", 1.0);
    }};
}
