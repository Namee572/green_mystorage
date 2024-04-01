package com.firstproject.firstproject.recipeboard.Contorller;

import com.firstproject.firstproject.recipeboard.Dto.RecipeDto;
import com.firstproject.firstproject.recipeboard.Entity.Recipe;
import com.firstproject.firstproject.recipeboard.Service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("recipe")
public class RecipeController {

    private final RecipeService recipeService;
    @PostMapping
    public Recipe insert(@RequestBody RecipeDto recipeDto){
        return recipeService.insert(recipeDto);
    }
    @GetMapping("/id/{id}") // id로 찾는 방법 // 타이틀로 할지 id로 할지 정하면 됨
    public Recipe select(@PathVariable Long id){
        return recipeService.select(id);
    }
    @GetMapping("/{title}")// title로 찾는 방법 // 값이 증가되어야 하는 경우
    public Recipe selectTitle(@PathVariable String title){
        return  recipeService.selecttitle(title);
    }
    @GetMapping() // 미리보기 (증가되면 안됨)
    public ResponseEntity<List<Recipe>> selectall(){
        List<Recipe>list =recipeService.selectall();
        if (list.size() == 0){
            return null;
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/recent")
    public List<Recipe> selectOrderByRegistDateDesc(){
        return recipeService.selectOrderByRegistDateDesc();
    }
    @GetMapping("/view")
    public List<Recipe> findAllByOrderByViewCountDesc(){
        return recipeService.findAllByOrderByViewCountDesc();
    }
    @GetMapping("/memberid/{memberid}")
    public List<Recipe> findAllBymemberid(@PathVariable Long memberid){
        return recipeService.selectmemberid(memberid);
    }
}
