package com.qst.agrifinancetrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qst.agrifinancetrade.dao.ExpertDao;
import com.qst.agrifinancetrade.entity.Expert;
import com.qst.agrifinancetrade.service.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpertServiceImpl extends ServiceImpl<ExpertDao, Expert> implements ExpertService {
    @Autowired
    private ExpertDao expertDao;

    //每页显示多条数据
    private  Integer pageSize = 30;

    @Override
    public Expert selectById(String name) {

        Expert expert = expertDao.selectById(name);

        return expert;
    }

    @Override
    public List<Expert> selectAllExpert() {

        List<Expert> experts = expertDao.selectList(null);

        return experts;
    }

    @Override
    public void delete(String name) {
        expertDao.deleteById(name);
    }

    @Override
    public void insert(Expert expert) {
        expertDao.insert(expert);
    }

    @Override
    public int updateById(Expert expert) {
        return expertDao.updateById(expert);
    }

    @Override
    public Page<Expert> findPage(Integer pageNum){
        Page<Expert> page = new Page<>(pageNum, pageSize);
        return expertDao.selectPage(page, null);
    }

    @Override
    public Page<Expert> findPageByKeys(String keys, Integer pageNum){
        Page<Expert> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Expert> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_name", keys).or()
                  .like("real_name", keys).or()
                  .like("profession", keys).or()
                  .like("position", keys).or()
                  .like("belong", keys);
        return expertDao.selectPage(page, queryWrapper);
    }
}
