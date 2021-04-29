package im.zhaojun.zfile.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import im.zhaojun.zfile.context.DriveContext;
import im.zhaojun.zfile.model.constant.ZFileConstant;
import im.zhaojun.zfile.model.dto.FileItemDTO;
import im.zhaojun.zfile.model.dto.SystemConfigDTO;
import im.zhaojun.zfile.model.entity.ErgodicDirConfig;
import im.zhaojun.zfile.model.entity.SystemConfig;
import im.zhaojun.zfile.model.enums.FileTypeEnum;
import im.zhaojun.zfile.service.base.AbstractBaseFileService;
import im.zhaojun.zfile.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author by LiuSy
 * @Date 2021/4/27 15:06
 * @Classname FileService
 * @Description  TypechoBlog需要的文件处理
 */


@Slf4j
@Service
public class FileTcService {


    public static final String[] videoType = new String[]{"mp4", "webm", "m3u8", "rmvb", "avi", "swf", "3gp", "mkv", "flv"};


    @Resource
    private DriveContext driveContext;


    @Resource
    private SystemConfigService systemConfigService;





    /**
     *  获取typecho视频需要的 文件url
     * @param driveId 驱动id
     * @param path 路径
     * @return
     * @throws Exception
     */
    public String typechoVideoUrl(Integer driveId, String path) throws Exception {
        AbstractBaseFileService fileService = driveContext.get(driveId);
        List<FileItemDTO> fileItemDTOS = listOfDir(fileService, path, 0, 0);
        return  getVideoFileUrlToStr(fileItemDTOS,driveId);
    }

    /**
     * 获取typecho视频需要的 文件url
     * @param ergodicDirConfig 遍历目录配置
     * @return
     * @throws Exception
     */
    public String typechoVideoUrl(ErgodicDirConfig ergodicDirConfig) throws Exception {

        if ( ergodicDirConfig == null ) return null;

        return typechoVideoUrl(ergodicDirConfig.getDriveId(), ergodicDirConfig.getFilePath());
    }

    /**
     * 递归遍历目录下文件
     * @param fileService 挂载网盘驱动
     * @param path 开始路径
     * @param start 默认 0
     * @param depth 深度 0为不限制深度遍历全部.  2(depth)-0(start) =2(遍历两级目录)
     * @return
     * @throws Exception
     */
    public List<FileItemDTO> listOfDir(AbstractBaseFileService fileService, String path, int start, int depth) throws Exception {
        List<FileItemDTO> resultList = new ArrayList<>();
        // 0为不限制深度
        if ( depth != 0 && start >= depth) return resultList;

        // 获取path下 所有文件和文件夹
        List<FileItemDTO> fileItemList =
                fileService.fileList(StringUtils.removeDuplicateSeparator(ZFileConstant.PATH_SEPARATOR + path + ZFileConstant.PATH_SEPARATOR));

        for (FileItemDTO itemDTO : fileItemList) {
            // 跳过密码文件
            if (ZFileConstant.PASSWORD_FILE_NAME.equals(itemDTO.getName())) continue;
            if (itemDTO.getType() == FileTypeEnum.FILE){
                resultList.add(itemDTO);
            }else if (itemDTO.getType() == FileTypeEnum.FOLDER){
                resultList.addAll(listOfDir(fileService,(path+"/"+itemDTO.getName()),(start+1),depth));
            }
        }
        return resultList;
    }


    /**
     * 视频文件url 拼接成typecho博客所需要的字符串
     * @param fileItemList
     * @return
     */
    public String getVideoFileUrlToStr(List<FileItemDTO> fileItemList,Integer driveId){
        StringBuffer sb = new StringBuffer();
        String row ="\r\n";
        String column ="$";

        SystemConfigDTO systemConfig = systemConfigService.getSystemConfig();
        String domain = systemConfig.getDomain();
        String directlink ="/directlink/" + driveId +"/";

        for (FileItemDTO itemDTO : fileItemList) {
            // 获取文件扩展名
            String suffix =itemDTO.getName().substring(itemDTO.getName().lastIndexOf(".")+1);
                // 视频类型文件
            if (ArrayUtil.contains(videoType,suffix)) {
                // 文件直链路径
                String filePath = StringUtils.removeDuplicateSeparator( domain + directlink +itemDTO.getPath()+itemDTO.getName());
                String rowStr = itemDTO.getName() + column + filePath + row;
                sb.append(rowStr);
            }
        }
        return StrUtil.removeSuffix(sb.toString(),row);
    }

}
