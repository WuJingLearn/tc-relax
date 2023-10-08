package org.javaboy.relax.leadboard.memeber;

import lombok.Data;
import lombok.ToString;
import org.javaboy.relax.dal.dataobject.user.User;

/**
 * @author:majin.wj
 */
@Data
@ToString(callSuper = true)
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
