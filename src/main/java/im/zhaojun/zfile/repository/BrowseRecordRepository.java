package im.zhaojun.zfile.repository;

import im.zhaojun.zfile.model.entity.BrowseRecord;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Created by LiuSy
 * @Date 2020/11/25 14:38
 * @Classname BrowseRecordRepository
 * @Description
 */

@Repository
public interface BrowseRecordRepository extends JpaRepository<BrowseRecord, Integer> {

    /**
     * 查找目录下是否存在 记录
     * @param userName
     * @param filePath
     * @return
     */
    List<BrowseRecord> findByUserNameAndFilePath(String userName,String filePath);

    /**
     * 查找记录是否存在
     * @param userName
     * @param filePath
     * @return
     */
    BrowseRecord findByUserNameAndFilePathAndFileName(String userName,String filePath,String fileName);


    /**
     * 模糊查询记录
     * @param filePath
     * @param by
     * @return
     */
    List<BrowseRecord> findByUserNameAndFilePathLike(String userName, String filePath, Sort by);


    /**
     * 状态 模糊查询记录
     * @param userName
     * @param filePath
     * @param status
     * @return
     */
    List<BrowseRecord> findByUserNameAndFilePathLikeAndStatus(String userName, String filePath, Integer status, Sort by);
}
