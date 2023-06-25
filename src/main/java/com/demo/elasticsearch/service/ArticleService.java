package com.demo.elasticsearch.service;

import com.demo.elasticsearch.entity.Article;
import com.demo.elasticsearch.entity.Author;
import com.demo.elasticsearch.repository.ArticleRepository;
import com.demo.elasticsearch.web.request.ArticleRequest;
import com.demo.elasticsearch.web.response.ArticleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    public ArticleResponse create(ArticleRequest request) {
        Article article = Article.builder()
                .title(request.getTitle())
                .authors(Arrays.asList(new Author("thang")))
                .build();
        var saved = articleRepository.save(article);
        return ArticleResponse.fromEntity(saved);
    }

    public Page<ArticleResponse> getArticlesByName(String name) {
        var res = articleRepository.findByTitle(name, PageRequest.of(1, 10));
        return new PageImpl<>(res.getContent().stream().map(ArticleResponse::fromEntity).collect(Collectors.toList()));
    }

    public Page<ArticleResponse> getAllArticlePage() {
        var res = articleRepository.findAll(PageRequest.of(1, 10));
        return new PageImpl<ArticleResponse>(res.getContent().stream().map(ArticleResponse::fromEntity).collect(Collectors.toList()));
    }

    public List<ArticleResponse> getAllArticle() {
        var res = articleRepository.findAll(PageRequest.of(1, 10));
        return res.getContent().stream().map(ArticleResponse::fromEntity).collect(Collectors.toList());
    }
}
