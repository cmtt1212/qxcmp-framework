package com.qxcmp.framework.statistics;

import com.qxcmp.framework.core.entity.AbstractEntityService;
import com.qxcmp.framework.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Aaric
 */
@Service
public class SearchKeyWordService extends AbstractEntityService<SearchKeyWord, Long, SearchKeyWordRepository> {

    public SearchKeyWordService(SearchKeyWordRepository repository) {
        super(repository);
    }

    /**
     * 增加一个搜索关键词记录
     *
     * @param title 关键词
     * @param user  搜索用户
     */
    public void add(String title, User user) {
        create(() -> {
            SearchKeyWord next = next();
            next.setDateCreated(new Date());
            next.setTitle(title);
            next.setUserId(user.getId());
            return next;
        });
    }

    /**
     * 查找指定日期内搜索关键字排名最高的结构
     *
     * @param date     日期
     * @param pageable 分页信息
     * @return 排名结果
     */
    public Page<SearchKeyWordPageResult> findByDateCreatedAfter(Date date, Pageable pageable) {
        return repository.findByDateCreatedAfter(date, pageable);
    }

    @Override
    protected <S extends SearchKeyWord> Long getEntityId(S entity) {
        return entity.getId();
    }
}
