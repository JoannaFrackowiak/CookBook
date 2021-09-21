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
    public List<AmountOfIngredientsDto> amountForRecipe(Integer recipeId) {
        List<AmountOfIngredients> amountOfIngredients = amountOfIngredientsRepository.findAllByRecipeId(recipeId);
        List<AmountOfIngredientsDto> amountsOfIngredientsDto = new ArrayList<>();
        for (AmountOfIngredients amount : amountOfIngredients) {
            AmountOfIngredientsDto amountOfIngredientsDto = amountOfIngredientsDtoMapper.toDto(amount);
            amountsOfIngredientsDto.add(amountOfIngredientsDto);
        }
        return amountsOfIngredientsDto;
    }

    @Transactional
    public List<AmountOfIngredientsDto> getAmountByIngredient(Integer idA, Integer idB, Integer idC) {
        List<AmountOfIngredients> amountA = amountOfIngredientsRepository.findAmountOfIngredientsByIngredientId(idA);
        List<Recipe> recipeListA = new ArrayList<>();
        List<AmountOfIngredientsDto> amountABC = new ArrayList<>();
        List<Recipe> recipeIdListAB = new ArrayList<>();
        for (AmountOfIngredients amount : amountA) {
            recipeListA.add(amount.getRecipe());
        }
        for (Recipe recipeA : recipeListA) {
            List<AmountOfIngredientsDto> amountRecipeA = amountForRecipe(recipeA.getId());
            for (AmountOfIngredientsDto amountDtoA : amountRecipeA) {
                if (amountDtoA.getIngredientId().equals(idB)) {
                    recipeIdListAB.add(recipeA);
                }
            }
        }
        for (Recipe recipeAB : recipeIdListAB) {
            List<AmountOfIngredientsDto> amountRecipeAB = amountForRecipe(recipeAB.getId());
            for (AmountOfIngredientsDto amountDtoAB : amountRecipeAB) {
                if (amountDtoAB.getIngredientId().equals(idC)) {
                    amountABC.add(amountDtoAB);
                }
            }
        }
        return amountABC;
    }


    @Transactional
    public AmountOfIngredientsDto showAmountOfIngredient(Integer id) {
        return amountOfIngredientsDtoMapper.toDto(amountOfIngredientsRepository.findAmountOfIngredientsById(id));
    }

    @Transactional
    public List<AmountOfIngredientsDto> newAmountOfIngredients(Integer recipeId, List<AmountOfIngredientsDto> ingredientsDtoList) {
        Recipe recipe = recipeRepository.getOne(recipeId);
        for (AmountOfIngredientsDto amountOfIngredientsDto : ingredientsDtoList) {
            AmountOfIngredients amountOfIngredients = AmountOfIngredients.builder()
                    .recipe(recipe)
                    .amount(amountOfIngredientsDto.getAmount())
                    .ingredientId(amountOfIngredientsDto.getIngredientId())
                    .build();
            AmountOfIngredients saved = amountOfIngredientsRepository.save(amountOfIngredients);
//            AmountOfIngredientsDto savedDto = amountOfIngredientsDtoMapper.toDto(saved);
//            amount.add(savedDto);
//            AmountOfIngredientsDto checkSave = savedDto;
        }
        return ingredientsDtoList;
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
