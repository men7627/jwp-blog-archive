package techcourse.myblog.dto;

import lombok.Data;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.User;

public class ArticleDto {

    @Data
    public static class Create {
        private String title;
        private String coverUrl;
        private String contents;

        public Article toArticle(User author) {
            return Article.builder()
                    .title(title)
                    .coverUrl(coverUrl)
                    .contents(contents)
                    .author(author)
                    .build();
        }
    }

    @Data
    public static class Update {
        private String title;
        private String coverUrl;
        private String contents;

        public Article toArticle(long articleId) {
            return Article.builder()
                    .id(articleId)
                    .title(title)
                    .coverUrl(coverUrl)
                    .contents(contents)
                    .build();
        }
    }

    @Data
    public static class Response {
        private long id;
        private String title;
        private String coverUrl;
        private String contents;
    }
}
