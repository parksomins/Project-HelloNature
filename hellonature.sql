create table tb_magazines(
                             idx number(10) primary key,
                             show_flag char(1) not null,
                             img varchar2(100) not null,
                             title varchar2(30) not null,
                             des varchar2(30) not null,
                             mg_content clob not null,
                             mg_type char(1) not null,
                             mg_like number(4),
                             cook_time varchar2(10),
                             cook_kcal number(10),
                             cook_level char(1),
                             cook_ingredient varchar2(500),
                             ingre_list varchar2(500),
                             rel_list varchar2(500),
                             regdate date default sysdate
);

select * from tb_magazines;

create sequence magazines_seq
    increment by 1
    start with 1;
----------------------------------------------------------------------------------
create table tb_admins(
                          idx number(10) primary key,
                          ad_name varchar2(5) not null,
                          ad_id varchar2(10) unique not null,
                          ad_password varchar2(100) not null,
                          member_role number(2) not null,
                          regdate date default sysdate
);

create sequence admins_seq
    increment by 1
    start with 1;
----------------------------------------------------------------------------------
create table tb_members(
                           idx number(10) primary key,
                           mem_name varchar2(20) not null,
                           email varchar2(30) unique not null,
                           mem_password varchar2(100) not null,
                           hp varchar2(13) not null,
                           birth varchar2(20),
                           gender char(1),
                           mhp_idx number(10),
                           green_flag char(1) default '0' not null,
                           sms_flag char(1) default '0' not null,
                           email_flag char(1) default '0' not null,
                           hellocash number(10) default 0 not null,
                           mem_site number(1) not null,
                           member_role number(2) not null,
                           regdate date default sysdate
);

create sequence members_seq
    increment by 1
    start with 1;
----------------------------------------------------------------------------------
create table tb_members_addresses(
                                     idx number(10) primary key,
                                     mem_idx number(10) not null,
                                     addr_name varchar2(20) not null, -- 배송지명
                                     maddr_name varchar2(20) not null, -- 받는 사람
                                     hp varchar2(20) not null,
                                     zipcode varchar2(10) not null,
                                     addr1 varchar2(100) not null,
                                     addr2 varchar2(100) not null,
                                     request_type number(1) not null,
                                     request_memo1 varchar2(20),
                                     request_memo2 varchar2(30),
                                     dawn_flag number(1) default 0 not null,
                                     base_flag number(1) default 1 not null,
                                     gr_flag number(1) default 0 not null,
                                     regdate date default sysdate
);

create sequence members_addresses_seq
    increment by 1
    start with 1;
----------------------------------------------------------------------------------
create table tb_members_coupons(
                                   idx number(10) primary key,
                                   mem_idx number(10) not null,
                                   ct_idx number(10) not null,
                                   used_flag number(1) default 0 not null,
                                   date_start varchar2(20) not null,
                                   date_end varchar2(20) not null,
                                   regdate date default sysdate
);

create sequence members_coupons_seq
    increment by 1
    start with 1;
----------------------------------------------------------------------------------
create table tb_coupons_types(
                                 idx number(10) primary key,
                                 title varchar2(20) unique,
                                 ct_target number(1) default 1 not null,
                                 ct_auto number(1) not null,
                                 ct_count number(7) not null,
                                 discount number(7) not null,
                                 min_price number(7) not null,
                                 date_start varchar2(20) not null,
                                 date_end varchar2(20) not null,
                                 type1 number(1) not null,
                                 type2 varchar2(50),
                                 regdate date default sysdate
);

create sequence coupons_types_seq
    increment by 1
    start with 31546878;
----------------------------------------------------------------------------------
create table tb_members_baskets(
                                   idx number(10) primary key,
                                   mem_idx number(10),
                                   pro_idx number(10),
                                   pro_count number(5),
                                   regdate date default sysdate
);

create sequence members_baskets_seq
    increment by 1
    start with 1;
----------------------------------------------------------------------------------
create table tb_cards(
                         idx number(10) primary key,
                         mem_idx number(10),
                         cd_num varchar2(30) unique,
                         cd_date varchar2(10) not null,
                         cvc varchar2(4) not null,
                         cd_password varchar2(4) not null,
                         base_flag number(1) default 0 not null,
                         cd_name varchar2(20) not null,
                         bank number(2) not null,
                         fav_flag number(1) default 0 not null,
                         busi_flag number(1) default 0 not null,
                         birth varchar2(10) not null,
                         regdate date default sysdate
);

create sequence cards_seq
    increment by 1
    start with 1;
----------------------------------------------------------------------------------
create table tb_products(
                            idx number(10) primary key,
                            title varchar2(20) unique,
                            des varchar2(100) not null,
                            br_idx number(10) not null,
                            net_price number(10) not null,
                            sale_price number(10),
                            price number(10),
                            pro_state number(1) default 0 not null,
                            date_start varchar2(20) not null,
                            date_end varchar2(20) not null,
                            origin varchar2(10) not null,
                            size_weight varchar2(20) not null,
                            temp number(1) not null,
                            pro_count number(10) not null,
                            delivery number(1) not null,
                            packing number(1) not null,
                            img1 varchar2(100) not null,
                            img2 varchar2(100),
                            img3 varchar2(100),
                            img4 varchar2(100),
                            pro_des clob not null,
                            pro_like number(10) default 0 not null,
                            cate_idx number(10),
                            eve_cate_idx number(10),
                            pro_type varchar2(20) not null,
                            pro_name varchar2(20) not null,
                            food_type varchar2(20) not null,
                            producer varchar2(20) not null,
                            pro_location varchar2(20) not null,
                            date_built varchar2(20) not null,
                            date_valid varchar2(20) not null,
                            best_flag number(1) default 0 not null,
                            regdate date default sysdate
);

create sequence products_seq
    increment by 1
    start with 1;
----------------------------------------------------------------------------------
create table tb_products_questions(
                                      idx number(10) primary key,
                                      mem_idx number(10) not null,
                                      regdate date default sysdate,
                                      proq_content clob not null,
                                      pro_idx number(10) not null,
                                      ans_flag number(1) default 0 not null,
                                      img varchar2(100),
                                      ans_content clob,
                                      ans_date varchar2(20)
);

create sequence products_questions_seq
    increment by 1
    start with 1;
----------------------------------------------------------------------------------
create table tb_products_reviews(
                                    idx number(10) primary key,
                                    mem_idx number(10),
                                    regdate date default sysdate,
                                    rv_like number(1),
                                    rv_content clob,
                                    pro_idx number(10) not null,
                                    ans_flag number(1) default 0 not null,
                                    ans_content clob,
                                    mord_idx number(10),
                                    mordp_idx number(10),
                                    files varchar2(100),
                                    ans_date varchar2(20)
);

create sequence products_reviews_seq
    increment by 1
    start with 1;
----------------------------------------------------------------------------------
create table tb_categories(
                              idx number(10) primary key,
                              cate_name varchar2(20) unique,
                              root_idx number(10),
                              img varchar2(100),
                              life_flag number(1) default 0 not null,
                              regdate date default sysdate
);

create sequence categories_seq
    increment by 1
    start with 1;
----------------------------------------------------------------------------------
create table tb_members_hellopass(
                                     idx number(10) primary key,
                                     mem_idx number(10) not null,
                                     date_start varchar2(20) not null,
                                     date_end varchar2(20) not null,
                                     memhp_type number(1) not null,
                                     regdate date default sysdate
);

create sequence members_hellopass_seq
    increment by 1
    start with 1
    nocache;

--------------------------------------------------------------------------------------------
create table tb_hellopass_payments(
                                      idx number(10) primary key,
                                      hppaym_type number(1) not null,
                                      price number(5) not null,
                                      mem_idx number(10) not null,
                                      card_num varchar2(30),
                                      regdate date default sysdate

);

create sequence hellopass_payments_seq
    increment by 1
    start with 1
    nocache;

----------------------------------------------------------------------------------------------
create table tb_hellocash(
                             idx number(10) primary key,
                             hc_point number(10) not null,
                             date_used varchar2(20),
                             date_val varchar2(20),
                             hc_type number(1) not null,
                             mem_idx number(10) not null,
                             title varchar2(100) not null,
                             regdate date default sysdate
);

create sequence hellocash_seq
    increment by 1
    start with 1
    nocache;

----------------------------------------------------------------------------------------------
create table tb_faqs(
                        idx number(10) primary key,
                        faq_type number(1) not null,
                        subject varchar2(100) not null,
                        title varchar2(100) not null,
                        faq_content clob not null,
                        regdate date default sysdate
);

create sequence faqs_seq
    increment by 1
    start with 1
    nocache;
--------------------------------------------------------------------------------------------
create table tb_questions(
                             idx number(10) primary key,
                             mem_idx number(10) not null,
                             ans_flag number(1) not null,
                             ans_date varchar2(20),
                             que_content clob not null,
                             ans_content clob,
                             files varchar2(100),
                             email varchar2(20) not null,
                             hp varchar2(13) not null,
                             que_type number(2) not null,
                             regdate date default sysdate
);

create sequence questions_seq
    increment by 1
    start with 1
    nocache;
--------------------------------------------------------------------------------------------------
create table tb_notices(
                           idx number(10) primary key,
                           n_type number(1) not null,
                           title varchar2(100) not null,
                           n_content clob not null,
                           regdate date default sysdate
);

create sequence notices_seq
    increment by 1
    start with 1
    nocache;

--------------------------------------------------------------------------------------------------
create table tb_brands(
                          idx number(10) primary key,
                          br_name varchar2(20) not null,
                          des varchar2(1000) not null,
                          logo varchar2(100) not null,
                          br_state number(1) not null,
                          banner varchar2(200),
                          date_start varchar2(20) not null,
                          date_end varchar2(20) not null,
                          regdate date default sysdate
);

create sequence brands_seq
    increment by 1
    start with 1
    nocache;

select * from tb_brands;

insert into tb_brands values(1, '오리온', '오리오리온', '/brand/logo/1', 1, '/brand/banner/1', '2021-09-14', '2021-10-14', sysdate);
--------------------------------------------------------------------------------------------------
create table tb_events(
                          idx number(10) primary key,
                          type_flag number(1) not null,
                          ing_flag number(1) not null,
                          date_start varchar2(20) not null,
                          date_end varchar2(20) not null,
                          img varchar2(100) not null,
                          title varchar2(100) not null,
                          des varchar2(1000) not null,
                          eve_content clob not null,
                          regdate date default sysdate
);

create sequence events_seq
    increment by 1
    start with 1
    nocache;

--------------------------------------------------------------------------------------------------
create table tb_popupstores(
                               idx number(10) primary key,
                               img varchar2(100) not null,
                               title varchar2(100) not null,
                               des varchar2(1000) not null,
                               br_idx number(10) not null,
                               pop_content clob not null,
                               date_start varchar2(20) not null,
                               date_end varchar2(20) not null,
                               regdate date default sysdate
);

create sequence popupstores_seq
    increment by 1
    start with 1
    nocache;
select * from tb_popupstores;

--------------------------------------------------------------------------------------------------
create table tb_nonmembers_payments(
                                       idx number(10) primary key,
                                       nmord_idx number(10) not null,
                                       price number(8) not null,
                                       nmpaym_state number(1) not null,
                                       payment_type number(1) not null,
                                       nmpaym_num varchar2(20),
                                       regdate date default sysdate
);

create sequence nonmembers_payments_seq
    increment by 1
    start with 1
    nocache;
----------------------------------------------------------------------------------------------------
create table tb_nonmembers_orders_products(
                                              idx number(10) primary key,
                                              nmem_idx number(10) not null,
                                              pro_idx number(10) not null,
                                              pro_count number(10) not null,
                                              pro_price number(10) not null,
                                              regdate date default sysdate
);

create sequence nonmembers_orders_products_seq
    increment by 1
    start with 1
    nocache;
-------------------------------------------------------------------------------------------------------
create table tb_nonmembers_orders(
                                     idx number(10) primary key,
                                     nmord_state number(2) not null,
                                     nmord_name varchar2(20) not null,
                                     hp varchar2(13) not null,
                                     dawn_flag number(1) not null,
                                     alarm number(1) not null,
                                     zipcode varchar2(10) not null,
                                     address1 varchar2(30) not null,
                                     address2 varchar2(30) not null,
                                     request_type number(1) not null,
                                     request_memo1 varchar2(20),
                                     request_memo2 varchar2(20),
                                     regdate date default sysdate


);

create sequence nonmembers_orders_seq
    increment by 1
    start with 1
    nocache;
------------------------------------------------------------------------------------------------------
create table tb_members_payments(
                                    idx number(10) primary key,
                                    mord_idx number(10),
                                    price number(8) not null,
                                    mpaym_state number(1) not null,
                                    payment_type number(1) not null,
                                    mpaym_num varchar2(20),
                                    regdate date default sysdate,
                                    mem_idx number(10)
);
ALTER TABLE tb_members_payments MODIFY (mord_idx null);


create sequence members_payments_seq
    increment by 1
    start with 1
    nocache;
----------------------------------------------------------------------------------------------------
create table tb_members_orders_products(
                                           idx number(10) primary key,
                                           ord_idx number(10) not null,
                                           pro_idx number(10) not null,
                                           pro_count number(5) not null,
                                           pro_price number(10) not null,
                                           regdate date default sysdate
);

create sequence members_orders_products_seq
    increment by 1
    start with 1
    nocache;
----------------------------------------------------------------------------------------------------
create table tb_members_orders(
                                  idx number(10) primary key,
                                  mem_idx number(10) not null,
                                  mord_state number(2) not null,
                                  dawn_flag number(1) not null,
                                  alarm number(1) not null,
                                  zipcode varchar2(20) not null,
                                  address1 varchar2(50) not null,
                                  address2 varchar2(50) not null,
                                  request_type number(1) not null,
                                  request_memo1 varchar2(30),
                                  request_memo2 varchar2(30),
                                  cp_idx  number(10),
                                  regdate date default sysdate,
                                  green_flag number(1) not null,
                                  rec_name varchar2(20),
                                  rec_hp varchar2(20)
);

create sequence members_orders_seq
    increment by 1
    start with 1
    nocache;
----------------------------------------------------------------------------------------------------
create table tb_members_likes(
                                 idx number(10) primary key,
                                 mem_idx number(10) not null,
                                 pro_idx number(10),
                                 br_idx number(10),
                                 mg_idx number(10),
                                 ev_idx number(10),
                                 regdate date default sysdate
);

create sequence members_likes_seq
    increment by 1
    start with 1
    nocache;
----------------------------------------------------------------------------------------------------
create table tb_members_purchases(
                                     idx number(10) primary key,
                                     mem_idx number(10) not null,
                                     pro_idx number(10)not null,
                                     mpurc_count number(3) not null,
                                     regdate date default sysdate
);

create sequence members_purchases_seq
    increment by 1
    start with 1
    nocache;
----------------------------------------------------------------------------------------------------
create table persistent_logins (
    username varchar2(100) NOT NULL,
    series varchar2(100) PRIMARY KEY,
    token varchar2(100) NOT NULL,
    last_used date NOT NULL
);