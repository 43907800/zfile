package im.zhaojun.zfile.controller.home;

import im.zhaojun.zfile.context.DriveContext;
import im.zhaojun.zfile.model.dto.AudioInfoDTO;
import im.zhaojun.zfile.model.dto.FileItemDTO;
import im.zhaojun.zfile.model.support.ResultBean;
import im.zhaojun.zfile.service.base.AbstractBaseFileService;
import im.zhaojun.zfile.util.AudioUtil;
import im.zhaojun.zfile.util.HttpUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 文件解析 Controller
 * @author zhaojun
 */
@RestController
@RequestMapping("/common")
public class FileParseController {


    @Resource
    private DriveContext driveContext;


    /**
     * 获取文件内容, 仅限用于 txt, md, ini 等普通文本文件.
     *
     * @param   url
     *          文件路径
     *
     * @return  文件内容
     */
    @GetMapping("/content")
    public ResultBean getContent(String url) {
        return ResultBean.successData(HttpUtil.getTextContent(url));
    }


    /**
     * 获取音频文件信息
     *
     * @param   url
     *          文件 URL
     *
     * @return 音频信息, 标题封面等信息
     */
    @GetMapping("/audio-info")
    public ResultBean getAudioInfo(String url,String filePath,Integer driveId) throws Exception {
        AudioInfoDTO audioInfo = null;
        try {
            audioInfo = AudioUtil.getAudioInfo(url);
        } catch (Exception e) {
            AbstractBaseFileService fileService = driveContext.get(driveId);
            FileItemDTO fileItem = fileService.getFileItem(filePath);
            audioInfo = AudioInfoDTO.buildDefaultAudioInfoDTO(fileItem.getUrl());
        }
        return ResultBean.success(audioInfo);
    }

}