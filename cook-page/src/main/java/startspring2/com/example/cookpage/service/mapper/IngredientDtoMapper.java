package startspring2.com.example.cookpage.service.mapper;

import org.springframework.stereotype.Component;
import startspring2.com.example.cookpage.model.AmountOfIngredients;
import startspring2.com.example.cookpage.model.Ingredient;
import startspring2.com.example.cookpage.service.dto.IngredientDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class IngredientDtoMapper {

    public IngredientDto toDto(Ingredient ingredient) {

        Integer typesOfIngredientsId = ingredient.getType().getId();

        List<Integer> amountOfIngredientsId = new ArrayList<>();

        for (AmountOfIngredients amount : ingredient.getRecipesWithIngredients()) {
            Integer amountId = amount.getId();
            amountOfIngredientsId.add(amountId);
        }

        return IngredientDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .typesOfIngredientsId(typesOfIngredientsId)
                .description(ingredient.getDescription())
                .amountOfIngredientsId(amountOfIngredientsId)
                .build();
    }
}
