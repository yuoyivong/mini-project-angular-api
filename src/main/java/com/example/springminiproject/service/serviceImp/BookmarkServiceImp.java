package com.example.springminiproject.service.serviceImp;

import com.example.springminiproject.config.GlobalCurrentUserConfig;
import com.example.springminiproject.exception.NotFoundException;
import com.example.springminiproject.model.Article;
import com.example.springminiproject.model.Bookmark;
import com.example.springminiproject.model.BookmarkStatus;
import com.example.springminiproject.model.User;
import com.example.springminiproject.repository.ArticleRepository;
import com.example.springminiproject.repository.BookmarkRepository;
import com.example.springminiproject.repository.UserRepository;
import com.example.springminiproject.response.dto.ArticleDTO;
import com.example.springminiproject.service.BookmarkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookmarkServiceImp implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public BookmarkServiceImp(BookmarkRepository bookmarkRepository, ArticleRepository articleRepository, UserRepository userRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addBookmarkOnArticle(Long articleId, Long userId) {

        checkBookmarkOnArticle(userId, articleId);

        try {
            User user = userRepository.findById(userId).orElseThrow();
            Article article = articleRepository.findById(articleId).orElseThrow();

            Bookmark bookmark = new Bookmark();
            bookmark.setStatus(BookmarkStatus.TRUE);
            bookmark.setCreatedAt(LocalDateTime.now());
            bookmark.setUser(user);
            bookmark.setArticle(article);

            bookmarkRepository.save(bookmark);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }

    }

    @Override
    public void unBookmarkArticle(Long articleId, Long userId) {
        checkBookmarkOnArticle(userId, articleId);

        try {
            bookmarkRepository.updateBookmarkStatus(articleId, userId);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }

    }

    @Override
    public List<ArticleDTO> getAllBookmarkArticles(Long userId, int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Article> bookmarkPage = bookmarkRepository.getAllBookmarkArticles(pageable);

        return bookmarkPage.stream().map(Article::articleDTOResponse).collect(Collectors.toList());
    }


    private void checkBookmarkOnArticle(Long userId, Long articleId) {

        if(userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("User not found.");
        }

        if(articleRepository.findById(articleId).isEmpty()) {
            throw new NotFoundException("Cannot bookmark on not found article.");
        }

    }
}
