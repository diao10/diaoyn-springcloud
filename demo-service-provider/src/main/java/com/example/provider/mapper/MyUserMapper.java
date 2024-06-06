package com.example.provider.mapper;

import com.example.provider.entity.MyUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author diaoyn
 * @since 2024-06-06
 */
@Mapper
public interface MyUserMapper extends BaseMapper<MyUser> {

}
