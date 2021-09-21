package startspring2.com.example.cookpage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import startspring2.com.example.cookpage.controller.exception.AlreadyExistsException;
import startspring2.com.example.cookpage.model.Ingredient;
import startspring2.com.example.cookpage.repository.IngredientRepository;
import startspring2.com.example.cookpage.service.dto.AmountOfIngredientsDto;
import startspring2.com.example.cookpage.service.dto.CreateUpdateIngredientDto;
import startspring2.com.example.cookpage.service.dto.IngredientDto;
import startspring2.com.example.cookpage.service.mapper.IngredientDtoMapper;

import java.util.ArrayList;
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
    public List<IngredientDto> showAllIngredients() {
        List<IngredientDto> ingredientDtoList = new ArrayList<>();
        for (Ingredient ingredient : ingredientRepository.findAll()) {
            ingredientDtoList.add(ingredientDtoMapper.toDto(ingredient));
        }
        return ingredientDtoList;
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

    public IngredientDto addNewIngredient(CreateUpdateIngredientDto createUpdateIngredientDto) throws AlreadyExistsException {
        List<Ingredient> ingredientsList = ingredientRepository.findAll();
        for (Ingredient ingredient : ingredientsList) {
            if (ingredient.getName().equals(createUpdateIngredientDto.getName())) {
                throw new AlreadyExistsException();
            }
        }
        Ingredient newIngredient = ingredientDtoMapper.fromDto(createUpdateIngredientDto);
        Ingredient savedIngredient = ingredientRepository.save(newIngredient);
        return ingredientDtoMapper.toDto(savedIngredient);

    }

//    @Transactional
//    public IngredientDto addNewIngredient(@RequestBody CreateUpdateIngredientDto createUpdateIngredientDto) {
//
//    }
    //add method 'addNewIngredient'
}
