package com.firstproject.firstproject.recipeboard.Repository;

import com.firstproject.firstproject.recipeboard.Entity.Recipe;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Long > {

    Recipe findBytitle(String title);
    List<Recipe> findAllByOrderByRegistDateDesc();

    List<Recipe> findAllByOrderByViewCountDesc();
    List<Recipe> findAllBymemberid(Long memberid);

}
