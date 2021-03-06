package com.jsj.service.Impl;

import com.jsj.dao.NewsCateDao;
import com.jsj.dao.NewsDao;
import com.jsj.entity.News;
import com.jsj.entity.NewsCateVo;
import com.jsj.entity.NewsCate;
import com.jsj.factory.DaoFactory;
import com.jsj.service.NewsCateService;
import com.jsj.utils.JdbcUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsCateServiceImpl implements NewsCateService {

    private NewsCateDao newsCateDao = (NewsCateDao) DaoFactory.getDao("NewsCateDao");

    private NewsDao newsDao = (NewsDao) DaoFactory.getDao("NewsDao");

    @Override
    public List<NewsCate> getAllCate() {
        try {
            return newsCateDao.getAll();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<NewsCateVo> getAllNewsCateVo(Integer length) {
        try {
            List<NewsCateVo> allNewsCateVo = new ArrayList<>();
            List<NewsCate> newsAllCate = newsCateDao.getAll();
            for (NewsCate newsCate:newsAllCate ) {
                NewsCateVo newsCateVo = new NewsCateVo();
                newsCateVo.setCateId(newsCate.getId());
                newsCateVo.setName(newsCate.getName());
                newsCateVo.setNews(newsDao.getNewsListByCate(newsCate.getId(),0,length));
                allNewsCateVo.add(newsCateVo);
            }
            return allNewsCateVo;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public NewsCate getNewsCateById(Integer id) {
        try {
            return newsCateDao.getById(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int insert(NewsCate newsCate) {
        try {
            return newsCateDao.insert(newsCate);
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public int update(NewsCate newsCate) {
        try {
            return newsCateDao.update(newsCate);
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public int delete(Integer id) {
        try {
            JdbcUtils.beginTransaction();
            List<News> newsList = newsDao.getNewsListByCate(id,0,newsDao.getCountByCate(id));
            for (News news:newsList) {
                newsDao.deleteById(news.getId());
            }
            int res = newsCateDao.deleteById(id);
            if (res<1){
                try {
                    JdbcUtils.rollbackTransaction();
                    return 0;
                } catch (SQLException ignored) {}
            }
            JdbcUtils.commitTransaction();
            return res;
        } catch (Exception e) {
            try {
                JdbcUtils.rollbackTransaction();
                return 0;
            } catch (Exception ignored) {}
        }
        try {
            return newsCateDao.deleteById(id);
        } catch (Exception e) {
            return -1;
        }
    }
}
