package com.chinamobile.hnu.xiangyu.social.service.impl;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskInfo;
import com.chinamobile.hnu.xiangyu.social.dao.DynamicMapper;
import com.chinamobile.hnu.xiangyu.social.pojo.Dynamic;
import com.chinamobile.hnu.xiangyu.social.pojo.DynamicComment;
import com.chinamobile.hnu.xiangyu.social.service.DynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DynamicServiceImpl implements DynamicService {
    @Autowired
    private DynamicMapper dao;

    public int insertDynamic(Dynamic dynamic){
            return  dao.insertDynamic(dynamic);
    }
    public int insertPhoto(int dynamic_id,String image_route){return dao.insertPhoto(dynamic_id,image_route);}
    public Dynamic getDynamic(int dynamic_id){return dao.getDynamic(dynamic_id);}
    public List<String> getPhotos(int dynamic_id){return dao.getPhotos(dynamic_id);}
    public int insertLike(int user_id,int dynamic_id){return dao.insertLike(user_id,dynamic_id);}
    public List<Integer> selectLike(int dynamic_id){return dao.selectLike(dynamic_id);}
    public Long saveComment(DynamicComment dynamicComment){return dao.saveComment(dynamicComment);}
    public DynamicComment selectById(Integer id){return dao.selectById(id);}
    public List<Dynamic> getMyDynamic(int user_id){return dao.getMyDynamic(user_id);}
    public Long selectCritics(Integer id){return dao.selectCritics(id);}
}
