package startspring2.com.example.cookpage.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import startspring2.com.example.cookpage.model.RecipeLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUpdateRecipeDto {

    private String name;
    private Integer time;
    private RecipeLevel level;
    private Integer typeOfRecipeId;
    private String details;
    private List<AmountOfIngredientsDto> ingredientsWithAmount = new ArrayList<>();

    public void addAmount (AmountOfIngredientsDto amountDto) {
        this.ingredientsWithAmount.add(amountDto);
    }

}
