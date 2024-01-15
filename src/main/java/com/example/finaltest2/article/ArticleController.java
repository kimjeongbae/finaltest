package com.example.finaltest2.article;

import com.example.finaltest2.user.SiteUser;
import com.example.finaltest2.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RequestMapping("/article")
@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;


    @GetMapping("/list")
    public String list(Model model) {
        List<Article> articleList = this.articleService.getList();

        model.addAttribute("articleList", articleList);
        return "article_list";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create(ArticleForm articleForm) {

        return "article_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create(@RequestParam(value = "title") String title, @RequestParam(value = "content") String content, Principal principal) {

        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.articleService.create(title, content,siteUser);

        return "redirect:/article/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Article article = this.articleService.getArticle(id);

        model.addAttribute("article", article);
        return "article_detail";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(ArticleForm articleForm, @PathVariable("id") Integer id, Principal principal){
        Article article = this.articleService.getArticle(id);
        if(!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new RuntimeException("수정권한이 없습니다.");
        }
        articleForm.setTitle(article.getTitle());
        articleForm.setContent(article.getContent());
        return "article_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String articleModify(ArticleForm articleForm, @PathVariable("id") Integer id,Principal principal){
        Article article = this.articleService.getArticle(id);
        if(!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new RuntimeException("수정권한이 없습니다.");
        }
        this.articleService.modify(article, articleForm.getTitle(),articleForm.getContent());
        return String.format("redirect:/article/detail/%s",id);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Principal principal){
        Article article = this.articleService.getArticle(id);
        if(!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new RuntimeException("삭제권한이 없습니다.");
        }
        this.articleService.delete(article);
        return "redirect:/";
    }


}
