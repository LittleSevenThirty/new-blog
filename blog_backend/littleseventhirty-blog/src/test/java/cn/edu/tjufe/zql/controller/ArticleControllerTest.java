package cn.edu.tjufe.zql.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class ArticleControllerTest {
    public ArticleController articleController = new ArticleController();

    @Test
    void getHotArticles() {
        articleController.hotArticles();
    }
}