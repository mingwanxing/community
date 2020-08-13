package com.mingwangxin.community.dao;

import com.mingwangxin.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
// 写了注解之后，才可以被容器扫描装配
public interface DiscussPostMapper {


    // 查询的是多条帖子，返回List，个人主页有一个我发过的帖子功能，这个功能就可以调用这个方法，传入用户id
    // 考虑将来的功能，选择这个方法
    // 是一个动态 sql， 有时要拼 用户 id，有时 不要
    // 需要考虑到分页 ， mysql limit关键字后面跟两个参数，一个是这页的起始行行号，这页最多显示多少条数据
    // 可以分多少页。 总数据 / 每页显示多少数据。 后者是可以固化的
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    // 参数前加 @Param("userId"）注解， 给参数起别名
    // 如果需要动态的拼一个条件，并且这个方法有且仅有一个条件，这个参数之前必须起别名
    int selectDiscussPostRows(@Param("userId") int userId);

    // 增加帖子的方法
    int insertDiscussPost(DiscussPost discussPost);

    // 通过 id 查询帖子详情
    DiscussPost selectDiscussPostById(int id);

    int updateCommentCount(int id, int commentCount);




}
