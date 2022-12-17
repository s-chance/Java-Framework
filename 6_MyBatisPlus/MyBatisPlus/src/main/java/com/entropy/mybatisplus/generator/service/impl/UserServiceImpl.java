package com.entropy.mybatisplus.generator.service.impl;

import com.entropy.mybatisplus.generator.entity.User;
import com.entropy.mybatisplus.generator.mapper.UserMapper;
import com.entropy.mybatisplus.generator.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author entropy
 * @since 2022-12-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
