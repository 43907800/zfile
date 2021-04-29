package im.zhaojun.zfile.repository;

import im.zhaojun.zfile.model.entity.DriveConfig;
import im.zhaojun.zfile.model.entity.ErgodicDirConfig;
import im.zhaojun.zfile.model.entity.ShortLinkConfig;
import im.zhaojun.zfile.model.enums.StorageTypeEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author by LiuSy
 * @Date 2021/4/28 10:17
 * @Classname ErgodicDirRepository
 * @Description
 */
public interface ErgodicDirRepository extends JpaRepository<ErgodicDirConfig, Integer> , JpaSpecificationExecutor<ErgodicDirConfig> {

    /**
     * 根据状态查询配置列表
     * @param status 状态
     * @return
     */
    List<ErgodicDirConfig> findByStatus(Integer status);

    /**
     * 按驱动id和文件路径查找
     * @param driveId 驱动id
     * @param filePath 文件路径
     * @return
     */
    ErgodicDirConfig findByDriveIdAndFilePath(Integer driveId,String filePath);

    /**
     * 查询所有分类
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT category FROM `ERGODIC_CONFIG` WHERE category !='' GROUP BY category")
    List<String> findCategory();
}
