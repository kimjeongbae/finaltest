package com.example.finaltest2.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/article")
@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;


    @GetMapping("/list")
    public String list(Model model) {
        List<Article> articleList = this.articleService.getList();

        model.addAttribute("articleList", articleList);
        return "article_list";
    }

    @GetMapping("/create")
    public String create(ArticleForm articleForm) {

        return "article_form";
    }

    @PostMapping("/create")
    public String create(@RequestParam(value = "title") String title, @RequestParam(value = "content") String content) {
        this.articleService.create(title, content);

        return "redirect:/article/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Article article = this.articleService.getArticle(id);

        model.addAttribute("article", article);
        return "article_detail";
    }

    @GetMapping("/modify/{id}")
    public String modify(ArticleForm articleForm, @PathVariable("id") Integer id){
        Article article = this.articleService.getArticle(id);

        articleForm.setTitle(article.getTitle());
        articleForm.setContent(article.getContent());
        return "article_form";
    }

    @PostMapping("/modify/{id}")
    public String articleModify(ArticleForm articleForm, @PathVariable("id") Integer id){
        Article article = this.articleService.getArticle(id);
        this.articleService.modify(article, articleForm.getTitle(),articleForm.getContent());
        return String.format("redirect:/article/detail/%s",id);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        Article article = this.articleService.getArticle(id);

        this.articleService.delete(article);
        return "redirect:/";
    }


}
