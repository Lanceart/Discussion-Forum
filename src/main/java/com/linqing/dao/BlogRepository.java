package com.linqing.dao;

import com.linqing.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
    @Query("select b from Blog b where b.recommended = true and b.published = true ")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from Blog b where b.published = true ")
    Page<Blog> findAllNotScratch(Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Blog b set b.views  = b.views+1 where b.id=?1")
    int updateViews(Long id);


    @Query("select function('date_format',b.updateTime,'%Y')as year from Blog b group by function('date_format',b.updateTime,'%Y') order by function('date_format',b.updateTime,'%Y') desc ")
    List<String> findGroupYear();
}
