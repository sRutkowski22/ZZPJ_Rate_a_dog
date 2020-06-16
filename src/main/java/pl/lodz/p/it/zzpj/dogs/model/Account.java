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
        put("affenpinscher", 2.0);
        put("african", 2.0);
        put("airedale", 2.0);
        put("akita", 2.0);
        put("appenzeller", 2.0);
        put("basenji", 2.0);
        put("beagle", 2.0);
        put("bluetick", 2.0);
        put("borzoi", 2.0);
        put("bouvier", 2.0);
        put("boxer", 2.0);
        put("brabancon", 2.0);
        put("briard", 2.0);
        put("cairn", 2.0);
        put("chihuahua", 2.0);
        put("chow", 2.0);
        put("clumber", 2.0);
        put("cockapoo", 2.0);
        put("coonhound", 2.0);
        put("cotondetulear", 2.0);
        put("dachshund", 2.0);
        put("dalmatian", 2.0);
        put("dhole", 2.0);
        put("dingo", 2.0);
        put("doberman", 2.0);
        put("entlebucher", 2.0);
        put("eskimo", 2.0);
        put("germanshepherd", 2.0);
        put("groenendael", 2.0);
        put("havanese", 2.0);
        put("husky", 2.0);
        put("keeshond", 2.0);
        put("kelpie", 2.0);
        put("komondor", 2.0);
        put("kuvasz", 2.0);
        put("labrador", 2.0);
        put("leonberg", 2.0);
        put("lhasa", 2.0);
        put("malamute", 2.0);
        put("malinois", 2.0);
        put("maltese", 2.0);
        put("mexicanhairless", 2.0);
        put("mix", 2.0);
        put("newfoundland", 2.0);
        put("otterhound", 2.0);
        put("papillon", 2.0);
        put("pekinese", 2.0);
        put("pembroke", 2.0);
        put("pitbull", 2.0);
        put("pomeranian", 2.0);
        put("pug", 2.0);
        put("puggle", 2.0);
        put("pyrenees", 2.0);
        put("redbone", 2.0);
        put("rottweiler", 2.0);
        put("saluki", 2.0);
        put("samoyed", 2.0);
        put("schipperke", 2.0);
        put("shiba", 2.0);
        put("shihtzu", 2.0);
        put("stbernard", 2.0);
        put("vizsla", 2.0);
        put("weimaraner", 2.0);
        put("whippet", 2.0);
        put("australian", 2.0);
        put("buhund", 2.0);
        put("bulldog", 2.0);
        put("bullterrier", 2.0);
        put("cattledog", 2.0);
        put("collie", 2.0);
        put("corgi", 2.0);
        put("dane", 2.0);
        put("deerhound", 2.0);
        put("elkhound", 2.0);
        put("finnish", 2.0);
        put("frise", 2.0);
        put("greyhound", 2.0);
        put("hound", 2.0);
        put("mastiff", 2.0);
        put("mountain", 2.0);
        put("ovcharka", 2.0);
        put("pinscher", 2.0);
        put("pointer", 2.0);
        put("poodle", 2.0);
        put("retriever", 2.0);
        put("ridgeback", 2.0);
        put("schnauzer", 2.0);
        put("setter", 2.0);
        put("sheepdog", 2.0);
        put("spaniel", 2.0);
        put("springer", 2.0);
        put("terrier", 2.0);
        put("waterdog", 2.0);
        put("wolfhound", 2.0);
    }};
}
