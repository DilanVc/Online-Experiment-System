package cc.mrbird.febs.exercise.service;

import cc.mrbird.febs.exercise.entity.QuestionStudentStatus;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.exercise.vo.BarVo;
import cc.mrbird.febs.exercise.vo.ChapterErrorQuestionVo;
import cc.mrbird.febs.exercise.vo.ErrorQuestionTimesBarVo;
import cc.mrbird.febs.exercise.vo.QuestionBarVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 *  Service接口
 *
 * @author liuxin
 * @date 2021-02-23 18:09:21
 */
public interface IQuestionStudentStatusService extends IService<QuestionStudentStatus> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param questionStudentStatus questionStudentStatus
     * @return IPage<QuestionStudentStatus>
     */
    IPage<QuestionStudentStatus> findQuestionStudentStatuss(QueryRequest request, QuestionStudentStatus questionStudentStatus);

    /**
     * 查询（所有）
     *
     * @param questionStudentStatus questionStudentStatus
     * @return List<QuestionStudentStatus>
     */
    List<QuestionStudentStatus> findQuestionStudentStatuss(QuestionStudentStatus questionStudentStatus);


    /**
     * 查询一个学生做的题
     *
     * @param questionStudentStatus questionStudentStatus
     * @return List<QuestionStudentStatus>
     */
    QuestionStudentStatus findQuestionByStudentIdAndQuestionID(QuestionStudentStatus questionStudentStatus);



    /**
     * 新增
     *
     * @param questionStudentStatus questionStudentStatus
     */
    void createQuestionStudentStatus(QuestionStudentStatus questionStudentStatus);

    /**
     * 修改
     *
     * @param questionStudentStatus questionStudentStatus
     */
    void updateQuestionStudentStatus(QuestionStudentStatus questionStudentStatus);

    /**
     * 删除
     *
     * @param questionStudentStatus questionStudentStatus
     */
    void deleteQuestionStudentStatus(QuestionStudentStatus questionStudentStatus);



    List<QuestionBarVo> getChapterRight(Long userId);



    List<ChapterErrorQuestionVo> getChapterErrorByUserId(Long userId);


    List<ErrorQuestionTimesBarVo> getErrorQuestionTimesBarVoById(Long userId);
}
