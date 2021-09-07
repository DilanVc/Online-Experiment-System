package cc.mrbird.febs.exercise.service;

import cc.mrbird.febs.exercise.entity.Record;

import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author liuxin
 * @date 2021-02-22 19:17:30
 */
public interface IRecordService extends IService<Record> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param record record
     * @return IPage<Record>
     */
    IPage<Record> findRecords(QueryRequest request, Record record);

    /**
     * 查询（所有）
     *
     * @param record record
     * @return List<Record>
     */
    List<Record> findRecords(Record record);

    /**
     * 新增
     *
     * @param record record
     */
    void createRecord(Record record);

    /**
     * 修改
     *
     * @param record record
     */
    void updateRecord(Record record);

    /**
     * 删除
     *
     * @param record record
     */
    void deleteRecord(Record record);
}
