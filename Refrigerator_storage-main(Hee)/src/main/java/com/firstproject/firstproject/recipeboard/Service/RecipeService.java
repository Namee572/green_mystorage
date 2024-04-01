package com.firstproject.firstproject.recipeboard.Service;

import com.firstproject.firstproject.recipeboard.Dto.RecipeDto;
import com.firstproject.firstproject.recipeboard.Entity.Recipe;
import com.firstproject.firstproject.recipeboard.Repository.RecipeRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    // insert
    public Recipe insert(RecipeDto recipeDto){
        Recipe recipe = new Recipe();
        recipe.setTitle(recipeDto.getTitle());
        recipe.setContent(recipeDto.getContent());
        recipe.setUpdateDate(LocalDateTime.now());
        recipe.setRegistDate(LocalDateTime.now());
        return recipeRepository.save(recipe);
    }
    public Recipe select(Long id){ // id로 찾는 방법 // 너도 그냥 해둘게
        Optional<Recipe>optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isEmpty()){
            return null;
        }
        Recipe recipe = optionalRecipe.get();
        recipe.setViewCount(recipe.getViewCount()+1);
        recipeRepository.save(recipe);
        return recipe;

    }
    public Recipe selecttitle(String title){ // title로 찾는 방법
        Optional<Recipe>optionalRecipe = Optional.ofNullable(recipeRepository.findBytitle(title));
        if(optionalRecipe.isEmpty()){
            return null;
        }
        Recipe recipe = optionalRecipe.get();
        recipe.setViewCount(recipe.getViewCount()+1);
        recipeRepository.save(recipe);
        return recipe;


    }
    public List<Recipe> selectall(){ // 전체 레시피 찾기
        List<Recipe> list = recipeRepository.findAll();
        return list;
    }

    // 최신 등록순 조회
    public List<Recipe> selectOrderByRegistDateDesc(){
        return recipeRepository.findAllByOrderByRegistDateDesc();
    }

    // 조회수 순서로 조회
    public List<Recipe> findAllByOrderByViewCountDesc(){
        return recipeRepository.findAllByOrderByViewCountDesc();
    }

    public List<Recipe> selectmemberid(Long memberid){
        List<Recipe> list = recipeRepository.findAllBymemberid(memberid);
        return list;
    }
}
