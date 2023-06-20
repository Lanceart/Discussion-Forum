package com.linqing.controller;

import com.linqing.pojo.Comment;
import com.linqing.pojo.User;
import com.linqing.service.BlogService;
import com.linqing.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        System.out.println("ahah oh!");
        return "blog :: commentList";
    }


    @PostMapping("/comments")
    public String post(Comment comment) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
//        User user = (User) session.getAttribute("user");
//        if (user != null) {
//            comment.setAvatar(user.getAvatar());
////            comment.setAdminComment(true);
//        } else {
//            comment.setAvatar(avatar);
//        }
        comment.setAvatar(avatar);
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }



}

