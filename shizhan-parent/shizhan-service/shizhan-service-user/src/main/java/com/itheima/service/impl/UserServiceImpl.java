package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.UserMapper;
import com.itheima.service.UserService;
import com.itheima.user.pojo.User;
import com.itheima.util.IdWorker;
import com.sun.xml.internal.ws.resources.SenderMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/****
 * @Author:itheima
 * @Description:User业务层接口实现类
 *****/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserMapper userMapper;

    /**
     * 未登录时点击忘记密码
     *
     * @param username
     * @param oldPassword
     * @param phoneNumber
     */
    @Override
    public String forgetPassword(String username, String phoneNumber) {
        // 根据用户名查询用户
        User user = userMapper.selectByPrimaryKey(username);
        if (null != user) {
            throw new RuntimeException("用户名不存在");
        }
        // 发送验证码给用户
        return senMessageToUser(phoneNumber);
    }

    // 发送验证码给用户
    private String senMessageToUser(String phoneNumber) {
        // 将验证码与手机号存入redis.以便验证,时效为5分钟
        String id = String.valueOf(idWorker.nextId());
        redisTemplate.boundValueOps(phoneNumber).set(id);

        return "手机号码" + phoneNumber + "的验证码是:" + id;
    }

    /**
     * 已登录时的重置密码
     *
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    @Override
    public void updatePasswordWithPassword(String username, String oldPassword, String newPassword) {
        User example = new User();
        example.setUsername(username);
        example.setPassword(oldPassword);
        // 根据用户名和密码查询用户
        User user = userMapper.selectOne(example);
        if (null != user) {
            // 设置用户的密码为新密码
            user.setPassword(newPassword);
            userMapper.updateByPrimaryKeySelective(user);
        } else {
            throw new RuntimeException("密码错误");
        }
    }

    /**
     * @param username    用户名
     * @param phoneNumber 电话号码
     * @param code        验证码
     * @param newPassword 新的密码
     * @description 使用手机号和验证码重置密码
     */
    @Override
    public void updatePasswordWithCode(String username, String phoneNumber, String code, String newPassword) {
        String redisCode = (String) redisTemplate.boundValueOps(phoneNumber).get();
        if (code != null && code.equals(redisCode)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(newPassword);
            userMapper.updateByPrimaryKeySelective(user);
        } else {
            throw new RuntimeException("用户名或验证码错误");
        }
    }

    /**
     * User条件+分页查询
     *
     * @param user 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<User> findPage(User user, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //搜索条件构建
        Example example = createExample(user);
        //执行搜索
        return new PageInfo<User>(userMapper.selectByExample(example));
    }

    /**
     * User分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<User> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page, size);
        //分页查询
        return new PageInfo<User>(userMapper.selectAll());
    }

    /**
     * User条件查询
     *
     * @param user
     * @return
     */
    @Override
    public List<User> findList(User user) {
        //构建查询条件
        Example example = createExample(user);
        //根据构建的条件查询数据
        return userMapper.selectByExample(example);
    }


    /**
     * User构建查询对象
     *
     * @param user
     * @return
     */
    public Example createExample(User user) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if (user != null) {
            // 用户名
            if (!StringUtils.isEmpty(user.getUsername())) {
                criteria.andLike("username", "%" + user.getUsername() + "%");
            }
            // 密码，加密存储
            if (!StringUtils.isEmpty(user.getPassword())) {
                criteria.andEqualTo("password", user.getPassword());
            }
            // 注册手机号
            if (!StringUtils.isEmpty(user.getPhone())) {
                criteria.andEqualTo("phone", user.getPhone());
            }
            // 注册邮箱
            if (!StringUtils.isEmpty(user.getEmail())) {
                criteria.andEqualTo("email", user.getEmail());
            }
            // 创建时间
            if (!StringUtils.isEmpty(user.getCreated())) {
                criteria.andEqualTo("created", user.getCreated());
            }
            // 修改时间
            if (!StringUtils.isEmpty(user.getUpdated())) {
                criteria.andEqualTo("updated", user.getUpdated());
            }
            // 会员来源：1:PC，2：H5，3：Android，4：IOS
            if (!StringUtils.isEmpty(user.getSourceType())) {
                criteria.andEqualTo("sourceType", user.getSourceType());
            }
            // 昵称
            if (!StringUtils.isEmpty(user.getNickName())) {
                criteria.andEqualTo("nickName", user.getNickName());
            }
            // 真实姓名
            if (!StringUtils.isEmpty(user.getName())) {
                criteria.andLike("name", "%" + user.getName() + "%");
            }
            // 使用状态（1正常 0非正常）
            if (!StringUtils.isEmpty(user.getStatus())) {
                criteria.andEqualTo("status", user.getStatus());
            }
            // 头像地址
            if (!StringUtils.isEmpty(user.getHeadPic())) {
                criteria.andEqualTo("headPic", user.getHeadPic());
            }
            // QQ号码
            if (!StringUtils.isEmpty(user.getQq())) {
                criteria.andEqualTo("qq", user.getQq());
            }
            // 手机是否验证 （0否  1是）
            if (!StringUtils.isEmpty(user.getIsMobileCheck())) {
                criteria.andEqualTo("isMobileCheck", user.getIsMobileCheck());
            }
            // 邮箱是否检测（0否  1是）
            if (!StringUtils.isEmpty(user.getIsEmailCheck())) {
                criteria.andEqualTo("isEmailCheck", user.getIsEmailCheck());
            }
            // 性别，1男，0女
            if (!StringUtils.isEmpty(user.getSex())) {
                criteria.andEqualTo("sex", user.getSex());
            }
            // 会员等级
            if (!StringUtils.isEmpty(user.getUserLevel())) {
                criteria.andEqualTo("userLevel", user.getUserLevel());
            }
            // 积分
            if (!StringUtils.isEmpty(user.getPoints())) {
                criteria.andEqualTo("points", user.getPoints());
            }
            // 经验值
            if (!StringUtils.isEmpty(user.getExperienceValue())) {
                criteria.andEqualTo("experienceValue", user.getExperienceValue());
            }
            // 出生年月日
            if (!StringUtils.isEmpty(user.getBirthday())) {
                criteria.andEqualTo("birthday", user.getBirthday());
            }
            // 最后登录时间
            if (!StringUtils.isEmpty(user.getLastLoginTime())) {
                criteria.andEqualTo("lastLoginTime", user.getLastLoginTime());
            }
            // 省份ID
            if (!StringUtils.isEmpty(user.getProvinceid())) {
                criteria.andEqualTo("provinceid", user.getProvinceid());
            }
            // 城市ID
            if (!StringUtils.isEmpty(user.getCityid())) {
                criteria.andEqualTo("cityid", user.getCityid());
            }
            // 地区ID
            if (!StringUtils.isEmpty(user.getAreaid())) {
                criteria.andEqualTo("areaid", user.getAreaid());
            }
        }
        return example;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改User
     *
     * @param user
     */
    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKey(user);
    }

    /**
     * 增加User
     *
     * @param user
     */
    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    /**
     * 根据ID查询User
     *
     * @param id
     * @return
     */
    @Override
    public User findById(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询User全部数据
     *
     * @return
     */
    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }
}
