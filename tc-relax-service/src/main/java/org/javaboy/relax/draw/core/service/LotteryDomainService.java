package org.javaboy.relax.draw.core.service;



import org.javaboy.relax.common.TcResult;
import org.javaboy.relax.draw.core.entity.AwardInfo;
import org.javaboy.relax.draw.core.request.DrawRequest;

import java.util.List;

/**
 * @author:majin.wj
 */
public interface LotteryDomainService {

    TcResult<List<AwardInfo>> draw(DrawRequest request);

    TcResult<List<AwardInfo>> preview(DrawRequest request);
}
