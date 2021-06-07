package startspring2.com.example.cookpage.service.mapper;

import org.springframework.stereotype.Component;
import startspring2.com.example.cookpage.model.AmountOfIngredients;
import startspring2.com.example.cookpage.service.dto.AmountOfIngredientsDto;

@Component
public class AmountOfIngredientsDtoMapper {

    public AmountOfIngredientsDto toDto(AmountOfIngredients amount) {

        Integer ingredientId = amount.getIngredient().getId();

        Integer recipeId = amount.getRecipe().getId();

        return AmountOfIngredientsDto.builder()
                .id(amount.getId())
                .ingredientId(ingredientId)
                .recipeId(recipeId)
                .amount(amount.getAmount())
                .build();

    }
}
