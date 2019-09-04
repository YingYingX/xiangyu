package com.chinamobile.hnu.xiangyu.social.service;

import com.chinamobile.hnu.xiangyu.social.pojo.Dynamic;
import com.chinamobile.hnu.xiangyu.social.pojo.DynamicComment;
import com.chinamobile.hnu.xiangyu.social.vo.DynamicVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface DynamicService {
    int insertDynamic(Dynamic dynamic);
    int insertPhoto(int dynamic_id,String image_route);
    Dynamic getDynamic(int dynamic_id);
    List<String> getPhotos(int dynamic_id);
    int insertLike(int user_id,int dynamic_id);
    List<Integer> selectLike(int dynamic_id);
    Long saveComment(DynamicComment dynamicComment);
    List<Dynamic> getMyDynamic(int user_id);
    DynamicComment selectById(Integer id);
    Long selectCritics(Integer id);
}
