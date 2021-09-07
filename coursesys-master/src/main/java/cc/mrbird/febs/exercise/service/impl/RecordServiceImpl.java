package cc.mrbird.febs.exercise.service.impl;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.exercise.entity.Record;
import cc.mrbird.febs.exercise.mapper.RecordMapper;
import cc.mrbird.febs.exercise.service.IRecordService;
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
 * @date 2021-02-22 19:17:30
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {

    private final RecordMapper recordMapper;

    @Override
    public IPage<Record> findRecords(QueryRequest request, Record record) {
        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<Record> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<Record> findRecords(Record record) {
	    LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
        queryWrapper.eq(Record::getStudentId,record.getStudentId());
        queryWrapper.eq(Record::getQuestionId,record.getQuestionId());
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createRecord(Record record) {
        this.save(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRecord(Record record) {
        this.saveOrUpdate(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRecord(Record record) {
        LambdaQueryWrapper<Record> wrapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wrapper);
	}
}
