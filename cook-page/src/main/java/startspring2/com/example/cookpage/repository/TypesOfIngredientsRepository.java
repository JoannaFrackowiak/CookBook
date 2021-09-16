package startspring2.com.example.cookpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import startspring2.com.example.cookpage.model.TypesOfIngredients;

@Repository
public interface TypesOfIngredientsRepository extends JpaRepository <TypesOfIngredients, Integer> {

    TypesOfIngredients findTypesOfIngredientsById(Integer id);

}
