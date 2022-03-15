package com.hellonature.hellonature_back.controller.page;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    // ----------------------------------------- Magazine -----------------------------------------
    // 매거진 메인페이지
    @GetMapping("/magazine")
    public String magazine(){
        return "/user/magazine/User_magazine.html";
    }
    // 레시피 메인페이지
    @GetMapping("/magazine/recipe")
    public String recipeMain() {
        return "/user/magazine/User_recipe_main.html";
    }
    // 레시피 상세페이지
    @GetMapping("/magazine/recipe_content")
    public String recipeContent() {
        return "/user/magazine/User_recipe_content.html";
    }
    // 탐험노트 메인페이지
    @GetMapping("/magazine/adventure")
    public String adventureMain() {
        return "/user/magazine/User_adventure_main.html";
    }
    // 탐험노트 상세페이지
    @GetMapping("/magazine/adventure_content")
    public String adventureContent() {
        return "/user/magazine/User_adventure_content.html";
    }
    // 라이프스타일 메인페이지
    @GetMapping("/magazine/lifestyle")
    public String lifestyleMain() {
        return "/user/magazine/User_lifestyle_main.html";
    }
    // 라이프스타일 상세페이지
    @GetMapping("/magazine/lifestyle_content")
    public String lifestyleContent() {
        return "/user/magazine/User_lifestyle_content.html";
    }
    // 키친가이드 메인페이지
    @GetMapping("/magazine/kitchen")
    public String kitchenMain() {
        return "/user/magazine/User_kitchen_main.html";
    }
    // 키친가이드 상세페이지
    @GetMapping("/magazine/kitchen_content")
    public String kitchenContent() {
        return "/user/magazine/User_kitchen_content.html";
    }
    // 더신선 페이지
    @GetMapping("/recommend/fresh")
    public String fresh() {
        return "/user/recommend/User_fresh.html";
    }
    // 첫구매혜택 페이지
    @GetMapping("/recommend/firstbuy")
    public String firstbuy() {
        return "/user/recommend/User_firstbuy.html";
    }
    // 팝업스토어 페이지
    @GetMapping("/recommend/store")
    public String store() {
        return "/user/recommend/User_store.html";
    }
    // 기획전/이벤트 페이지
    @GetMapping("/recommend/event")
    public String event() {
        return "/user/recommend/User_event.html";
    }
}
