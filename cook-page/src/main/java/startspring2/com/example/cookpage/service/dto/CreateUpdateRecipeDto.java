package startspring2.com.example.cookpage.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import startspring2.com.example.cookpage.model.RecipeLevel;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUpdateRecipeDto {

    private String name;
    private Integer time;
    private RecipeLevel level;
    private Integer typesOfRecipesId;
    private String details;
    private Map<IngredientDto, Integer> ingredientsWithAmount = new HashMap<>();
    //Is IngredientDto right? Maybe only name ingredient as String?
}
