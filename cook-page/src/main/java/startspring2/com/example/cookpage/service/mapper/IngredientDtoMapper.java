package startspring2.com.example.cookpage.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import startspring2.com.example.cookpage.model.Ingredient;
import startspring2.com.example.cookpage.model.TypesOfIngredients;
import startspring2.com.example.cookpage.repository.TypesOfIngredientsRepository;
import startspring2.com.example.cookpage.service.dto.CreateUpdateIngredientDto;
import startspring2.com.example.cookpage.service.dto.IngredientDto;

@Component
public class IngredientDtoMapper {

    @Autowired
    private TypesOfIngredientsRepository typesOfIngredientsRepository;

    public IngredientDto toDto(Ingredient ingredient) {

        Integer typesOfIngredientsId = ingredient.getType().getId();

        return IngredientDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .unit(ingredient.getUnit())
                .typeOfIngredientId(typesOfIngredientsId)
                .description(ingredient.getDescription())
                .build();
    }

    public Ingredient fromDto(CreateUpdateIngredientDto createUpdateIngredientDto) {

        TypesOfIngredients typesOfIngredients = typesOfIngredientsRepository.findTypesOfIngredientsById(createUpdateIngredientDto.getTypeOfIngredientsId());

        return Ingredient.builder()
                .name(createUpdateIngredientDto.getName())
                .unit(createUpdateIngredientDto.getUnit())
                .type(typesOfIngredients)
                .description(createUpdateIngredientDto.getDescription())
                .build();

    }
}
