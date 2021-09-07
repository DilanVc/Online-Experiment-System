package cc.mrbird.febs.exercise.service.impl;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.exercise.entity.ChapterQuestion;
import cc.mrbird.febs.exercise.mapper.ChapterQuestionMapper;
import cc.mrbird.febs.exercise.service.IChapterQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 *  Service实现
 *
 * @author liuxin
 * @date 2021-02-20 11:17:49
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ChapterQuestionServiceImpl extends ServiceImpl<ChapterQuestionMapper, ChapterQuestion> implements IChapterQuestionService {

    private final ChapterQuestionMapper chapterQuestionMapper;

    @Override
    public IPage<ChapterQuestion> findChapterQuestions(QueryRequest request, ChapterQuestion chapterQuestion) {
        LambdaQueryWrapper<ChapterQuestion> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<ChapterQuestion> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<ChapterQuestion> findChapterQuestions(ChapterQuestion chapterQuestion) {
	    LambdaQueryWrapper<ChapterQuestion> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createChapterQuestion(ChapterQuestion chapterQuestion) {
        this.save(chapterQuestion);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateChapterQuestion(ChapterQuestion chapterQuestion) {
        this.saveOrUpdate(chapterQuestion);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteChapterQuestion(ChapterQuestion chapterQuestion) {
        LambdaQueryWrapper<ChapterQuestion> wrapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wrapper);
	}
}
