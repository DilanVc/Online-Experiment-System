package cc.mrbird.febs.exercise.service;

import cc.mrbird.febs.exercise.entity.ChapterQuestion;

import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author liuxin
 * @date 2021-02-20 11:17:49
 */
public interface IChapterQuestionService extends IService<ChapterQuestion> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param chapterQuestion chapterQuestion
     * @return IPage<ChapterQuestion>
     */
    IPage<ChapterQuestion> findChapterQuestions(QueryRequest request, ChapterQuestion chapterQuestion);

    /**
     * 查询（所有）
     *
     * @param chapterQuestion chapterQuestion
     * @return List<ChapterQuestion>
     */
    List<ChapterQuestion> findChapterQuestions(ChapterQuestion chapterQuestion);

    /**
     * 新增
     *
     * @param chapterQuestion chapterQuestion
     */
    void createChapterQuestion(ChapterQuestion chapterQuestion);

    /**
     * 修改
     *
     * @param chapterQuestion chapterQuestion
     */
    void updateChapterQuestion(ChapterQuestion chapterQuestion);

    /**
     * 删除
     *
     * @param chapterQuestion chapterQuestion
     */
    void deleteChapterQuestion(ChapterQuestion chapterQuestion);
}
