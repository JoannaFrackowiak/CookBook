package startspring2.com.example.cookpage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Ingredient {

    private Integer id;
    private String name;
    private TypesOfIngredients type;
    private String description;

    private List<AmountOfIngredients> recipesWithIngredients;
}
