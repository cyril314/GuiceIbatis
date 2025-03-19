$(function(){
    //搜索按钮
    $("#search").click(function(){
        $("#keyword").toggleClass("hide");
        $(".navd").toggle();
        $(".full").toggle();
    });
    $(".full").click(function(){
        $("#keyword").toggleClass("hide");
        $(".navd").show();
        $(".full").hide();
    });
    //根据电影评分在前台显示分数图
    var score = Math.ceil($("#score").html());
    var star = $("#star");
    switch (score) {
        case (0):
            star.addClass("s0");
            break;
        case (1):
            star.addClass("s0-1");
            break;
        case (2):
            star.addClass("s1-2");
            break;
        case (3):
            star.addClass("s2-3");
            break;
        case (4):
            star.addClass("s3-4");
            break;
        case (5):
            star.addClass("s4-5");
            break;
        case (6):
            star.addClass("s5-6");
            break;
        case (7):
            star.addClass("s6-7");
            break;
        case (8):
            star.addClass("s7-8");
            break;
        case (9):
            star.addClass("s8-9");
            break;
        case (10):
            star.addClass("s9-10");
            break;

    }
    //简介与详情切换
    $(".change").click(function(){
        if($(this).hasClass("down")){
            $(this).removeClass("down").addClass("up");
            $(".all").show();
            $(".part").hide();
        }else{
            $(this).removeClass("up").addClass("down");
            $(".part").show();
            $(".all").hide();
        }
    });
    //电影图集切换
    var i = 0 ;
    //遍历图片数量
    $(".pics img").each(function () {
        i++;
    });
    var width1 = i * 100 + "%";
    var width2 = 100/i + "%";
    //根据图片数量相关宽度设置
    $(".pics").css("width",width1);
    $(".pics img").css("width",width2);
    var n = 0;
    //左右切换js
    $(".left").click(function(){
        n+=100;
        if(n>0){
            n=-(i-1)*100;
        }
        $(".pics").animate({left:"n+'%'"}).css("left",n+"%");
    });
    $(".right").click(function(){
        n-=100;
        if(n<-(i-1)*100){
            n=0;
        }
        $(".pics").animate({left:"n+'%'"}).css("left",n+"%");
    });
    //电影标签位置设置
    var l = parseInt($(".recommend dl").css("width"))/2-20;
    $(".score").css("left",l);
    $(".soon").css("right",l);
    //电影筛选选中状态
    $(".select .swiper-slide").click(function () {
        $(this).addClass("selected").siblings().removeClass("selected");
    });
    //筛选详细
    var swiper1 = new Swiper('.demo1 .swiper-container', {
        pagination: '.demo1 .swiper-pagination',
        paginationClickable: true,
        loop:true
    });
    var swiper2 = new Swiper('.demo2 .swiper-container', {
        pagination: '.demo2 .swiper-pagination',
        slidesPerView: 6,
        paginationClickable: true,
        spaceBetween: 0,
        freeMode: true
    });
    var swiper3 = new Swiper('.demo3 .swiper-container', {
        pagination: '.demo3 .swiper-pagination',
        slidesPerView: 6,
        paginationClickable: true,
        spaceBetween: 0,
        freeMode: true
    });
    var swiper4 = new Swiper('.demo4 .swiper-container', {
        pagination: '.demo4 .swiper-pagination',
        slidesPerView: 6,
        paginationClickable: true,
        spaceBetween: 0,
        freeMode: true
    });
    var swiper5 = new Swiper('.demo5 .swiper-container', {
        pagination: '.demo5 .swiper-pagination',
        slidesPerView: 6,
        paginationClickable: true,
        spaceBetween: 10,
        freeMode: true
    });
})