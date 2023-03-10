package edu.whu.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author dzf
 * @date 2022/12/8 15:55
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户实体")
public class UserItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("用户ID")
    long userId;

    @ApiModelProperty("用户姓名")
    String userName;

    @ApiModelProperty("用户身份")
//    @Column(columnDefinition = "VARCHAR(30) CHECK (user_identity IN ('学生', '老师'))")
    String userIdentity;
}
