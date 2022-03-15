package com.hellonature.hellonature_back.controller.page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Admin_pageController {

    @GetMapping("/")
    public String index(){ return "index.html";}

    @GetMapping("/admin/")
    public String main(){ return "admin/Index.html";}

    @GetMapping("/admin/useredit")
    public String useredit() { return "admin/Admin_UserEdit.html";}

    @GetMapping("/admin/userlist")
    public String userlist(){
        return "admin/Admin_UserList.html";
    }

    @GetMapping("/admin/recipe")
    public String recipe(){
        return "admin/Admin_RecipeCreate.html";
    }

    @GetMapping("/admin/questionlist")
    public String questionlist(){
        return "admin/Admin_QuestionList.html";
    }

    @GetMapping("/admin/question")
    public String question(){
        return "admin/Admin_Question.html";
    }

    @GetMapping("/admin/producttext")
    public String producttext() { return "admin/Admin_ProductText.html";}

    @GetMapping("/admin/ProductReview")
    public String ProductReview(){
        return "admin/Admin_ProductReview.html";
    }

    @GetMapping("/admin/productquestionlist")
    public String productquestionlist(){
        return "admin/Admin_ProductQuestionList.html";
    }

    @GetMapping("/admin/prodcutquestion")
    public String prodcutquestion(){
        return "admin/Admin_ProductQuestion.html";
    }

    @GetMapping("/admin/productlist")
    public String productlist(){
        return "admin/Admin_ProductList.html";
    }

    @GetMapping("/admin/prodcutcreate")
    public String prodcutcreate(){return "admin/Admin_ProductCreate.html"; }

    @GetMapping("/admin/productcate")
    public String productcate(){
        return "admin/Admin_ProductCategory.html";
    }

    @GetMapping("/admin/productanswer")
    public String productanswer(){
        return "admin/Admin_ProductAnswer.html";
    }

    @GetMapping("/admin/PopupStore")
    public String PopupStore(){
        return "admin/Admin_PopupStore.html";
    }

    @GetMapping("/admin/PaymentHistory")
    public String PaymentHistory(){
        return "admin/Admin_PaymentHistory.html";
    }

    @GetMapping("/admin/OrderList")
    public String OrderList(){
        return "admin/Admin_OrderList.html";
    }

    @GetMapping("/admin/NoticeCreate")
    public String NoticeCreate(){
        return "admin/Admin_NoticeCreate.html";
    }

   @GetMapping("/admin/NoticeEdit")
    public String NoticeEdit() {
        return "admin/Admin_NoticeEdit.html";
    }

    @GetMapping("/admin/Notice")
    public String Notice(){
        return "admin/Admin_Notice.html";
    }

    @GetMapping("/admin/Main")
    public String Main(){return "admin/Admin_Main.html";}

    @GetMapping("/admin/MagazineList")
    public String MagazineList(){
        return "admin/Admin_MagazineList.html";
    }

    @GetMapping("/admin/MagazineCreate")
    public String MagazineCreate(){
        return "admin/Admin_MagazineCreate.html";
    }

    @GetMapping("/admin/MagazineCategory")
    public String MagazineCategory(){
        return "admin/Admin_MagazineCategory.html";
    }

    @GetMapping("/admin/Login")
    public String Login(){
        return "admin/Admin_Login.html";
    }

    @GetMapping("/admin/LifeZoneCategory")
    public String LifeZoneCategory(){
        return "admin/Admin_LifeZoneCategory.html";
    }

    @GetMapping("/admin/LifeZone")
    public String LifeZone(){
        return "admin/Admin_LifeZone.html";
    }

    @GetMapping("/admin/FAQ")
    public String FAQ(){
        return "admin/Admin_FAQ.html";
    }

    @GetMapping("/admin/FAQ_regist")
    public String FAQ_regist(){
        return "admin/Admin_FAQ_regist.html";
    }

    @GetMapping("/admin/FAQ_edit")
    public String FAQ_edit(){
        return "admin/Admin_FAQ_edit.html";
    }

    @GetMapping("/admin/EventList")
    public String EventList(){
        return "admin/Admin_EventList.html";
    }

    @GetMapping("/admin/EventEdit")
    public String EventEdit(){return "admin/Admin_EventEdit.html";}

    @GetMapping("/admin/EventCreate")
    public String EventCreate(){
        return "admin/Admin_EventCreate.html";
    }

    @GetMapping("/admin/CouponList")
    public String CouponList(){
        return "admin/Admin_CouponList.html";
    }

    @GetMapping("/admin/CouponCreate")
    public String CouponCreate(){
        return "admin/Admin_CouponCreate.html";
    }

    @GetMapping("/admin/BrandList")
    public String BrandList(){
        return "admin/Admin_BrandList.html";
    }

    @GetMapping("/admin/BrandEdit")
    public String BrandEdit(){
        return "admin/Admin_BrandEdit.html";
    }

    @GetMapping("/admin/BrandCreate")
    public String BrandCreate(){
        return "admin/Admin_BrandCreate.html";
    }

    @GetMapping("/admin/AddressList")
    public String AddressList(){
        return "admin/Admin_AddressList.html";
    }

    @GetMapping("/admin/AddressEdit")
    public String AddressEdit(){
        return "admin/Admin_AddressEdit.html";
    }

    @GetMapping("/admin/OrderDetail")
    public String OrderDetail(){
        return "admin/Admin_OrderDetail.html";
    }

    @GetMapping("/admin/productEdit")
    public String ProductEdit(){
        return "admin/Admin_ProductEdit.html";
    }

    @GetMapping("/admin/fresh")
    public String fresh(){
        return "admin/Admin_Fresh";
    }

}
