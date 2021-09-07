package cc.mrbird.febs.exercise.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.exercise.entity.QuestionStudentStatus;
import cc.mrbird.febs.exercise.entity.Record;
import cc.mrbird.febs.exercise.service.IQuestionStudentStatusService;
import cc.mrbird.febs.exercise.service.IRecordService;
import cc.mrbird.febs.exercise.vo.*;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;



import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  Controller
 *
 * @author liuxin
 * @date 2021-02-23 18:09:21
 */
@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class QuestionStudentStatusController extends BaseController {

    private final IQuestionStudentStatusService questionStudentStatusService;

    private final IRecordService recordService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "questionStudentStatus")
    public String questionStudentStatusIndex(){
        return FebsUtil.view("questionStudentStatus/questionStudentStatus");
    }

    @GetMapping("questionStudentStatus")
    @ResponseBody
    @RequiresPermissions("questionStudentStatus:list")
    public FebsResponse getAllQuestionStudentStatuss(QuestionStudentStatus questionStudentStatus) {
        return new FebsResponse().success().data(questionStudentStatusService.findQuestionStudentStatuss(questionStudentStatus));
    }

    @GetMapping("questionStudentStatus/list")
    @ResponseBody
    @RequiresPermissions("questionStudentStatus:list")
    public FebsResponse questionStudentStatusList(QueryRequest request, QuestionStudentStatus questionStudentStatus) {
        Map<String, Object> dataTable = getDataTable(this.questionStudentStatusService.findQuestionStudentStatuss(request, questionStudentStatus));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增QuestionStudentStatus", exceptionMessage = "新增QuestionStudentStatus插入失败")
    @PostMapping("questionStudentStatus")
    @ResponseBody
    @RequiresPermissions("exercise:view")
    public FebsResponse addQuestionStudentStatus(@Valid QuestionStudentStatus questionStudentStatus) {

//        System.out.println(questionStudentStatus);
        questionStudentStatus.setStudentId(getCurrentUser().getUserId());
//        System.out.println(" 1 2 3 3 4 4 ");

        Long currentUserId = getCurrentUser().getUserId();
        Long currentQuestionId = questionStudentStatus.getQuestionId();
        System.out.println(currentQuestionId);
        Record record = new Record();
        record.setQuestionId(currentQuestionId);
        record.setStudentId(currentUserId);
        List<Record> recordList = recordService.findRecords(record);

        int times = recordList.size() + 1;
        QuestionStudentStatus questionStudentStatus1 = questionStudentStatusService.findQuestionByStudentIdAndQuestionID(questionStudentStatus);
//        System.out.println("上句执行了");

        if(questionStudentStatus1!=null)
       {
//           System.out.println("非空");
//           System.out.println(questionStudentStatus);
//               System.out.println(questionStudentStatus1);
//               System.out.println("存在记录");

               if(questionStudentStatus1.getStatus().equals("error")&&questionStudentStatus.getStatus().equals("error"))
               {
//                   System.out.println("修改记录");
                   questionStudentStatus1.setTimes(questionStudentStatus1.getTimes()+1);
                   questionStudentStatusService.updateQuestionStudentStatus(questionStudentStatus1);

               }else if(questionStudentStatus1.getStatus().equals("error")&&questionStudentStatus.getStatus().equals("success"))
               {
//                   System.out.println("存在成功的记录");
                   questionStudentStatus.setTimes(questionStudentStatus1.getTimes()+1);
                   questionStudentStatus.setStatus("success");
               }

       }else {
//            System.out.println("插入新纪录");
           questionStudentStatus.setTimes(times);
           questionStudentStatus.setCreateTime(new Date());
           System.out.println(questionStudentStatus);
           this.questionStudentStatusService.createQuestionStudentStatus(questionStudentStatus);
       }
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除QuestionStudentStatus", exceptionMessage = "删除QuestionStudentStatus失败")
    @GetMapping("questionStudentStatus/delete")
    @ResponseBody
    @RequiresPermissions("questionStudentStatus:delete")
    public FebsResponse deleteQuestionStudentStatus(QuestionStudentStatus questionStudentStatus) {
        this.questionStudentStatusService.deleteQuestionStudentStatus(questionStudentStatus);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改QuestionStudentStatus", exceptionMessage = "修改QuestionStudentStatus失败")
    @PostMapping("questionStudentStatus/update")
    @ResponseBody
    @RequiresPermissions("questionStudentStatus:update")
    public FebsResponse updateQuestionStudentStatus(QuestionStudentStatus questionStudentStatus) {
        this.questionStudentStatusService.updateQuestionStudentStatus(questionStudentStatus);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改QuestionStudentStatus", exceptionMessage = "导出Excel失败")
    @PostMapping("questionStudentStatus/excel")
    @ResponseBody
    @RequiresPermissions("questionStudentStatus:export")
    public void export(QueryRequest queryRequest, QuestionStudentStatus questionStudentStatus, HttpServletResponse response) {
        List<QuestionStudentStatus> questionStudentStatuss = this.questionStudentStatusService.findQuestionStudentStatuss(queryRequest, questionStudentStatus).getRecords();
        ExcelKit.$Export(QuestionStudentStatus.class, response).downXlsx(questionStudentStatuss, false);
    }

    /*
    *  获取章节正确的题目数量
    * */
    @GetMapping("chapter/rightNum")
    @ResponseBody
    @RequiresPermissions("exercise:view")
    public FebsResponse getChapterRightNum(QueryRequest request, QuestionStudentStatus questionStudentStatus) {

        Long userId = questionStudentStatus.getStudentId();
//        System.out.println(userId);
//        System.out.println(questionStudentStatus);
        List<QuestionBarVo> barVoList = this.questionStudentStatusService.getChapterRight(userId);
//        System.out.println(barVoList);

            List<String> chapterNam1 = new ArrayList<>();
            List<Integer> totalNumbers = new ArrayList<>();
            List<Integer> rightNumbers = new ArrayList<>();

            for (QuestionBarVo questionBarVo : barVoList) {
                chapterNam1.add(questionBarVo.getChapterNam1());
                totalNumbers.add(questionBarVo.getTotalNumber());
                rightNumbers.add(questionBarVo.getRightNumber());

            }
            BarVo barVo = new BarVo();
            barVo.setChapterNam1(chapterNam1);
            barVo.setTotalNumber(totalNumbers);
            barVo.setRightNumber(rightNumbers);

//            System.out.println(barVo);
        return new FebsResponse().success().data(barVo);
    }

    /*
     *  获取个人中心章节正确的题目数量
     * */
    @GetMapping("chapter/selfRightNum")
    @ResponseBody
    @RequiresPermissions("exercise:view")
    public FebsResponse getSelfChapterRightNum(QueryRequest request, QuestionStudentStatus questionStudentStatus) {

        Long userId = getCurrentUser().getUserId();

        List<QuestionBarVo> barVoList = this.questionStudentStatusService.getChapterRight(userId);
        List<String> chapterNam1 = new ArrayList<>();
        List<Integer> totalNumbers = new ArrayList<>();
        List<Integer> rightNumbers = new ArrayList<>();

        for (QuestionBarVo questionBarVo : barVoList) {
            chapterNam1.add(questionBarVo.getChapterNam1());
            totalNumbers.add(questionBarVo.getTotalNumber());
            rightNumbers.add(questionBarVo.getRightNumber());

        }
        BarVo barVo = new BarVo();
        barVo.setChapterNam1(chapterNam1);
        barVo.setTotalNumber(totalNumbers);
        barVo.setRightNumber(rightNumbers);

//        System.out.println("1 2 3 4 4");
//        System.out.println(barVo);
        return new FebsResponse().success().data(barVo);
    }



    /*
     *  获取个人中心章节正确的题目数量
     * */
    @GetMapping("chapter/errorQuestionTimes")
    @ResponseBody
    @RequiresPermissions("exercise:view")
    public FebsResponse getErrorQuestionTimes(QueryRequest request, QuestionStudentStatus questionStudentStatus) {

        Long userId = getCurrentUser().getUserId();

        List<ErrorQuestionTimesBarVo> errorQuestionTimesBarVoList = this.questionStudentStatusService.getErrorQuestionTimesBarVoById(userId);
        List<String> questionTitle = new ArrayList<>();
        List<Integer> timesss = new ArrayList<>();


        for (ErrorQuestionTimesBarVo errorQuestionTimesBarVo :errorQuestionTimesBarVoList) {
            questionTitle.add(errorQuestionTimesBarVo.getQuestionTitle());
            timesss.add(errorQuestionTimesBarVo.getTimes());


        }
        QuestionTimesBarVo questionTimesBarVo = new QuestionTimesBarVo();
        questionTimesBarVo.setQuestionTitles(questionTitle);
        questionTimesBarVo.setTimess(timesss);

//        System.out.println(questionTimesBarVo);
        return new FebsResponse().success().data(questionTimesBarVo);
    }
    /*
     *  获取每个人章节的错误题
     * */
    @GetMapping("chapter/selfErrorQuestion")
    @ResponseBody
    @RequiresPermissions("exercise:view")
    public FebsResponse getErrorQuestionByUserId(Long userId) {//为什么useId传不进来？//getCurrentUser()是在哪写好的
//      Long userId=questionStudentStatus.getStudentId();
        Long userid=getCurrentUser().getUserId();
//        System.out.println(userid);
        List<ChapterErrorQuestionVo> chapterErrorQuestionVoList= questionStudentStatusService.getChapterErrorByUserId(userid);
//        System.out.println(chapterErrorQuestionVoList);
        return new FebsResponse().success().data(chapterErrorQuestionVoList);
    }


}
