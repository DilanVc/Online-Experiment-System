package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.exercise.service.IQuestionService;
import cc.mrbird.febs.system.mapper.QuestionMapper1;
import cc.mrbird.febs.system.service.QuestionService1;
import cc.mrbird.febs.system.vo.BarVo;
import cc.mrbird.febs.system.vo.QuestionBarVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class QuestionServiceImpl1 extends ServiceImpl<QuestionMapper1,BarVo> implements QuestionService1 {
    @Autowired
    private QuestionMapper1 questionMapper1;
    @Override
    public BarVo getBarVo(){
        List<QuestionBarVo> list = questionMapper1.findAllQuestionBarVo();
        List<String> chapterNames = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        for (QuestionBarVo questionBarVo : list) {
            chapterNames.add(questionBarVo.getChapterName());
            values.add(questionBarVo.getCount());
        }
        BarVo barVo =new BarVo();
        barVo.setChapterNames(chapterNames);
        barVo.setValues(values);
        return barVo;
    }

//    public static void main(String[] args) {
//        QuestionServiceImpl1 questionServiceImpl1 = new QuestionServiceImpl1();
//        BarVo barVo =new BarVo();
//        barVo=questionServiceImpl1.getBarVo();
//        System.out.println(barVo);
//    }
}
