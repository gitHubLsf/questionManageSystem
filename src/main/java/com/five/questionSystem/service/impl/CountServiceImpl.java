package com.five.questionSystem.service.impl;

import com.five.questionSystem.dao.CountDao;
import com.five.questionSystem.service.CountService;
import com.five.questionSystem.vo.Search;
import com.five.questionSystem.vo.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CountServiceImpl implements CountService {

    @Autowired
    private CountDao countDao;

    @Override
    public List<Types> querySystemQuestionNumByDifficulty() {
        ArrayList<Types> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Types type = new Types();
            if (i == 0) {
                type.setName("简单");
            } else if (i == 1) {
                type.setName("较难");
            } else if (i == 2) {
                type.setName("很难");
            }
            int num = this.countDao.querySystemQuestionNumByDifficulty(i);
            type.setValue(num);
            list.add(type);
        }
        return list;
    }


    @Override
    public List<Types> querySystemQuestionNumByGrade() {
        ArrayList<Types> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Types types = new Types();
            if (i == 0) {
                types.setName("一年级");
            } else if (i == 1) {
                types.setName("二年级");
            } else if (i == 2) {
                types.setName("三年级");
            } else if (i == 3) {
                types.setName("四年级");
            } else if (i == 4) {
                types.setName("五年级");
            } else if (i == 5) {
                types.setName("六年级");
            } else if (i == 6) {
                types.setName("初一");
            } else if (i == 7) {
                types.setName("初二");
            } else if (i == 8) {
                types.setName("初三");
            }
            int num = this.countDao.querySystemQuestionNumByGrade(i);
            types.setValue(num);
            list.add(types);
        }
        return list;
    }

    @Override
    public int queryStudentNumUnderTeacher(int id) {
        return this.countDao.queryStudentNumUnderTeacher(id);
    }

    @Override
    public List<Types> queryQuestionNumByTypeUnderTeacher(int id) {
        Search search = new Search();
        search.setUserId(id);
        ArrayList<Types> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Types types = new Types();
            if (i == 0) {
                types.setName("选择题");
            } else if (i == 1) {
                types.setName("填空题");
            } else if (i == 2) {
                types.setName("问答题");
            }
            search.setQuestionId(i);
            int num = this.countDao.queryQuestionNumByTypeUnderTeacher(search);
            types.setValue(num);
            list.add(types);
        }
        return list;
    }

    @Override
    public List<Types> queryQuestionNumByTypeUnderStudent(int id) {
        Search search = new Search();
        search.setUserId(id);
        ArrayList<Types> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Types types = new Types();
            if (i == 0) {
                types.setName("选择题");
            } else if (i == 1) {
                types.setName("填空题");
            } else if (i == 2) {
                types.setName("问答题");
            }
            search.setQuestionId(i);
            int num = this.countDao.queryQuestionNumByTypeUnderStudent(search);
            types.setValue(num);
            list.add(types);
        }
        return list;
    }

    @Override
    public List<Types> queryQuestionNumByDifficultyUnderStudent(int id) {
        Search search = new Search();
        search.setUserId(id);
        ArrayList<Types> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Types types = new Types();
            if (i == 0) {
                types.setName("简单");
            } else if (i == 1) {
                types.setName("较难");
            } else if (i == 2) {
                types.setName("很难");
            }
            search.setQuestionId(i);
            int num = this.countDao.queryQuestionNumByDifficultyUnderStudent(search);
            types.setValue(num);
            list.add(types);
        }
        return list;
    }

    @Override
    public List<Types> queryQuestionNumByDifficultyUnderTeacher(int id) {
        Search search = new Search();
        search.setUserId(id);
        ArrayList<Types> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Types types = new Types();
            if (i == 0) {
                types.setName("简单");
            } else if (i == 1) {
                types.setName("较难");
            } else if (i == 2) {
                types.setName("很难");
            }
            search.setQuestionId(i);
            int num = this.countDao.queryQuestionNumByDifficultyUnderTeacher(search);
            types.setValue(num);
            list.add(types);
        }
        return list;
    }

    @Override
    public List<Types> queryQuestionNumEveryStudentUnderTeacher(int id) {
        return countDao.queryQuestionNumEveryStudentUnderTeacher(id);
    }

    @Override
    public List<Types> queryStudentInfo(int id) {
        ArrayList<Types> list = new ArrayList<>();
        list.add(new Types(this.countDao.queryStudentQuestionNum(id)));
        list.add(new Types(this.countDao.queryStudentOwnExamNum(id)));
        list.add(new Types(this.countDao.queryStudentTeacherExamNum(id)));
        return list;
    }

    @Override
    public int queryQuestionNumUnderTeacher(int id) {
        return countDao.queryQuestionNumUnderTeacher(id);
    }


    @Override
    public List<Types> querySystemInfo() {
        ArrayList<Types> list = new ArrayList<>();
        list.add(new Types(this.countDao.querySystemQuestionNum()));
        list.add(new Types(this.countDao.querySystemExamNum()));
        list.add(new Types(this.countDao.querySystemStudentNum()));
        list.add(new Types(this.countDao.querySystemTeacherNum()));
        list.add(new Types(this.countDao.querySystemLogNum()));
        return list;
    }


    @Override
    public List<Types> querySystemQuestionNumByType() {
        ArrayList<Types> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Types types = new Types();
            if (i == 0) {
                types.setName("选择题");
            } else if (i == 1) {
                types.setName("填空题");
            } else if (i == 2) {
                types.setName("问答题");
            }
            int num = this.countDao.querySystemQuestionNumByType(i);
            types.setValue(num);
            list.add(types);
        }
        return list;
    }


}
