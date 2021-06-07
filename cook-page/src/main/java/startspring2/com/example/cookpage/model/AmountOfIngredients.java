package startspring2.com.example.cookpage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmountOfIngredients {

    private Integer id;
    private Ingredient ingredient;
    private Recipe recipe;
    private Integer amount;

}
