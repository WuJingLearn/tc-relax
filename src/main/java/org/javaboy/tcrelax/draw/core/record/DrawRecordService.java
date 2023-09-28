package org.javaboy.tcrelax.draw.core.record;

import com.alibaba.fastjson.TypeReference;
import org.javaboy.tcrelax.draw.dao.DrawRecordMapper;
import org.javaboy.tcrelax.draw.dataobject.DrawRecordDO;
import org.javaboy.tcrelax.draw.enums.CacheKeyEnum;
import org.javaboy.tcrelax.insfra.utils.CacheKey;
import org.javaboy.tcrelax.insfra.utils.DbCacheTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:majin.wj
 */
@Service
public class DrawRecordService {


    @Autowired
    private DrawRecordMapper drawRecordMapper;


    @Autowired
    private DbCacheTemplate dbCacheTemplate;

    /**
     * 查询抽奖记录
     *
     * @param userId
     * @param outId
     * @return
     */
    public List<DrawRecordDO> queryDrawRecord(Long userId, String outId) {
        return dbCacheTemplate.read(new DbCacheTemplate.ReadOperation<List<DrawRecordDO>>() {
            @Override
            public List<DrawRecordDO> readDb() {
                return drawRecordMapper.selectByOutId(userId, outId);
            }

            @Override
            public CacheKey cacheKey() {
                return CacheKey.of(CacheKeyEnum.DRAW_RECORD.getCacheKey(userId, outId), CacheKeyEnum.DRAW_RECORD.getTimeout());
            }

            @Override
            public TypeReference<List<DrawRecordDO>> typeReference() {
                return new TypeReference<List<DrawRecordDO>>() {
                };
            }
        });

    }


}
