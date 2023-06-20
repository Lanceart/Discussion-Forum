package com.linqing.service;

import com.linqing.dao.BlogRepository;
import com.linqing.pojo.Blog;
import com.linqing.pojo.Type;
import com.linqing.pojo.User;
import com.linqing.util.MarkdownUtils;
import com.linqing.util.MyBeanUtils;
import com.linqing.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImp implements BlogService{

    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {

        Blog blog = blogRepository.findById(id).orElse(null);

        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        blogRepository.updateViews(id);
        return b;
    }

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {

        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(!"".equals(blog.getTitle()) && blog.getTitle()!=null){
                    predicates.add(criteriaBuilder.like(root.get("title"), "%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId()!=null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                System.out.println("wow is recommaned???" + blog.getTitle() + "  Type:  "+blog.getTypeId());
                if(blog.isRecommended()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommended"),blog.isRecommended()));
                }
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog, User user) {
        if(blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
            blog.setUser(user);

        }else {
            blog.setUpdateTime(new Date());
        }

        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b  = blogRepository.getOne(id);
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }


    @Override
    public Page<Blog> listBlog(Pageable pageable){
        return blogRepository.findAllNotScratch(pageable);
    }

    @Override
    public List<Blog> listRecommenBlogTop(Integer size) {
        Pageable pageable = PageRequest.of(0,size, Sort.by(Sort.Direction.DESC,"updateTime"));
        return blogRepository.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tag");
                return criteriaBuilder.equal(join.get("id"),tagId);
            }
        },pageable );
    }

    @Override
    public Map<String, List<Blog>> archieveBlog() {
        List<String> years = blogRepository.findGroupYear();
        return null;
    }
}
