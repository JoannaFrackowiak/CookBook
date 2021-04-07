package startspring2.com.example.cookpage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

    private int id;
    private String name;
    private TypesOfIngredients type;
    private String description;

//    private List<AmoutOfIngredients> recipesWithIngredients;
}
