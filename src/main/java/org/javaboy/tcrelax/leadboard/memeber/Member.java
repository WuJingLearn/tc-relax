package org.javaboy.tcrelax.leadboard.memeber;

import lombok.Data;
import org.javaboy.tcrelax.user.entity.User;

/**
 * @author:majin.wj
 */
@Data
public class Member extends User {

    /**
     * 分数
     */
    private Long score;
    /**
     * 排名
     */
    private Integer rank;



}
