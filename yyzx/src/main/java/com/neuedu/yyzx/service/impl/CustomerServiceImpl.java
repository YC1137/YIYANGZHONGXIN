package com.neuedu.yyzx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.yyzx.mapper.CustomerMapper;
import com.neuedu.yyzx.mapper.MenuMapper;
import com.neuedu.yyzx.mapper.RolemenuMapper;
import com.neuedu.yyzx.mapper.UserMapper;
import com.neuedu.yyzx.pojo.Customer;
import com.neuedu.yyzx.pojo.Menu;
import com.neuedu.yyzx.pojo.User;
import com.neuedu.yyzx.service.CustomerService;
import com.neuedu.yyzx.service.UserService;
import com.neuedu.yyzx.utils.ResultVo;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {



}
