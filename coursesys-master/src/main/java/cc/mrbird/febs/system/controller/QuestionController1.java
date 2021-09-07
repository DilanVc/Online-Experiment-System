package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.system.service.QuestionService1;
import cc.mrbird.febs.system.vo.BarVo;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QuestionController1 {
    @Autowired
    private QuestionService1 questionService1;

    @RequestMapping("/barVo")
    @RequestBody
    public BarVo getBarVo(){
        return questionService1.getBarVo();
    }

}
