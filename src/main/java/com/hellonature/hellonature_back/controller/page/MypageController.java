package com.hellonature.hellonature_back.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class MypageController {
    // ------------------------------------------ 레이아웃 ------------------------------------------

        // 마이페이지 섹션
        @GetMapping("/section")
        public String mypageSection(){
            return "./user/mypage/mypage_layout/section.html";
        }
        // 마이페이지 사이드바1
        @GetMapping("/sidebar1")
        public String mypageSidebar1(){
            return "./user/mypage/mypage_layout/sidebar1.html";
        }
        // 마이페이지 사이드바2
        @GetMapping("/sidebar2")
        public String mypageSidebar2(){
            return "./user/mypage/mypage_layout/sidebar2.html";
        }
        // 마이페이지 사이드바3
        @GetMapping("/sidebar3")
        public String mypageSidebar3(){
            return "./user/mypage/mypage_layout/sidebar3.html";
        }
        // 마이페이지 오더리스트1
        @GetMapping("/orderlist1")
        public String mypageOrderlist1(){
            return "./user/mypage/mypage_layout/orderlist1.html";
        }
        // 마이페이지 오더리스트2
        @GetMapping("/orderlist2")
        public String mypageOrderlist2(){
            return "./user/mypage/mypage_layout/orderlist2.html";
        }
        // 마이페이지 오더리스트3
        @GetMapping("/orderlist3")
        public String mypageOrderlist3(){
            return "./user/mypage/mypage_layout/orderlist3.html";
        }
        // 마이페이지 오더리스트4
        @GetMapping("/orderlist4")
        public String mypageOrderlist4(){
            return "./user/mypage/mypage_layout/orderlist4.html";
        }
        // 마이페이지 FAQ1
        @GetMapping("/faq1")
        public String mypageFaq1(){
            return "./user/mypage/mypage_layout/faq1.html";
        }
        // 마이페이지 FAQ2
        @GetMapping("/faq2")
        public String mypageFaq2(){
            return "./user/mypage/mypage_layout/faq2.html";
        }
        // 마이페이지 FAQ3
        @GetMapping("/faq3")
        public String mypageFaq3(){
            return "./user/mypage/mypage_layout/faq3.html";
        }
        // 마이페이지 FAQ4
        @GetMapping("/faq4")
        public String mypageFaq4(){
            return "./user/mypage/mypage_layout/faq4.html";
        }
        // 마이페이지 FAQ5
        @GetMapping("/faq5")
        public String mypageFaq5(){
            return "./user/mypage/mypage_layout/faq5.html";
        }
        // 마이페이지 FAQ6
        @GetMapping("/faq6")
        public String mypageFaq6(){
            return "./user/mypage/mypage_layout/faq6.html";
        }
        // 마이페이지 FAQ7-1
        @GetMapping("/faq7_1")
        public String mypageFaq7_1(){
            return "./user/mypage/mypage_layout/faq7-1.html";
        }
        // 마이페이지 FAQ7-2
        @GetMapping("/faq7_2")
        public String mypageFaq7_2(){
            return "./user/mypage/mypage_layout/faq7-2.html";
        }
        // 마이페이지 FAQ8
        @GetMapping("/faq8")
        public String mypageFaq8(){
            return "./user/mypage/mypage_layout/faq8.html";
        }
        // 작성가능한 후기
        @GetMapping("/available_review")
        public String mypageAvailableReview(){
            return "./user/mypage/mypage_layout/available_review.html";
        }
        // 사용가능한 쿠폰
        @GetMapping("/available_coupon")
        public String mypageAvailableCoupon(){
            return "./user/mypage/mypage_layout/available_coupon.html";
        }
        // 사용한 쿠폰
        @GetMapping("/used_coupon")
        public String mypageUsedCoupon(){
            return "./user/mypage/mypage_layout/used_coupon.html";
        }


        // ------------------------------------------ 필터 ------------------------------------------
        // 주문배송 취소교환반품 최근1개월 필터
        @GetMapping("/filter1")
        public String mypageFilter1(){
            return "./user/mypage/mypage_layout/orderlist_filter1.html";
        }
        // 주문배송 취소교환반품 3개월 필터
        @GetMapping("/filter2")
        public String mypageFilter2(){
            return "./user/mypage/mypage_layout/orderlist_filter2.html";
        }
        // 주문배송 취소교환반품 6개월 필터
        @GetMapping("/filter3")
        public String mypageFilter3(){
            return "./user/mypage/mypage_layout/orderlist_filter3.html";
        }
        // 주문배송 취소교환반품 기간조회 필터
        @GetMapping("/filter4")
        public String mypageFilter4(){
            return "./user/mypage/mypage_layout/orderlist_filter4.html";
        }
        // 자주 구매 최근1개월 필터
        @GetMapping("/favorite_filter1")
        public String mypageFavoriteFilter1(){
            return "./user/mypage/mypage_layout/favorite_filter1.html";
        }
        // 자주 구매 3개월 필터
        @GetMapping("/favorite_filter2")
        public String mypageFavoriteFilter2(){
            return "./user/mypage/mypage_layout/favorite_filter2.html";
        }
        // 자주 구매 6개월 필터
        @GetMapping("/favorite_filter3")
        public String mypageFavoriteFilter3(){
            return "./user/mypage/mypage_layout/favorite_filter3.html";
        }
        // 자주 구매 기간조회 필터
        @GetMapping("/favorite_filter4")
        public String mypageFavoriteFilter4(){
            return "./user/mypage/mypage_layout/favorite_filter4.html";
        }
        // 좋아요 상품 필터
        @GetMapping("/like_filter1")
        public String mypageLikeFilter1(){
            return "./user/mypage/mypage_layout/like_filter1.html";
        }
        // 좋아요 매거진 필터
        @GetMapping("/like_filter2")
        public String mypageLikeFilter2(){
            return "./user/mypage/mypage_layout/like_filter2.html";
        }


    // ------------------------------------------ 페이지 ------------------------------------------
    // sidebar1
    // 주문내역(홈)
    @GetMapping("/mypage_orderlist")
    public String mypageOrderlist(){
        return "user/mypage/!!!mypage_orderlist.html";
    }
    // 배송지 관리
    @GetMapping("/mypage_shipping")
    public String mypageShipping(){
        return "user/mypage/!!!mypage_shipping.html";
    }
    // 배송지 수정
    @GetMapping("/mypage_editShipping")
    public String mypageEditShipping(){
        return "user/mypage/!!!mypage_edit_shipping.html";
    }
    // 배송지 추가
    @GetMapping("/mypage_addShipping")
    public String mypageAddShipping(){
        return "user/mypage/!!!mypage_add_shipping.html";
    }
    // 구매후기
    @GetMapping("/mypage_review")
    public String mypageReview(){
        return "user/mypage/!!!mypage_review.html";
    }
    // 구매후기 작성
    @GetMapping("/mypage_enrollReview")
    public String mypageEnrollReview(){
        return "user/mypage/!!!mypage_enroll_review.html";
    }
    // 구매후기 상세
    @GetMapping("/mypage_detaillReview")
    public String mypageDetailReview(){
        return "user/mypage/!!!mypage_detail_review.html";
    }
    // 더그린배송
    @GetMapping("/mypage_thegreen")
    public String mypageThegreen(){
        return "user/mypage/!!!mypage_thegreen.html";
    }
    // 더그린배송 선택
    @GetMapping("/mypage_chooseThegreen")
    public String mypageChooseThegreen(){
        return "user/mypage/!!!mypage_choose_thegreen.html";
    }
    // 더그린배송 결제
    @GetMapping("/mypage_paythegreen")
    public String mypagePayThegreen(){
        return "user/mypage/!!!mypage_pay_thegreen.html";
    }
    // 상품문의
    @GetMapping("/mypage_productinq")
    public String mypageProductinq(){
        return "user/mypage/!!!mypage_productinq.html";
    }
    // 상품문의 등록
    @GetMapping("/mypage_enrollProductinq")
    public String mypageEnrollProductinq(){
        return "user/mypage/!!!mypage_enroll_productinq.html";
    }
    // 1:1문의
    @GetMapping("/mypage_mantoman")
    public String mypageMantoman(){
        return "user/mypage/!!!mypage_mantoman.html";
    }
    // 1:1문의 등록
    @GetMapping("/mypage_enrollMantoman")
    public String mypageEnrollMantoman(){
        return "user/mypage/!!!mypage_enroll_mantoman.html";
    }
    // 개인정보수정
    @GetMapping("/mypage_edit")
    public String mypageEdit(){
        return "user/mypage/!!!mypage_edit.html";
    }
    // 개인정보수정 - 내정보관리
    @GetMapping("/mypage_editMyinfo")
    public String mypageEditMyinfo(){
        return "user/mypage/!!!mypage_edit_myinfo.html";
    }

    // ----------------------------------------- sidebar2 -----------------------------------------
    // 고객센터
    @GetMapping("/mypage_center")
    public String mypageCenter(){
        return "user/mypage/!!!mypage_center.html";
    }
    // FAQ
    @GetMapping("/mypage_faq")
    public String mypageFaq(){
        return "user/mypage/!!!mypage_faq.html";
    }
    // NEWS(공지사항)
    @GetMapping("/mypage_news")
    public String mypageNews(){
        return "user/mypage/!!!mypage_news.html";
    }
    // NEWS(공지사항 디테일)
    @GetMapping("/mypage_newsDetail")
    public String mypageNewsDetail(){
        return "user/mypage/!!!mypage_newsdetail.html";
    }


    // ----------------------------------------- sidebar3 -----------------------------------------
    // 헬로캐쉬
    @GetMapping("/mypage_hellocash")
    public String mypageHellocash(){
        return "user/mypage/!!!mypage_hellocash.html";
    }
    // 쿠폰
    @GetMapping("/mypage_coupon")
    public String mypageCoupon(){
        return "user/mypage/!!!mypage_coupon.html";
    }
    // 헬로페이
    @GetMapping("/mypage_hellopay")
    public String mypageHellopay(){
        return "user/mypage/!!!mypage_hellopay.html";
    }
    // 헬로페이 등록(카드등록)
    @GetMapping("/mypage_enrollHellopay")
    public String mypageEnrollHellopay(){
        return "user/mypage/!!!mypage_enroll_hellopay.html";
    }
    // 헬로패스
    @GetMapping("/mypage_hellopass")
    public String mypageHellopass(){
        return "user/mypage/!!!mypage_hellopass.html";
    }
    // 헬로패스 해지
    @GetMapping("/mypage_cancelHellopass")
    public String mypageCancelHellopass(){
        return "user/mypage/!!!mypage_cancel_hellopass.html";
    }
    // 헬로패스 결제
    @GetMapping("/mypage_payhellopass")
    public String mypagePayHellopass(){
        return "user/mypage/!!!mypage_pay_hellopass.html";
    }

    // 회원 장바구니
    @GetMapping("/mypage_usercart")
    public String mypageUserCart(){
        return "user/mypage/UserCart.html";
    }
    // 비회원 주문서
    @GetMapping("/mypage_nonuserOrdersheet")
    public String mypageNonUserOrderSheet(){
        return "user/mypage/NonUserOrderSheet.html";
    }

    //바로구매 주문서
    @GetMapping("/mypage_userDirectOrdersheet")
    public String mypageuserDirectOrdersheet(){return "user/mypage/User_Direct_OrderSheet.html";}

    // 회원 주문서
    @GetMapping("/mypage_userOrdersheet")
    public String mypageUserOrderSheet(){
        return "user/mypage/UserOrderSheet.html";
    }
    // 회원등록(가입)
    @GetMapping("/mypage_userRegist")
    public String mypageUserRegist(){
        return "user/mypage/UserRegist.html";
    }
    // 이메일 찾기
    @GetMapping("/mypage_findEmail")
    public String mypageUserFindEmail(){
        return "user/mypage/UserFindEmail.html";
    }
    // 비밀번호 찾기
    @GetMapping("/mypage_findPassword")
    public String mypageUserFindPassword(){
        return "user/mypage/UserFindPassword.html";
    }
    // 회원 로그인
    @RequestMapping(value = "/mypage_userLogin", method = {RequestMethod.GET, RequestMethod.POST})
    public String mypageUserLoginGetGet(){
        return "user/mypage/UserLogin.html";
    }

    // 로그인 실패 시 띄울 페이지
    @RequestMapping(value = "/mypage_userLogin_re", method = {RequestMethod.GET, RequestMethod.POST})
    public String mypageUserLoginFail(){
        return "user/mypage/UserLogin_fail.html";
    }

    // 배송팝업
    @GetMapping("/shipping_popup")
    public String mypageUserShippingPopup(){
        return "user/mypage/UserShippingPopup.html";
    }
}
