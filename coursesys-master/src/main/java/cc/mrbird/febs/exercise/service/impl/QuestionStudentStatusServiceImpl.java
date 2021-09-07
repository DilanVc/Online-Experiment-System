package cc.mrbird.febs.exercise.service.impl;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.exercise.entity.QuestionStudentStatus;
import cc.mrbird.febs.exercise.mapper.QuestionStudentStatusMapper;
import cc.mrbird.febs.exercise.service.IQuestionStudentStatusService;
import cc.mrbird.febs.exercise.vo.BarVo;
import cc.mrbird.febs.exercise.vo.ChapterErrorQuestionVo;
import cc.mrbird.febs.exercise.vo.ErrorQuestionTimesBarVo;
import cc.mrbird.febs.exercise.vo.QuestionBarVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 *  Service实现
 *
 * @author liuxin
 * @date 2021-02-23 18:09:21
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class QuestionStudentStatusServiceImpl extends ServiceImpl<QuestionStudentStatusMapper, QuestionStudentStatus> implements IQuestionStudentStatusService {

    private final QuestionStudentStatusMapper questionStudentStatusMapper;

    @Override
    public IPage<QuestionStudentStatus> findQuestionStudentStatuss(QueryRequest request, QuestionStudentStatus questionStudentStatus) {
        LambdaQueryWrapper<QuestionStudentStatus> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<QuestionStudentStatus> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<QuestionStudentStatus> findQuestionStudentStatuss(QuestionStudentStatus questionStudentStatus) {
	    LambdaQueryWrapper<QuestionStudentStatus> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public QuestionStudentStatus findQuestionByStudentIdAndQuestionID(QuestionStudentStatus questionStudentStatus) {
        return  questionStudentStatusMapper.findQuestionByStudentIdAndQuestionID(questionStudentStatus.getQuestionId(),questionStudentStatus.getStudentId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createQuestionStudentStatus(QuestionStudentStatus questionStudentStatus) {
        this.save(questionStudentStatus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateQuestionStudentStatus(QuestionStudentStatus questionStudentStatus) {
        this.saveOrUpdate(questionStudentStatus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteQuestionStudentStatus(QuestionStudentStatus questionStudentStatus) {
        LambdaQueryWrapper<QuestionStudentStatus> wrapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wrapper);
	}

    @Override
    public List<QuestionBarVo> getChapterRight(Long userId) {
        System.out.println(userId);
        return questionStudentStatusMapper.getChapterRight(userId);
    }

    @Override
    public List<ChapterErrorQuestionVo> getChapterErrorByUserId(Long userId) {
       return questionStudentStatusMapper.getChapterErrorByUserId(userId);
    }

    @Override
    public List<ErrorQuestionTimesBarVo> getErrorQuestionTimesBarVoById(Long userId) {

        return questionStudentStatusMapper.getErrorQuestionTimesBarVoById(userId);
    }
}
