package im.zhaojun.zfile.controller.home;

import im.zhaojun.zfile.model.entity.BrowseRecord;
import im.zhaojun.zfile.model.support.ResultBean;
import im.zhaojun.zfile.service.BrowseRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Created by LiuSy
 * @Date 2020/11/25 14:54
 * @Classname BrowseRecordController
 * @Description
 */

@RestController
@RequestMapping("/record")
public class BrowseRecordController {

    @Resource
    BrowseRecordService browseRecordService;



    @PostMapping("/save")
    public ResultBean saveBrowseRecord(BrowseRecord browseRecord) {
        browseRecordService.saveBrowseRecord(browseRecord);
        return ResultBean.success();
    }


    @GetMapping("/findUserRecord")
    public ResultBean findUserRecord(BrowseRecord browseRecord){
        List<BrowseRecord> browseRecordList = browseRecordService.findBrowseRecordByUser(browseRecord);
        return ResultBean.success(browseRecordList);
    }
}
