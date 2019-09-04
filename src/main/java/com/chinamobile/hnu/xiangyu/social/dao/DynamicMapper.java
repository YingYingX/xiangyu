package com.chinamobile.hnu.xiangyu.social.dao;

import com.chinamobile.hnu.xiangyu.social.pojo.Dynamic;
import com.chinamobile.hnu.xiangyu.social.pojo.DynamicComment;
import com.chinamobile.hnu.xiangyu.social.vo.SocialVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DynamicMapper {
    int insertDynamic(Dynamic dynamic);
    int insertPhoto(@Param("dynamic_id")int dynamic_id, @Param("image_route")String image_route);
    Dynamic getDynamic(int dynamic_id);
    List<String> getPhotos(int dynamic_id);
    int insertLike(@Param("user_id")int user_id,@Param("dynamic_id")int dynamic_id);
    List<Integer>selectLike(int dynamic_id);
    List<Dynamic> getMyDynamic(int user_id);
    Long saveComment(DynamicComment dynamicComment);
    DynamicComment selectById(Integer id);
    Long selectCritics(Integer id);
}
