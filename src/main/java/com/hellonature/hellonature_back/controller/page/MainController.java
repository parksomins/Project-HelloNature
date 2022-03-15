package com.hellonature.hellonature_back.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class MainController {
    // index.html
    @GetMapping("/index")
    public String index(){
        return "/user/index.html";
    }
    // product.html
    @GetMapping("/product")
    public String product(){
        return "/user/product.html";
    }

    // life.html
    @GetMapping("/life")
    public String life(){
        return "/user/lifezone/life.html";
    }
    @GetMapping("/life/baby")
    public String life_baby(){
        return "/user/lifezone/life_baby.html";
    }
    @GetMapping("/life/camping")
    public String life_camping(){
        return "/user/lifezone/life_camping.html";
    }
    @GetMapping("/life/gamtan")
    public String life_gamtan(){
        return "/user/lifezone/life_gamtan.html";
    }
    @GetMapping("/life/kids")
    public String life_kids(){
        return "/user/lifezone/life_kids.html";
    }
    @GetMapping("/life/vegan")
    public String life_vegan(){
        return "/user/lifezone/life_vegan.html";
    }

    // goods_list.html
    @GetMapping("/goods_list")
    public String goods_list(){
        return "/user/goods_list.html";
    }

    // new_goods_list.html
    @GetMapping("/new_goods")
    public String new_goods(){
        return "/user/new_goods_list.html";
    }

    // recommend_goods_list.html
    @GetMapping("/recommend_goods")
    public String recommend_goods(){
        return "/user/recommend_goods_list.html";
    }

    // sale_goods_list.html
    @GetMapping("/sale_goods")
    public String sale_goods(){
        return "/user/sale_goods_list.html";
    }

    // innerbrand.html
    @GetMapping("/innerbrand")
    public String innerbrand(){
        return "/user/brand/innerbrand.html";
    }

    // brand_goods_list.html
    @GetMapping("/brand_goods_list")
    public String brand_goods_list(){
        return "/user/brand/brand_goods_list.html";
    }

    // funstaurant.html
    @GetMapping("/funstaurant")
    public String funstaurant() {return "/user/magazine/funstaurant.html";}
}
