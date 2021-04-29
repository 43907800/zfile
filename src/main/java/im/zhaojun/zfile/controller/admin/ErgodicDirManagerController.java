package im.zhaojun.zfile.controller.admin;

import cn.hutool.cron.CronUtil;
import cn.hutool.cron.Scheduler;
import im.zhaojun.zfile.exception.InvalidShortLinkException;
import im.zhaojun.zfile.model.entity.ErgodicDirConfig;
import im.zhaojun.zfile.model.support.ResultBean;
import im.zhaojun.zfile.service.ErgodicDirService;
import im.zhaojun.zfile.service.FileTcService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author by LiuSy
 * @Date 2021/4/28 10:37
 * @Classname ErgodicDirController
 * @Description 遍历文件夹配置
 */

@RestController
@RequestMapping("/admin")
public class ErgodicDirManagerController {


    @Resource
    ErgodicDirService ergodicDirService;

    @Resource
    FileTcService fileTcService;
    /**
     * 获取所有列表
     *
     * @return
     */
    @GetMapping("/ergodics")
    public ResultBean ergodicList(ErgodicDirConfig ergodicDirConfig,
                                  Integer page,
                                  Integer limit,
                                  @RequestParam(required = false, defaultValue = "createTime") String orderBy,
                                  @RequestParam(required = false, defaultValue = "desc") String orderDirection) {
        Page<ErgodicDirConfig> list = ergodicDirService.list(ergodicDirConfig,page,limit,orderBy,orderDirection);
        return ResultBean.success(list);
    }


    /**
     * 保存
     */
    @PostMapping("/ergodic")
    public ResultBean save(@RequestBody ErgodicDirConfig ergodicDirConfig){
        ergodicDirConfig.setStatus(0);
        ergodicDirService.save(ergodicDirConfig);
        return ResultBean.success();
    }

    /**
     * 删除
     *
     * @param   id
     *          驱动器 ID
     */
    @DeleteMapping("/ergodic/{id}")
    public ResultBean delete(@PathVariable Integer id) {
        ergodicDirService.delete(id);
        return ResultBean.success();
    }


    /**
     * 开始遍历指定目录配置
     * @param id
     * @return
     */
    @PostMapping("/ergodic/{id}")
    public void ergiducDir(@PathVariable Integer id) throws Exception {
        ErgodicDirConfig config = ergodicDirService.findById(id);
        String typechoVideoUrl = fileTcService.typechoVideoUrl(config);
        config.setTypechoVideoStr(typechoVideoUrl);
        config.setStatus(1);
        boolean save = ergodicDirService.save(config);
        if (!save) throw new InvalidShortLinkException();
    }

    @GetMapping("/ergodic/job")
    public ResultBean ergodicJob(String status) {
        String start ="start";
        if (start.equals(status)){
            CronUtil.start();
            start = "启动成功!";
        }else {
            Scheduler scheduler = CronUtil.getScheduler();
            CronUtil.stop();
            start = "已关闭定时任务!";
        }
        return ResultBean.success(start);
    }

}
