package LikelionSummerStudy.blogSummer.service;


import LikelionSummerStudy.blogSummer.domain.Article;
import LikelionSummerStudy.blogSummer.dto.request.AddArticleRequest;
import LikelionSummerStudy.blogSummer.dto.request.UpdateArticleRequest;
import LikelionSummerStudy.blogSummer.dto.response.ArticleResponse;
import LikelionSummerStudy.blogSummer.repository.BlogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request){
        return blogRepository.save(request.toEntity());
    }

    public List<ArticleResponse> findAll(){

        /**
         * .stream(): 리스트를 스트림(Stream) 으로 변환 (순차 처리 가능하게 함)
         * .map(ArticleResponse::new): 각 Article 객체를 ArticleResponse 객체로 변환
         * stream()으로 흘러온 각 Article 객체에 대해 new ArticleResponse(article)을 수행
         * .toList(): 변환된 요소들을 다시 리스트로 수집
         * List<Article> → List<ArticleResponse>로 변환하는 것이 목적!
         **/

        return blogRepository.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();
    }

    public Article findById(long id){
        //값이 없을 때 던질 예외를 람다식으로 정의
        return blogRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found: "+id));
    }

    public void delete(Long id){
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(),request.getContent());

        return article;
    }
}
