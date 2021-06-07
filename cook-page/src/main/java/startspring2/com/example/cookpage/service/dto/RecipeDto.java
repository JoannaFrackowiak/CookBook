package startspring2.com.example.cookpage.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import startspring2.com.example.cookpage.model.RecipeLevel;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDto {

    private Integer id;
    private String name;
    private Integer time;
    private RecipeLevel level;
    private Integer typesOfRecipesId;
    private String details;
    private List<Integer> amountOfIngredientsId;

}
