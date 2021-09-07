package cc.mrbird.febs.exercise.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.exercise.entity.Record;
import cc.mrbird.febs.exercise.service.IRecordService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  Controller
 *
 * @author liuxin
 * @date 2021-02-22 19:17:30
 */
@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class RecordController extends BaseController {

    private final IRecordService recordService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "record")
    public String recordIndex(){
        return FebsUtil.view("record/record");
    }

    @GetMapping("record")
    @ResponseBody
    @RequiresPermissions("exercise:view")
    public FebsResponse getAllRecords(Record record) {
        record.setStudentId(getCurrentUser().getUserId());
        return new FebsResponse().success().data(recordService.findRecords(record));
    }

    @GetMapping("record/list")
    @ResponseBody
    @RequiresPermissions("exercise:view")
    public FebsResponse recordList(QueryRequest request, Record record) {
        Map<String, Object> dataTable = getDataTable(this.recordService.findRecords(request, record));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增Record", exceptionMessage = "新增Record失败")
    @PostMapping("record")
    @ResponseBody
    @RequiresPermissions("exercise:view")
    public FebsResponse addRecord(@Valid Record record) {
        record.setStudentId(getCurrentUser().getUserId());
        record.setCreateTime(new Date());
        this.recordService.createRecord(record);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除Record", exceptionMessage = "删除Record失败")
    @GetMapping("record/delete")
    @ResponseBody
    @RequiresPermissions("record:delete")
    public FebsResponse deleteRecord(Record record) {
        this.recordService.deleteRecord(record);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改Record", exceptionMessage = "修改Record失败")
    @PostMapping("record/update")
    @ResponseBody
    @RequiresPermissions("record:update")
    public FebsResponse updateRecord(Record record) {
        this.recordService.updateRecord(record);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改Record", exceptionMessage = "导出Excel失败")
    @PostMapping("record/excel")
    @ResponseBody
    @RequiresPermissions("record:export")
    public void export(QueryRequest queryRequest, Record record, HttpServletResponse response) {
        List<Record> records = this.recordService.findRecords(queryRequest, record).getRecords();
        ExcelKit.$Export(Record.class, response).downXlsx(records, false);
    }
}
