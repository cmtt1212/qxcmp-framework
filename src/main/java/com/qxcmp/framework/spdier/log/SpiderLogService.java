package com.qxcmp.framework.spdier.log;

import com.qxcmp.framework.entity.AbstractEntityService;
import com.qxcmp.framework.spdier.Spider;
import com.qxcmp.framework.spdier.SpiderPageProcessor;
import com.qxcmp.framework.spdier.SpiderPipeline;
import com.qxcmp.framework.support.TimeDurationHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 蜘蛛日志服务
 *
 * @author aaric
 */
@Service
public class SpiderLogService extends AbstractEntityService<SpiderLog, Long, SpiderLogRepository> {

    private TimeDurationHelper timeDurationHelper;

    public SpiderLogService(SpiderLogRepository repository, TimeDurationHelper timeDurationHelper) {
        super(repository);
        this.timeDurationHelper = timeDurationHelper;
    }

    public void save(SpiderPageProcessor spiderPageProcessor) {
        create(() -> {
            SpiderLog spiderLog = next();
            spiderLog.setSpiderGroup(spiderPageProcessor.getClass().getAnnotation(Spider.class).group());
            spiderLog.setName(spiderPageProcessor.getClass().getAnnotation(Spider.class).name());
            spiderLog.setTargetPageCount(spiderPageProcessor.getTargetPageCount());
            spiderLog.setContentPageCount(spiderPageProcessor.getContentPageCount());
            spiderLog.setDateStart(new Date(spiderPageProcessor.getStartTime()));
            spiderLog.setDuration(timeDurationHelper.convert(System.currentTimeMillis() - spiderPageProcessor.getStartTime()));
            if (!spiderPageProcessor.getSpiderPipelines().isEmpty()) {
                SpiderPipeline spiderPipeline = (SpiderPipeline) spiderPageProcessor.getSpiderPipelines().get(0);
                spiderLog.setNewPageCount(spiderPipeline.getNewCount());
                spiderLog.setUpdatePageCount(spiderPipeline.getUpdateCount());
                spiderLog.setDropPageCount(spiderPipeline.getDropCount());
            }
            return spiderLog;
        });
    }

    @Override
    public Page<SpiderLog> findAll(Pageable pageable) {
        return repository.findAllByOrderByDateFinishDesc(pageable);
    }

    @Override
    protected <S extends SpiderLog> Long getEntityId(S entity) {
        return entity.getId();
    }
}
