package startspring2.com.example.cookpage.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import startspring2.com.example.cookpage.controller.exception.AlreadyExistsException;
import startspring2.com.example.cookpage.model.TypesOfIngredients;
import startspring2.com.example.cookpage.service.IngredientService;
import startspring2.com.example.cookpage.service.TypesOfIngredientsService;
import startspring2.com.example.cookpage.service.dto.CreateUpdateIngredientDto;
import startspring2.com.example.cookpage.service.dto.IngredientDto;

@Controller
public class IngredientViewController {

    @Autowired
    private TypesOfIngredientsService typesOfIngredientsService;
    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/add-ingredient")
    public ModelAndView displayCreateIngredient() {
        ModelAndView modelAndView = new ModelAndView("add-ingredient");
        CreateUpdateIngredientDto createUpdateIngredientDto = new CreateUpdateIngredientDto();
        modelAndView.addObject("createUpdateIngredientDto", createUpdateIngredientDto);
        modelAndView.addObject("typesList", typesOfIngredientsService.getAllTypes());
        return modelAndView;
    }

    @PostMapping("/add-ingredient")
    public String createIngredient(@ModelAttribute CreateUpdateIngredientDto createUpdateIngredientDto) throws AlreadyExistsException {
        IngredientDto ingredientDto = ingredientService.addNewIngredient(createUpdateIngredientDto);
        return "redirect:/home-page";
    }


}
