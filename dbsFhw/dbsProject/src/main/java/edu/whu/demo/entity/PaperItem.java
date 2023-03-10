package edu.whu.demo.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author 孔德昱
 * @date 2022/12/6 14:54 星期二
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description="论文实体")
public class PaperItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("论文ID")
    long paperId;

    @ApiModelProperty("论文题目")
    String paperTitle;

    @ApiModelProperty("发表时间")
    Date paperDate;

    @ApiModelProperty("论文作者")
    String paperAuthor;

    @ApiModelProperty("上传者Id")
    long paperUploaderId;

    @ApiModelProperty("上传日期")
    Date uploadDate;

//    @ManyToOne(optional = false)  // 多对一
//    @JoinColumn(name="paperUploaderId") // 外键
//    UserItem uploader;
}
