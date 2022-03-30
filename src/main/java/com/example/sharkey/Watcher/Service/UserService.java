package com.example.sharkey.Watcher.Service;

/**
 * Author:Sharkey
 * Date:2020/8/1
 */

import com.example.sharkey.Entity.*;
import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Watcher.Mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private FileService fileService;

    @Value("${sharkey.static.img.path}")
    private String prefix;

    public User getUserByPrimaryAccount(String username) {
        return userMapper.getUserByPrimaryAccount(username);
    }

    public boolean changeProfile(String profile, String username) {
        return userMapper.changeProfile(profile, username);
    }

    public RespBean insertUser(User user) {
        try {
            int num = 0;
            num = userMapper.getUserNum(user.getUsername());
            if (num > 0) {
                return RespBean.error("用户名重复");
            }
            num = userMapper.getEmailNum(user.getEmail());
            if (num > 0) {
                return RespBean.error("邮箱已被注册");
            }
            boolean result = userMapper.insertUser(user);
            if (!result) {
                return RespBean.error("注册失败");
            }
            return RespBean.ok("注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("注册失败");
        }
    }

    public RespPageBean getAllUsers() {
        List<User> data = userMapper.getAllUsers();
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(Long.parseLong(String.valueOf(data.size())));
        return bean;
    }

    public RespPageBean getUsersByListParams(List<String> list) {
        List<User> data = userMapper.getUsersByListParams(list);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(Long.parseLong(String.valueOf(data.size())));
        return bean;
    }

    public RespBean UpdateUserInfo(User user) {
        if (userMapper.UpdateUserInfo(user)) {
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    public boolean UpdateUserIp(int id, String ip) {
        if (userMapper.UpdateUserIp(id, ip)) {
            MyLogger.logger("Ip更新成功");
            return true;
        }
        return false;
    }

    public RespPageBean getClockListByUserName(String username) {
        List<ClockIn> list = userMapper.getClockInList(username);
        RespPageBean bean = new RespPageBean();
        bean.setData(list);
        bean.setTotal(Long.parseLong(String.valueOf(list.size())));
        return bean;
    }

    public RespBean insertClockIn(ClockIn ci) {
        try {
            userMapper.insertClockIn(ci);
        } catch (Exception e) {
            MyLogger.logger(e.getMessage());
            return RespBean.error("打卡失败");
        }
        return RespBean.ok("打卡成功");
    }

    public RespPageBean getMemoListByUserName(String username) {
        List<Memo> data = userMapper.getMemoList(username);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(Long.parseLong(String.valueOf(data.size())));
        return bean;
    }

    public RespBean insertMemo(Memo memo) {
        try {
            userMapper.insertMemo(memo);
        } catch (Exception e) {
            MyLogger.logger(e.getMessage());
            return RespBean.error("添加失败");
        }
        return RespBean.ok("添加成功");
    }

    public RespBean deleteMemo(Memo memo) {
        try {
            MyLogger.logger(memo.getMemo());
            userMapper.deleteMemo(memo);
        } catch (Exception e) {
            MyLogger.logger(e.getMessage());
            return RespBean.error("删除失败");
        }
        return RespBean.ok("删除成功");
    }

    public RespBean getDIYPageByUsername(String username) {
        try {
            System.out.println(username);
            DIYPage data = userMapper.getDIYPageByUsername(username);
            if (data == null) {
                return RespBean.error(29400, "个人网页不存在");
            }
            return RespBean.ok(29200, "获取成功", data);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(29400, "未知错误");
        }
    }

    public RespBean insertDIYPage(String username, String paths) {
        try {
            DIYPage diyPage = new DIYPage();
            diyPage.setPath("/DIYPage/" + username + paths);
            diyPage.setUsername(username);
            diyPage.setType(2);
            userMapper.insertDIYPage(diyPage);
            return RespBean.ok("插入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("未知错误");
        }
    }

    public RespBean insertDIYPage(DIYPage diyPage) {
        try {
            userMapper.insertDIYPage(diyPage);
            return RespBean.ok("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("添加失败");
        }
    }

    public RespBean deleteDIYPage(String username) {
        if (StringUtils.isBlank(username)) {
            return RespBean.error("请求失败");
        }
        try {
            DIYPage diyPage = userMapper.getDIYPageByUsername(username);
            if (diyPage.getType() == 2) {
                boolean res = fileService.deleteDir(new File(prefix + File.separator + "DIYPage" + File.separator + username));
                if (!res) {
                    return RespBean.error("未知错误");
                }
            }
            userMapper.deleteDIYPage(username);
            return RespBean.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("未知错误");
        }
    }

    public RespBean getUserConfigByUserName(String username) {
        try {
            UserConfig userConfig = userMapper.getUserConfigByUserName(username);
            return RespBean.ok(29200, userConfig);
        } catch (Exception e) {
            return RespBean.error("执行错误");
        }
    }

    public RespBean updateUserConfig(UserConfig userConfig) {
        try {
            userMapper.updateUserConfig(userConfig);
            return RespBean.ok("修改陈坤");
        } catch (Exception e) {
            return RespBean.error("修改失败");
        }
    }

    public RespBean getVoteConfigByUsername(String username) {
        try {
            VoteConfig voteConfig = userMapper.getVoteConfig(username);
            return RespBean.ok(29200, voteConfig);
        } catch (Exception e) {
            return RespBean.error("执行错误");
        }
    }

    public RespBean updateVoteConfig(VoteConfig voteConfig) {
        try {
            userMapper.updateVoteConfig(voteConfig);
            return RespBean.ok("修改陈坤");
        } catch (Exception e) {
            log.error(e.getMessage());
            return RespBean.error("修改失败");
        }
    }
}
