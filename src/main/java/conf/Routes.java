package conf;

import com.fit.controllers.AdminController;
import com.fit.controllers.BannerController;
import com.fit.controllers.GoodsController;
import com.fit.controllers.MailController;
import com.fit.controllers.UploadController;

import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;

/**
 * @AUTO 配置路由的，也就是我们访问路径的
 * @FILE Routes.java
 * @DATE 2017-9-28 下午8:35:53
 * @Author AIM
 */
public class Routes implements ApplicationRoutes {

    @Override
    public void init(Router router) {
        // 支持不同的请求方式，链式编程，后面helloWorldJson指向ApplicationController的具体实现的一个方法
        // ///////////////////////// 前台(front) /////////////////////////////////
        router.GET().route("/hello_world.json").with(GoodsController.class, "helloWorldJson");
        router.POST().route("/hello_world.json").with(GoodsController.class, "helloWorldJson");
        router.GET().route("/goodsIndex.*").with(GoodsController.class, "goodsIndex");
        router.GET().route("/getGoods.*").with(GoodsController.class, "goodsDetails");
        router.GET().route("/goodsList.*").with(GoodsController.class, "conditionQry");
        router.POST().route("/goodsList").with(GoodsController.class, "conditionQry");
        // ///////////////////////// 后台(rear-end) //////////////////////////////
        router.GET().route("/admin/").with(AdminController.class, "login");
        router.GET().route("/admin/index").with(AdminController.class, "admin");
        router.POST().route("/admin/main").with(AdminController.class, "main");
        router.GET().route("/admin/goodsManage.*").with(AdminController.class, "goodsManage");
        router.POST().route("/admin/goodsManage").with(AdminController.class, "goodsManage");
        router.GET().route("/admin/editGoods.*").with(AdminController.class, "editGoods");
        router.POST().route("/admin/saveGoods").with(AdminController.class, "saveGoods");
        router.GET().route("/admin/delGoods").with(AdminController.class, "delGoods");
        router.GET().route("/admin/logout").with(AdminController.class, "logout");
        router.POST().route("/uploadFile").with(UploadController.class, "uploadFormFinish");
        router.GET().route("/admin/bannerManage.*").with(BannerController.class, "bannerManage");
        router.POST().route("/admin/bannerManage").with(BannerController.class, "bannerManage");
        router.GET().route("/admin/editBanner.*").with(BannerController.class, "editBanner");
        router.POST().route("/admin/saveBanner").with(BannerController.class, "saveBanner");
        router.GET().route("/admin/sendMail").with(MailController.class, "sendMail");

        // /////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        // /////////////////////////////////////////////////////////////////////
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController.class, "serveWebJars");
        router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");

        // /////////////////////////////////////////////////////////////////////
        // Index / Catchall shows index page
        // /////////////////////////////////////////////////////////////////////
        router.GET().route("/").with(GoodsController.class, "goodsIndex");
    }
}