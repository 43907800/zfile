package im.zhaojun.zfile.model.entity;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * @author by LiuSy
 * @Date 2021/4/27 16:35
 * @Classname ErgodicDirConfig
 * @Description 遍历文件夹配置
 */

@Entity(name = "ERGODIC_CONFIG")
@Data
public class ErgodicDirConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,columnDefinition = "INTEGER comment '驱动id'")
    private Integer driveId;

    @Column(nullable = false,columnDefinition = "varchar(255) comment '遍历的根目录'")
    private String filePath;

    @Column(columnDefinition = "varchar(255) comment '目录资源类型,如: Java.前端'")
    private String category;

    @Column(columnDefinition = "varchar(255) comment '资源名称'")
    private String title;

    @Lob
    @Column(columnDefinition = "longtext comment 'typecho需要的文件url'")
    private String typechoVideoStr;

    @Column(nullable = false,columnDefinition = "INTEGER DEFAULT 0 comment '是否已遍历生成'")
    @Builder.Default
    private Integer status = 0;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createTime;
}
