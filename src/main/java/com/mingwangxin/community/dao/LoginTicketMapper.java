package com.mingwangxin.community.dao;

import com.mingwangxin.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
@Deprecated
public interface LoginTicketMapper {
    // 增删改方法返回的通常好似影响的行数
    // 主键不用，其自动生成
    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    // 开启主键自动生成，ketProperty 指定那个属性
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select id,user_id,ticket,status,expired ",
            "from login_ticket where ticket=#{ticket}"
    })
    // 查询方法，ticket 是核心字段，通过ticket获取服务端的状态
    LoginTicket selectByTicket(String ticket);

    // 动态 sql，\"转义
    @Update({
            "<script>",
            "update login_ticket set status=#{status} where ticket=#{ticket} ",
            "<if test=\"ticket!=null\"> ",
            "and 1=1 ",
            "</if>",
            "</script>"
    })
    // 项目中删除数据很少，通常是修改状态
    int updateStatus(String ticket, int status);
}
