package im.zhaojun.zfile.model.entity;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * @author by LiuSy
 * @Created by LiuSy
 * @Date 2020/11/25 14:14
 * @Classname BrowseRecord
 * @Description
 */

@Entity(name = "BROWSE_RECORD")
@Data
public class BrowseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userName;

    private String filePath;

    private String fileName;

    private Integer driveId;

    @Builder.Default
    private Integer status = 1;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createTime;
}
