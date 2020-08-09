package com.mingwangxin.community.service;

import com.mingwangxin.community.dao.DiscussPostMapper;
import com.mingwangxin.community.entity.DiscussPost;
import com.mingwangxin.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class DiscussPostService {

    // 不可以跨层访问，因为不知道之后的代码会发送该改变名，而且跨层调用会很麻烦
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;


    // 查询到的 discussPosts， 会有一个外键，显示时不会显示外键，而是会显示用户名称
    // 一种是写 sql 时候关联查询用户，同时查询到用户数据
    // 可以针对每一个查询到的 discussPost 单独查一次 user，再组合在一起
    // 选择第二个方法， 后期使用 redis 缓存数据时会很方便，性能也高
    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    public int findDiscussPostRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }

    public int addDiscussPost(DiscussPost post) {
        if (post == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }

        // 转义HTML标记
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));
        // 过滤敏感词
        post.setTitle(sensitiveFilter.filter(post.getTitle()));
        post.setContent(sensitiveFilter.filter(post.getContent()));

        return discussPostMapper.insertDiscussPost(post);
    }

    public DiscussPost findDiscussPostById(int id) {
        return discussPostMapper.selectDiscussPostById(id);
    }




}
