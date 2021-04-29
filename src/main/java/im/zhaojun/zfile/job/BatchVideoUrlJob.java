package im.zhaojun.zfile.job;

import cn.hutool.cron.CronUtil;
import cn.hutool.system.SystemUtil;
import im.zhaojun.zfile.model.entity.ErgodicDirConfig;
import im.zhaojun.zfile.service.ErgodicDirService;
import im.zhaojun.zfile.service.FileTcService;
import im.zhaojun.zfile.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author by LiuSy
 * @Date 2021/4/28 9:56
 * @Classname ErgodicDirJob
 * @Description
 */
@Slf4j
public class BatchVideoUrlJob {



    ErgodicDirService ergodicDirService = SpringUtils.getBean(ErgodicDirService.class);

    FileTcService fileTcService = SpringUtils.getBean(FileTcService.class);



    public synchronized void  execute() {
        log.info("定时任务已执行!");
        dirFileHandle();
    }

    /**
     * 递归目录下文件
     */
    public void dirFileHandle(){
        // 所有未获取过文件的
        List<ErgodicDirConfig> dirConfigs = ergodicDirService.findByStatus(0);
        for (ErgodicDirConfig dir : dirConfigs) {
            try {
                String typechoVideoUrl = fileTcService.typechoVideoUrl(dir);
                dir.setTypechoVideoStr(typechoVideoUrl);
                dir.setStatus(1);
                ergodicDirService.save(dir);
                log.info("驱动:{},已完成遍历目录:{}",dir.getDriveId(),dir.getFilePath());
            } catch (Exception e) {
                e.printStackTrace();
                log.error("遍历目录错误:{}",e.toString());
                dir.setTypechoVideoStr("遍历目录错误:" + e.toString());
                dir.setStatus(2);
                ergodicDirService.save(dir);
            }
        }
    }


    public static void main(String[] args) {
        CronUtil.start();

//        CronUtil.stop();
    }
}
