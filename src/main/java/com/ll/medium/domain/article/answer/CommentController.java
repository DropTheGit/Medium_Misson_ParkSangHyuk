package com.ll.medium.domain.article.answer;

import com.ll.medium.domain.article.article.entity.Article;
import com.ll.medium.domain.article.article.service.ArticleService;
import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final ArticleService articleService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post/{id}/comment/write")
    public String addComment(Model model, @PathVariable("id") Long id, @Valid CommentForm commentForm, BindingResult bindingResult,
                             Principal principal){
        Article article = this.articleService.getArticle(id);
        Member member = this.memberService.getMember(principal.getName());
        if(bindingResult.hasErrors()){
            model.addAttribute("article", article);
            return "domain/article/article_detail";
        }
        this.commentService.createAnswer(commentForm.getContent(), article, member);
        return String.format("redirect:/post/%d", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("post/{id}/comment/{cid}/modify")
    public String modifyComment(Model model, CommentForm commentForm, @PathVariable("id") Long articleId,
                                @PathVariable("cid") Long commentId, Principal principal){
        Comment comment = this.commentService.getComment(commentId);
        if(!comment.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        commentForm.setContent(comment.getContent());
        return "domain/article/comment_modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("post/{id}/comment/{cid}/modify")
    public String modifyComment(@Valid CommentForm commentForm, BindingResult bindingReusult,
                                Principal principal,
                                @PathVariable("id") Long articleId, @PathVariable("cid") Long commentId){
        if(bindingReusult.hasErrors()){
            return "domain/article/comment_modify";
        }
        Comment comment = this.commentService.getComment(commentId);
        if(!comment.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한 없어요.");
        }
        this.commentService.modifyComment(comment, commentForm.getContent());
        return String.format("redirect:/post/%d", articleId);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("post/{articleId}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable("articleId") Long articleId, @PathVariable("commentId") Long commnetId,
                                Principal principal){
        Comment comment = this.commentService.getComment(commnetId);
        if(!comment.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없어요.");
        }
        this.commentService.deleteComment(comment);
        return String.format("redirect:/post/%d", articleId);
    }

}
