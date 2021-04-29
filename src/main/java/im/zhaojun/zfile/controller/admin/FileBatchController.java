package im.zhaojun.zfile.controller.admin;


import im.zhaojun.zfile.model.support.ResultBean;
import im.zhaojun.zfile.service.FileTcService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author by LiuSy
 * @Date 2021/4/27 14:46
 * @Classname FileBatchController
 * @Description
 */

@RestController
@RequestMapping("/admin/file")
public class FileBatchController {



    @Resource
    FileTcService fileTcService;

    @GetMapping("/typechoVideo")
    public ResultBean pathVideoFileUrl(@RequestParam(defaultValue = "2") Integer driveId,
                                @RequestParam(defaultValue = "/") String path) throws Exception {

        String videoStr = fileTcService.typechoVideoUrl(driveId, path);

        return ResultBean.success("操作成功",videoStr);
    }




}
