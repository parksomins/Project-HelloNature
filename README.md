# Hello Nature
<br/>
<div align="center">

  <img src="https://user-images.githubusercontent.com/86812090/151704982-54d03338-7378-4580-9f0b-2d8ffcca7046.png" width="300" height="200" />
  
</div>
<br/>
  
## 프로젝트 개요
 온라인 푸드테크 기업 헬로네이처의 웹사이트 제작

## 프로젝트 구성원
 김민지 <br/>
 김성용 <br/>
 박소민 <br/> 
 이수지 <br/>
 이예솔 <br/>
 정영훈 <br/>

## 프로젝트 목적 및 필요성

 헬로네이처 웹사이트는 앱에 최적화 되어있어 웹을 이용하는 소비자에게 불편을 줄 수 있음. 따라서 소바자가 웹 사이트를 이용하면서 겪을 불편함을 고려하여 앱에 최적된 사이트를 웹버전으로 개발 하였음 

## 프로젝트 진행과정

1. 스토리 보드 <br/>
2. 테이블정의서 <br/>
3. API문서 <br/>
4. 디자인 및 퍼블리싱 <br/>
5. 더미데이터를 활용한 프론트 서버 및 백엔드 서버 구현 <br/>
6. 오류 점검 및 최종 코드 수정 <br/>
7. 발표 <br/>
  
## 프로젝트 활용기술

`frontend`
	<img src="https://img.shields.io/badge/HTML5-E34F26.svg?style=for-the-badge&logo=HTML5&logoColor=white"/>
	<img src="https://img.shields.io/badge/CSS-1572B6.svg?style=for-the-badge&logo=CSS3&logoColor=white"/>
	<img src="https://img.shields.io/badge/JavaScript-F7DF1E.svg?style=for-the-badge&logo=JavaScript&logoColor=black"/>
	![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)
	![jQuery](https://img.shields.io/badge/jquery-%230769AD.svg?style=for-the-badge&logo=jquery&logoColor=white)
	<img src="https://img.shields.io/badge/axios-black.svg?style=for-the-badge&logo=axios&logoColor=white"/>

`backend`
	<img src="https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot"/>
	<img src="https://img.shields.io/badge/JPA-6DB33F.svg?style=for-the-badge&logo=spring-boot&logoColor=black"/>
	<img src="https://img.shields.io/badge/Spring Security-6DB33F.svg?style=for-the-badge&logo=spring-security&logoColor=white"/>
   
`database`
	<img src="https://img.shields.io/badge/Oracle-F80000.svg?style=for-the-badge&logo=Oracle&logoColor=white"/>

## 프로젝트 데이터 관계도
  
![dataModel](https://user-images.githubusercontent.com/86811852/152217508-4a009921-4a62-4fd8-ab5e-1d25deb37146.png)
  
## 프로젝트 담당 및 역할
  
`frontend` 
	마이페이지, 어드민 사이트 제작    
    
## 프로젝트 구현기능
  
`ADMIN`
1. 전체 상품 관리
   * 상품 카테고리 관리
      * 상품을 카테고리(1차 카테고리), 세부 카테고리(2차 카테고리)로 등록.    
   * 상품 관리
      * 상품을 등록
      * 상품조회 : 판매상태 / 카테고리 / 상품명을 검색하여 등록된 상품을 검색하고 조회할 수 있도록 함. 
   * 주문 관리
      * 주문관리 조회 : 구매일자 / 주문상태 / 주문번호를 검색하고 고객이 주문한 건을 검색하고 조회할 수 있도록 함. 
   * 상품 문의
      * 상품문의 조회 : 문의일자 / 답변여부/ 검색어(작성자 / 내용)를 검색하여 고객의 상품 문의건을 검색하고 조회할 수 있도록 함.  
   * 상품 후기 
      * 상품후기 조회 : 상품명을 검색하여 고객이 남긴 후기를 조회할 수 있도록 함.  
2. 매거진 관리
   * 카테고리 관리
      * 매거진 카테고리 등록 
   * 매거진 목록
      * 매거진 목록 조회 : 카테고리 / 매거진명 / 등록기간을 검색하여 조회할 수 있도록 함.
   * 매거진 등록
      * 매거진(탐험노트, 라이프스타일, 키친가이드) 등록 
   * 레시피 등록
      * 레시피 등록 
   
3. 헬로네이처 추천관리
   * 팝업스토어
      * 팝업스토어 등록 / 수정 
   * 기획전/이벤트
   * 브랜드관
      * 브랜드 등록 / 수정
      * 브랜드 조회 : 브랜드명 / 입접상태 / 입점기간을 검색하여 브랜드 목록을 조회할 수 있도록 함.
     
4. 고객센터
   * 공지사항 관리
      * 공지사항 등록
      * 공지사항 조회 : 제목 또는 분류기준 또는 기간을 검색 후 목록을 조회할 수 있도록 함.  
   * 결제 내역 관리
      * 결제내역 조회 : 결제일 또는 고객번호를 검색 후 목록을 조회할 수 있도록 함.
   * 1:1 문의 내역
      * 1:1 문의 조회 : 등록일 또는 답변상태 / 문의유형 또는 회원 이메일을 검색 후 목록을 조회할 수 있도록 함.   
   * FAQ 관리
      * FAQ 등록
      * FAQ 조회 : FAQ 제목을 검색 후 목록을 조회할 수 있도록 함.      
   * 회원 관리
      * 회원조회 : 가입일 또는 이메일 / 이름 / 연락처를 검색 후 회원목록을 조회할 수 있도록 함.
      * 회원정보 수정 
   * 쿠폰 관리
      * 쿠폰 등록
      * 쿠폰조회 : 쿠폰명 또는 기간을 검색 후 쿠폰목록을 조회할 수 있도록 함. 
   * 주소지 관리 
      * 회원 주소지 조회 : 회원 이메일을 검색하여 조회할 수 있도록 함.
      * 회원 주소지 수정 

`USER`
* 로그인 	
* 회원가입
  * 핸드폰 인증, 이메일 인증 
* 장바구니
  * 상품 구매 
* 상품조회
  * 상품상세 및 장바구니 담기 
* 마이페이지
  * 주문내역
    * 주문내역 및 주문상태 확인
  * 배송지 관리
    * 배송지 등록, 배송지 수정, 배송지 삭제
  * 구매 후기
    * 구매후기 등록, 답변 확인
  * 더그린배송
    * 더그린 배송 신청 및 해지
  * 상품 문의
    * 상품 문의, 답변 확인
  * 1:1 문의
    * 1:1 문의 등록 및 답변 확인  
  * 개인정보수정 
    * 이메일 찾기
    * 비밀번호 찾기
             
## 프로젝트 진행상세
  
#### 스토리보드 제작
* UI/UX 디자인부터 시작하여 요구사항에 맞는 스토리보드 제작 <br/><br/>
![stroyboard](https://user-images.githubusercontent.com/86812058/154220261-05cecfdc-c068-44d7-89f2-116347111f15.png)

<br/><br/>
	
#### 테이블정의서
* 짜여진 스토리 보드를 토대로 데이터베이스에 필요한 테이블과 컬럼을 정의 <br/><br/>
![테이블정의서](https://user-images.githubusercontent.com/86812058/154220264-c14b2535-413b-4ac9-beaa-197523f046f8.png)

<br/><br/>

#### API문서
* 필요한 API 정리 및 요청 URL 및 응답 예상 메시지 <br/><br/>
![api문서](https://user-images.githubusercontent.com/86812058/154220253-036bcc76-0adf-42a6-a613-016c55697984.png)
![api문서2](https://user-images.githubusercontent.com/86812058/154220258-8af8e5dc-4304-43f5-bbd8-8eeb061adaa4.png)

<br/><br/>

#### 디자인 및 퍼블리싱
* 스토리 보드에서 제작된 디자인을 토대로 퍼블리싱 진행 <br/><br/>
![uIux](https://user-images.githubusercontent.com/86812058/154220245-e83de3a4-4cd6-4f0d-b90e-177034bfc718.png)
    
<br/><br/>

#### 더미데이터를 활용한 프론트 서버 및 백엔드 서버 구현
* 깃을 활용한 협업<br/><br/>	
![git](https://user-images.githubusercontent.com/86812058/154220254-49844a6c-0f7d-4f90-8eaa-d874fd934b7b.png)<br/><br/>   
* 포스트맨을 활용한 API 요청과 응답 확인<br/><br/>
![postman](https://user-images.githubusercontent.com/86812058/154220266-128c2948-e908-40d9-9474-8ffaa462de8b.png)

	
* 구현 중 문제점
	* 검색
	* 선택 삭제
	* 이미지 업로드 rebuild 문제

	* 레시피
	* 관련상품
	* 장바구니

* 해결방안
	* jsql
	* array		
	* create
	* js
	* session storege        
	     
#### 오류 점검 및 최종 코드 수정 <br/>


#### 발표 <br/>
* 결과물을 토대로 오프라인 발표를 진행<br/><br/>	
<img src = https://user-images.githubusercontent.com/86812058/154227687-7d096157-1c6a-413f-a44d-60c17f291123.gif width="1920" height="720">

## 프로젝트 시연영상

#### 회원가입/ 로그인
![회원가입-Chrome-2022-02-17-14-15-09](https://user-images.githubusercontent.com/86812172/154411918-db3136ce-6e7b-4b3e-9ed6-4801e7e07e1b.gif)

#### 휴대폰 인증번호 확인
![회원가입-Chrome-2022-02-17-14-21-56](https://user-images.githubusercontent.com/86812172/154411981-dbb6ebff-5b50-45ee-b5c4-19fb30e5ed7d.gif)
![헬로네이처 인증](https://user-images.githubusercontent.com/86812172/154412083-33e1d92b-9f1c-46fd-8d41-f5e1b758e864.jpg)

#### 상품 등록
![admin-story-board-main-Chrome-2022-02-16-18-06-35](https://user-images.githubusercontent.com/86812172/154232337-9c749be0-0994-459d-8502-d15622db2f23.gif)

#### 카테고리 등록
![ezgif com-gif-maker (2)](https://user-images.githubusercontent.com/86812090/154260722-88e17a82-c79f-4d1c-b8a1-a98b4b0e56a8.gif)

#### 장바구니 담기
![아이템-리스트-Chrome-2022-02-17-21-14-13](https://user-images.githubusercontent.com/86812172/154617112-43c48cdb-423e-48d0-a4bd-333a53027d30.gif)

#### 마이페이지 - 더그린배송 신청
![더그린배송-Chrome-2022-02-17-19-08-07](https://user-images.githubusercontent.com/86812172/154454217-c34971ab-08c0-4c9b-af78-27a4762b6f36.gif)

## 프로젝트를 마치며...

느낀점
