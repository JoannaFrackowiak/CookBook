package startspring2.com.example.cookpage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import startspring2.com.example.cookpage.repository.IngredientRepository;
import startspring2.com.example.cookpage.service.dto.AmountOfIngredientsDto;
import startspring2.com.example.cookpage.service.dto.IngredientDto;
import startspring2.com.example.cookpage.service.mapper.IngredientDtoMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IngredientService {

    @Autowired
    private IngredientDtoMapper ingredientDtoMapper;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private AmountOfIngredientsService amountOfIngredientsService;

    @Transactional
    public IngredientDto showIngredient(Integer ingredientId) {
        return ingredientDtoMapper.toDto(ingredientRepository.findIngredientById(ingredientId));
    }

    @Transactional
    public Map<IngredientDto, Integer> ingredientsInRecipe(List<Integer> amountOfIngredientsId) {
        Map<IngredientDto, Integer> ingredientsWithAmount = new HashMap<>();
        for (Integer amountId : amountOfIngredientsId) {
            AmountOfIngredientsDto amountOfIngredientsDto = amountOfIngredientsService.showAmountOfIngredient(amountId);
            IngredientDto ingredientDto = showIngredient(amountOfIngredientsDto.getIngredientId());
            ingredientsWithAmount.put(ingredientDto, amountOfIngredientsDto.getAmount());
        }
        return ingredientsWithAmount;
    }

//    @Transactional
//    public IngredientDto addNewIngredient(@RequestBody CreateUpdateIngredientDto createUpdateIngredientDto) {
//
//    }
    //add method 'addNewIngredient'
}
