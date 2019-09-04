package com.chinamobile.hnu.xiangyu.web.social;
import java.util.*;
import com.chinamobile.hnu.xiangyu.common.service.PubBindingService;
import com.chinamobile.hnu.xiangyu.sms.SmsService;
import com.chinamobile.hnu.xiangyu.social.pojo.Dynamic;
import com.chinamobile.hnu.xiangyu.social.pojo.DynamicComment;
import com.chinamobile.hnu.xiangyu.social.pojo.Social;
import com.chinamobile.hnu.xiangyu.social.pojo.Photo;
import com.chinamobile.hnu.xiangyu.social.service.DynamicService;
import com.chinamobile.hnu.xiangyu.social.service.SocialService;
import com.chinamobile.hnu.xiangyu.social.service.impl.DynamicServiceImpl;
import com.chinamobile.hnu.xiangyu.social.vo.DynamicCommentVo;
import com.chinamobile.hnu.xiangyu.social.vo.SocialVo;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.user.service.UserAccountService;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.Utils;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;
import com.chinamobile.hnu.xiangyu.web.api.vo.UserCertificationInfoVo;
import com.sun.javafx.collections.MappingChange;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.IOException;

/**
 * 用户账号相关的Web处理逻辑
 *
 * @author songcl
 * @date 2018年5月18日
 */
@RestController
@RequestMapping("/api/social")
public class SocialController {
    private static final Logger logger = LoggerFactory.getLogger(SocialController.class);

    // 短信内容
    public static final String SMS_CONTENT = "验证码：%s。请输入验证码完成验证，验证码有效时间为30分钟【湘遇】";

    @Autowired
    private DynamicService dynamicService;

    @Autowired
    private SocialService service;

    @Resource
    private UserAccountService accountService;

    @Autowired
    private AuthTokenService tokenService;

    /****
     * 填写当前用户信息
     *
     * @return
     */
    @PostMapping("/setinfo.do")
    public ResponseDto setinfo(@RequestBody Map<String , java.lang.Object>maps) {
        Integer uid = (Integer) maps.get("uid");
        UserAccount user = tokenService.getLoginUser();
        if (user == null) {
            return ResultUtil.unLogin();
        }
        try {
            if (uid != null) {
                user = accountService.selectByPrimaryKey(uid);
                if (user == null) {
                  return   ResultUtil.result(0, "没有用户信息");
                }
            }
            Social social_info=new Social();
            social_info.setUser_id(uid);
            social_info.setBirthday((Date)maps.get("birthday"));
            social_info.setDescroption((String)maps.get("descroption"));
            social_info.setTelephone((String)maps.get("telephone"));
            social_info.setLongitude((Double)maps.get("longitude"));
            social_info.setLatitude((Double)maps.get("latitude"));
            service.insert(social_info);
            return ResultUtil.result(0, "信息填写成功");
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("select UserInfo Exception:", e);
            return ResultUtil.serviceException();
        }
    }
    /****
     * 查询当前用户信息
     *
     * @return
     */
    @PostMapping("/getinfo.do")
    public ResponseDto getinfo(@RequestBody Map<String, Object> maps) {
        Integer uid = (Integer) maps.get("uid");
        Integer hisid = (Integer) maps.get("hisid");
        UserAccount user = new UserAccount();
        if (user == null) {
            return ResultUtil.unLogin();
        }
        try {
            if (uid != null) {
                user = accountService.selectByPrimaryKey(uid);
                if (user == null) {
                   return ResultUtil.result(2, "没有用户信息");
                }
            }
            SocialVo vo=service.getUserInfo(uid);
            Calendar cal = Calendar.getInstance();
            Date birth=vo.getBirthday();
            cal.setTime(birth);
            System.out.println(cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.DATE));
            int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22 };
            String[] constellationArr = new String[] { "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座",
                    "处女座", "天秤座", "天蝎座", "射手座", "摩羯座" };
            vo.setConstellation(cal.get(Calendar.DAY_OF_MONTH) < dayArr[cal.get(Calendar.MONTH)]?constellationArr[cal.get(Calendar.MONTH)]:constellationArr[cal.get(Calendar.MONTH)+1]);

            int age = 0;
            Calendar calnow = Calendar.getInstance();
            age = calnow.get(Calendar.YEAR) - cal.get(Calendar.YEAR);   //计算整岁数
            if (calnow.get(Calendar.MONTH) <=cal.get(Calendar.MONTH)) {
                if (calnow.get(Calendar.MONTH) ==cal.get(Calendar.MONTH)) {
                    if (calnow.get(Calendar.DAY_OF_MONTH) <cal.get(Calendar.DAY_OF_MONTH)) age--;//当前日期在生日之前，年龄减一
                } else {
                    age--;//当前月份在生日之前，年龄减一
                }
            }
            vo.setAge(age);
            vo.setConcerned(service.isMyConcern(uid,hisid));
            vo.setFans(service.myFollow(hisid));
                return ResultUtil.result(0, "获取用户信息成功",vo);
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("select UserInfo Exception:", e);
            return ResultUtil.serviceException();
        }
    }

    /****
     * 发表动态
     *
     * @return
     */
    @PostMapping("/publishdynamic.do")
    public ResponseDto publishdynamic(@RequestBody Map<String, Object> maps) {
        Integer uid = (Integer) maps.get("uid");
        UserAccount user = tokenService.getLoginUser();
        if (user == null) {
            return ResultUtil.unLogin();
        }
        try {
            if (uid != null) {
                user = accountService.selectByPrimaryKey(uid);
                if (user == null) {
                  return   ResultUtil.result(2, "没有用户信息");
                }
            }
            Dynamic dynamic = new Dynamic();
            dynamic.setContent((String) maps.get("content"));
            dynamic.setDate(new java.sql.Date(new Date().getTime()));
            dynamic.setUser_id(uid);
            dynamic.setLocation((String) maps.get("location"));
            dynamicService.insertDynamic(dynamic);
            int id=dynamic.getId();
            if(maps.containsKey("photos")&&maps.get("photos")!=null)
            {
                dynamic.setPhotos((List<String>) maps.get("photos"));
                Iterator<String> it = dynamic.getPhotos().iterator();
                while (it.hasNext()) {
                    dynamicService.insertPhoto(id, it.next());
                }
            }
            return ResultUtil.result(0, "发表动态成功");
        }
        catch (Exception e) {
            // TODO: handle exception
            logger.info("insert Dynamic Exception:", e);
            return ResultUtil.serviceException();
        }
    }

    /****
     * 查看动态
     *
     * @return
     */
    @PostMapping("/getdynamic.do")
    public ResponseDto getdynamic(@RequestBody Map<String, Object> maps) {
        Integer uid = (Integer) maps.get("uid");
        UserAccount user = tokenService.getLoginUser();
        if (user == null) {
            return ResultUtil.unLogin();
        }
        try {
            if (uid != null) {
                user = accountService.selectByPrimaryKey(uid);
                if (user == null) {
                    ResultUtil.result(2, "没有用户信息");
                }
            }

            int id = (Integer)maps.get("dynamic_id");
            Dynamic dynamic=dynamicService.getDynamic(id);
            dynamic.setPhotos(dynamicService.getPhotos(id));
            return ResultUtil.result(0, "成功获取动态",dynamic);
        }
        catch (Exception e) {
            // TODO: handle exception
            logger.info("get Dynamic Exception:", e);
            return ResultUtil.serviceException();
        }
    }

    /****
     * 查看我的动态
     *
     * @return
     */
    @PostMapping("/getMyDynamic.do")
    public ResponseDto getMyDynamic(@RequestBody Map<String, Object> maps) {
        Integer uid = (Integer) maps.get("uid");
        UserAccount user = tokenService.getLoginUser();
        if (user == null) {
            return ResultUtil.unLogin();
        }
        try {
            if (uid != null) {
                user = accountService.selectByPrimaryKey(uid);
                if (user == null) {
                    ResultUtil.result(2, "没有用户信息");
                }
            }

            int id = (Integer)maps.get("uid");
            List<Dynamic> dynamic=dynamicService.getMyDynamic(id);
            return ResultUtil.result(0, "成功获取动态",dynamic);
        }
        catch (Exception e) {
            // TODO: handle exception
            logger.info("get Dynamic Exception:", e);
            return ResultUtil.serviceException();
        }
    }
    /****
     * 查看关注人的动态
     *
     * @return
     */
    @PostMapping("/getConcernedDynamic.do")
    public ResponseDto getFollowDynamic(@RequestBody Map<String, Object> maps) {
        Integer uid = (Integer) maps.get("uid");
        UserAccount user = tokenService.getLoginUser();
        if (user == null) {
            return ResultUtil.unLogin();
        }
        try {
            if (uid != null) {
                user = accountService.selectByPrimaryKey(uid);
                if (user == null) {
                    ResultUtil.result(2, "没有用户信息");
                }
            }
            List<Dynamic> dynamics=new ArrayList<>();
            List<Integer> concern=service.getMyConcern(uid);
            for(int i=0;i<concern.size();i++)
            {dynamics.removeAll(dynamicService.getMyDynamic(concern.get(i)));
                dynamics.addAll(dynamicService.getMyDynamic(concern.get(i)));}

            return ResultUtil.result(0, "成功获取动态",dynamics);
        }
        catch (Exception e) {
            // TODO: handle exception
            logger.info("get Dynamic Exception:", e);
            return ResultUtil.serviceException();
        }
    }

    /****
     * 点赞
     *
     * @return
     */
    @PostMapping("/likedynamic.do")
    public ResponseDto likedynamic(@RequestBody Map<String, Object> maps) {
        Integer uid = (Integer) maps.get("uid");
        UserAccount user = tokenService.getLoginUser();
        if (user == null) {
            return ResultUtil.unLogin();
        }
        try {
            if (uid != null) {
                user = accountService.selectByPrimaryKey(uid);
                if (user == null) {
                   return ResultUtil.result(2, "没有用户信息");
                }
            }

            int id = (Integer)maps.get("dynamic_id");
            dynamicService.insertLike(uid,id);
           return ResultUtil.result(0, "点赞成功！");
        }
        catch (Exception e) {
            // TODO: handle exception
            logger.info("get Dynamic Exception:", e);
            return ResultUtil.serviceException();
        }
    }

    /****
     * 查看点赞
     *
     * @return
     */
    @PostMapping("/getdynamiclike.do")
    public ResponseDto getdynamiclike(@RequestBody Map<String, Object> maps) {
        Integer uid = (Integer) maps.get("uid");
        UserAccount user = tokenService.getLoginUser();
        if (user == null) {
            return ResultUtil.unLogin();
        }
        try {
            if (uid != null) {
                user = accountService.selectByPrimaryKey(uid);
                if (user == null) {
                    ResultUtil.result(2, "没有用户信息");
                }
            }
            List<Integer> like=dynamicService.selectLike((Integer) maps.get("dynamic_id"));

            return ResultUtil.result(0, "成功获取点赞信息",like);
        }
        catch (Exception e) {
            // TODO: handle exception
            logger.info("get Dynamic Exception:", e);
            return ResultUtil.serviceException();
        }
    }


    /****
     * 查看相册
     *
     * @return
     */
    @PostMapping("/getalbum.do")
    public ResponseDto getalbum(@RequestBody Map<String, Object> maps) {
        Integer uid = (Integer) maps.get("uid");
        UserAccount user = tokenService.getLoginUser();
        if (user == null) {
            return ResultUtil.unLogin();
        }
        try {
            if (uid != null) {
                user = accountService.selectByPrimaryKey(uid);
                if (user == null) {
                    ResultUtil.result(2, "没有用户信息");
                }
            }
            List<Photo> photos=service.getMyAlbum(uid);

            return ResultUtil.result(0, "成功获取相册信息",photos);
        }
        catch (Exception e) {
            // TODO: handle exception
            logger.info("get Dynamic Exception:", e);
            return ResultUtil.serviceException();
        }
    }

    /****
     * 搜索
     *
     * @return
     */
    @PostMapping("/search.do")
    public ResponseDto search(@RequestBody Map<String, Object> maps) {
        Integer uid = (Integer) maps.get("uid");
        UserAccount user = tokenService.getLoginUser();
        if (user == null) {
            return ResultUtil.unLogin();
        }
        try {
            if (uid != null) {
                user = accountService.selectByPrimaryKey(uid);
                if (user == null) {
                    ResultUtil.result(2, "没有用户信息");
                }
            }
            List<Integer> id= service.getAll();
            if(!((String)maps.get("label")).equals("全部兴趣"))
            {id.retainAll(service.getSameLabel(uid));}
            if(!((String) maps.get("sex")).equals("无限制"))
            {
                int sex;
                if(((String) maps.get("sex")).equals("男"))
                {id.retainAll(service.selectBySex(1));}
                else
                {id.retainAll(service.selectBySex(2));}
            }
            if(!((String) maps.get("college")).equals("无限制"))
            {id.retainAll(service.selectByCollege((String) maps.get("college")));}
            List<SocialVo> info=new ArrayList<>();
            SocialVo temp=new SocialVo();
            for(int i=0;i<id.size();i++)
            {temp=service.getUserInfo(id.get(i));
                if (temp!=null&&temp.getId()!=uid)
                {info.add(temp);} }



            return ResultUtil.result(0, "搜索成功",info);
        }
        catch (Exception e) {
            // TODO: handle exception
            logger.info("get Dynamic Exception:", e);
            return ResultUtil.serviceException();
        }
    }

    /**
     * 评论
     * @param maps
     * @return
     */
    @PostMapping("saveComment.do")
    public ResponseDto saveComment(@RequestBody Map<String,Object>maps) {
        UserAccount user = tokenService.getLoginUser();
        Integer presentor = user.getId();
        if (user == null) {
            return ResultUtil.unLogin();
        }
        try {
            DynamicComment dynamicComment = new DynamicComment();
            dynamicComment.setPresentor(presentor);
            dynamicComment.setDynamic_id((Integer)maps.get("dynamic_id"));
            dynamicComment.setId((Integer)maps.get("id"));
            dynamicComment.setContent((String)maps.get("content"));
            dynamicComment.setGmt_create(new Date());
            dynamicComment.setType((Integer)maps.get("type"));
            dynamicComment.setCritics((Integer)maps.get("critics"));
            DynamicService dynamicService = new DynamicServiceImpl();
            Long ids = dynamicService.saveComment(dynamicComment);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", ids);
            return ResultUtil.result(0, "评论成功",map);
        } catch (Exception e) {
            logger.error("Dynamic comment is err:", e);
            return ResultUtil.serviceException();
        }
    }

    /**
     *
     * 查询动态的所有评论
     * @param dynamic_id
     * @return
     */
    @GetMapping("selectById.do")
    public ResponseDto selectById(@RequestParam (name = "dynamic_id")Integer dynamic_id) {
        UserAccount user = tokenService.getLoginUser();
        if (user == null) {
            return ResultUtil.unLogin();
        }
        try {
            DynamicComment dynamicComment = new DynamicComment();
            DynamicService dynamicService = new DynamicServiceImpl();
            dynamicComment = dynamicService.selectById(dynamic_id);
            if(dynamicComment.getType() == 1){
                Integer nickId = dynamicComment.getCritics();
                Long users = dynamicService.selectCritics(nickId);
            }
            else{
                Integer nickId0 = dynamicComment.getPresentor();
                Integer nickId1 = dynamicComment.getCritics();
                Long users = dynamicService.selectCritics(nickId0);
                Long users1 = dynamicService.selectCritics(nickId1);
            }

            return ResultUtil.result(0,"查询成功",dynamicComment);
        } catch (Exception e) {
            logger.error("查询失败", e);
            return ResultUtil.serviceException();
        }
    }

}