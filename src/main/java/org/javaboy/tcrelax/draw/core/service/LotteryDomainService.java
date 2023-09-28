package org.javaboy.tcrelax.draw.core.service;

import org.javaboy.tcrelax.common.TcResult;
import org.javaboy.tcrelax.draw.core.entity.AwardInfo;
import org.javaboy.tcrelax.draw.core.request.DrawRequest;

import java.util.List;

/**
 * @author:majin.wj
 */
public interface LotteryDomainService {

    TcResult<List<AwardInfo>> draw(DrawRequest request);

    TcResult<List<AwardInfo>> preview(DrawRequest request);
}
