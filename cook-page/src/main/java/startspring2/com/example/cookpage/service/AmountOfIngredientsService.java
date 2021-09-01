package startspring2.com.example.cookpage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import startspring2.com.example.cookpage.model.AmountOfIngredients;
import startspring2.com.example.cookpage.model.Recipe;
import startspring2.com.example.cookpage.repository.AmountOfIngredientsRepository;
import startspring2.com.example.cookpage.repository.RecipeRepository;
import startspring2.com.example.cookpage.service.dto.AmountOfIngredientsDto;
import startspring2.com.example.cookpage.service.dto.IngredientDto;
import startspring2.com.example.cookpage.service.mapper.AmountOfIngredientsDtoMapper;

import java.util.*;

@Service
public class AmountOfIngredientsService {

    @Autowired
    private AmountOfIngredientsRepository amountOfIngredientsRepository;
    @Autowired
    private AmountOfIngredientsDtoMapper amountOfIngredientsDtoMapper;
    @Autowired
    private RecipeRepository recipeRepository;

    @Transactional
    public List<AmountOfIngredientsDto> newAmountOfIngredients(Integer recipeId, Map<IngredientDto, Integer> ingredientMap) {

        Recipe recipe = recipeRepository.getOne(recipeId);
        List<AmountOfIngredientsDto> amount = new ArrayList<>();
        Set<Map.Entry<IngredientDto, Integer>> ingredients = ingredientMap.entrySet();
        for (Map.Entry<IngredientDto, Integer> ingredient : ingredients) {
            AmountOfIngredients amountOfIngredients = AmountOfIngredients.builder()
                    .recipe(recipe)
                    .amount(ingredient.getValue())
                    .ingredientId(ingredient.getKey().getId())
                    .build();
            AmountOfIngredients saved = amountOfIngredientsRepository.save(amountOfIngredients);
            AmountOfIngredientsDto amountOfIngredientsDto = amountOfIngredientsDtoMapper.toDto(saved);
            amount.add(amountOfIngredientsDto);
        }
        return amount;
    }

    @Transactional
    public List<AmountOfIngredientsDto> deleteAmountOfIngredients(Integer recipeId) {
        List<AmountOfIngredients> amountOfIngredientsList = amountOfIngredientsRepository.findAllByRecipeId(recipeId);
        for (AmountOfIngredients amountOfIngredients : amountOfIngredientsList) {
            amountOfIngredientsRepository.delete(amountOfIngredients);
        }
        List<AmountOfIngredientsDto> amountOfIngredientsDtoList = new ArrayList<>();
        for (AmountOfIngredients amountOfIngredients : amountOfIngredientsList) {
            amountOfIngredientsDtoList.add(amountOfIngredientsDtoMapper.toDto(amountOfIngredients));
        }
        return amountOfIngredientsDtoList;
    }

}
