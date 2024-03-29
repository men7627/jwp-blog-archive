package techcourse.myblog.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.reactive.function.BodyInserters;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class ArticleControllerTest extends ControllerTest {
    private static long currentArticleId = 1;

    @BeforeEach
    void setUp() {
        init();
    }

    @Test
    void index() {
        webTestClient.get().uri("/")
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    void 게시물_작성_페이지_이동_테스트() {
        webTestClient.get().uri("/writing")
            .header("Cookie", cookie)
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    void 게시물_작성_페이지_이동_비로그인_테스트() {
        webTestClient.get().uri("/writing")
            .exchange()
            .expectStatus().isFound();
    }

    @Test
    void 게시물_작성_요청_비로그인_테스트() {
        webTestClient.post().uri("/articles")
            .exchange()
            .expectStatus().isFound().expectHeader().valueMatches("location", "(.)*/login");
    }

    @Test
    void 게시물_조회_테스트() {
        String title = "jaemok";
        String contents = "naeyong";

        webTestClient.get().uri("/articles/" + currentArticleId)
            .header("Cookie", cookie)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .consumeWith(response -> {
                String body = new String(response.getResponseBody());
                assertThat(body.contains(title)).isTrue();
                //assertThat(body.contains(coverUrl)).isTrue();
                assertThat(body.contains(contents)).isTrue();
            });
    }

    @Test
    void 게시물_조회_비로그인_테스트() {
        String title = "jaemok";
        String contents = "naeyong";

        webTestClient.get().uri("/articles/" + currentArticleId)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .consumeWith(response -> {
                String body = new String(response.getResponseBody());
                assertThat(body.contains(title)).isTrue();
                //assertThat(body.contains(coverUrl)).isTrue();
                assertThat(body.contains(contents)).isTrue();
            });
    }

    @Test
    void 게시물_추가_요청_테스트() {
        String newTitle = "newTitle";
        String newCoverUrl = "newCoverUrl";
        String newContents = "newContents";

        webTestClient.post()
            .uri("/articles")
            .header("Cookie", cookie)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters
                .fromFormData("title", newTitle)
                .with("coverUrl", newCoverUrl)
                .with("contents", newContents))
            .exchange()
            .expectStatus().isFound()
            .expectBody()
            .consumeWith(response -> {
                webTestClient.get().uri(response.getResponseHeaders().getLocation())
                    .header("Cookie", cookie)
                    .exchange()
                    .expectBody()
                    .consumeWith(res -> {
                        String body = new String(res.getResponseBody());
                        assertThat(body.contains(newTitle)).isTrue();
                        //assertThat(body.contains(updatedCoverUrl)).isTrue();
                        assertThat(body.contains(newContents)).isTrue();
                    });
            });
    }

    @Test
    void 게시물_수정_페이지_이동_테스트() {
        webTestClient.get().uri("/articles/" + currentArticleId + "/edit")
            .header("Cookie", cookie)
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    void 게시물_수정_페이지_비로그인_이동_테스트() {
        webTestClient.get().uri("/articles/" + currentArticleId + "/edit")
            .exchange()
            .expectStatus().isFound().expectHeader().valueMatches("location", "(.)*/login");
    }

    @Test
    void 게시물_수정_요청_테스트() {
        String updatedTitle = "updatedTitle";
        String updatedCoverUrl = "updatedCoverUrl";
        String updatedContents = "updatedContents";

        webTestClient.put().uri("/articles/" + currentArticleId)
            .header("Cookie", cookie)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters
                .fromFormData("title", updatedTitle)
                .with("coverUrl", updatedCoverUrl)
                .with("contents", updatedContents))
            .exchange()
            .expectStatus().isFound()
            .expectBody()
            .consumeWith(response -> {
                webTestClient.get().uri(response.getResponseHeaders().getLocation())
                    .header("Cookie", cookie)
                    .exchange()
                    .expectBody()
                    .consumeWith(res -> {
                        String body = new String(res.getResponseBody());
                        assertThat(body.contains(updatedTitle)).isTrue();
                        //assertThat(body.contains(updatedCoverUrl)).isTrue();
                        assertThat(body.contains(updatedContents)).isTrue();
                    });
            });
    }

    @Test
    void 게시물_수정_요청_비로그인_테스트() {
        String updatedTitle = "updatedTitle";
        String updatedCoverUrl = "updatedCoverUrl";
        String updatedContents = "updatedContents";

        webTestClient.put().uri("/articles/" + currentArticleId)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters
                .fromFormData("title", updatedTitle)
                .with("coverUrl", updatedCoverUrl)
                .with("contents", updatedContents))
            .exchange()
            .expectStatus().isFound().expectHeader().valueMatches("location", "(.)*/login");
    }

    @Test
    void 게시물_삭제_요청_비로그인_테스트() {
        webTestClient.delete()
            .uri("/articles/" + 1)
            .exchange()
            .expectStatus().isFound().expectHeader().valueMatches("location", "(.)*/login");
    }

    @AfterEach
    void 게시물_삭제_요청_테스트() {
        webTestClient.delete()
            .uri("/articles/" + currentArticleId++)
            .exchange()
            .expectStatus().isFound();
    }
}
