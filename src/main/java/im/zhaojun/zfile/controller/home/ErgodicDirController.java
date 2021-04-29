package im.zhaojun.zfile.controller.home;

import im.zhaojun.zfile.model.entity.ErgodicDirConfig;
import im.zhaojun.zfile.model.support.ResultBean;
import im.zhaojun.zfile.service.ErgodicDirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by LiuSy
 * @Date 2021/4/28 11:14
 * @Classname ErgodicDirController
 * @Description
 */

@RestController
@RequestMapping("/api")
public class ErgodicDirController {


    @Resource
    ErgodicDirService ergodicDirService;

    /**
     * 保存
     */
    @PostMapping("/ergodic")
    public ResultBean save(@RequestBody ErgodicDirConfig ergodicDirConfig){
        ergodicDirService.save(ergodicDirConfig);
        return ResultBean.success();
    }


    /**
     * 查询所有类型
     * @return
     */
    @GetMapping("/ergodic/category")
    public ResultBean findCategory(){
        List<String> category = ergodicDirService.findCategory();
        return ResultBean.success(category);
    }

}
