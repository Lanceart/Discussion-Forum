package com.linqing.service;

import com.linqing.pojo.Blog;
import com.linqing.pojo.User;
import com.linqing.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface BlogService {

    Blog getAndConvert(Long id);
    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Blog saveBlog(Blog blog, User user);

    Blog updateBlog(Long id, Blog blog);
    void deleteBlog(Long id);

    Page<Blog> listBlog(Pageable pageable);

    List<Blog> listRecommenBlogTop(Integer size);

    Page<Blog> listBlog(Long tagId,Pageable pageable);

    Map<String , List<Blog>> archieveBlog();
}
