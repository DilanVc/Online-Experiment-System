package cc.mrbird.febs.exercise.service;

import cc.mrbird.febs.exercise.entity.QuestionFile;

import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author liuxin
 * @date 2021-02-21 22:54:31
 */
public interface IQuestionFileService extends IService<QuestionFile> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param questionFile questionFile
     * @return IPage<QuestionFile>
     */
    IPage<QuestionFile> findQuestionFiles(QueryRequest request, QuestionFile questionFile);

    /**
     * 查询（所有）
     *
     * @param questionFile questionFile
     * @return List<QuestionFile>
     */
    List<QuestionFile> findQuestionFiles(QuestionFile questionFile);

    /**
     * 新增
     *
     * @param questionFile questionFile
     */
    void createQuestionFile(QuestionFile questionFile);

    /**
     * 修改
     *
     * @param questionFile questionFile
     */
    void updateQuestionFile(QuestionFile questionFile);

    /**
     * 删除
     *
     * @param questionFile questionFile
     */
    void deleteQuestionFile(QuestionFile questionFile);
}
