package com.example.library_management.service;

import com.example.library_management.Exception.NotFoundException;
import com.example.library_management.model.entity.Book;
import com.example.library_management.model.entity.Category;
import com.example.library_management.model.request.BookRequest;
import com.example.library_management.model.response.BookResponse;
import com.example.library_management.model.response.CategoryResponse;
import com.example.library_management.repository.BookRepository;
import com.example.library_management.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

//    get all books
    public List<Book> getAllBooks() {

        return bookRepository.findAll();
    }

//    get book details by bookId
    public BookResponse getBookDetailByBookId(Integer bookId) {
        bookRepository.findById(bookId);

        return BookResponse.builder()
                .message("Book is created successfully.")
                .status(HttpStatus.OK.value())
                .payload(bookRepository.findById(bookId))
                .build();

    }

//    get books by categoryId
    public List<Book> getBooksByCategoryId(Integer categoryId) {
        return bookRepository.findBooksByCategoryId(categoryId);
    }

//    create new book
    public BookResponse createBook(BookRequest bookRequest) {

        List<Category> categories = categoryRepository.findAllById(bookRequest.getCategoryId());

        var book = Book.builder()
                .bookTitle(bookRequest.getBookTitle())
                .author(bookRequest.getAuthor())
                .description(bookRequest.getDescription())
                .image(bookRequest.getImage())
                .categoryList(categories)
                .build();

        bookRepository.save(book);

        return BookResponse.builder()
                .message("Book is created successfully.")
                .status(HttpStatus.OK.value())
                .payload(book)
                .build();
    }

//    delete book by id
    public BookResponse deleteBookByBookId(Integer bookId) {
        bookRepository.deleteById(bookId);
        return BookResponse.builder()
                .message("Book is deleted successfully.")
                .status(HttpStatus.OK.value())
                .payload("No Book to show!")
                .build();
    }

//    update book by book id
    public BookResponse updateBookByBookId(Integer bookId, BookRequest bookRequest) {

        List<Category> categories = categoryRepository.findAllById(bookRequest.getCategoryId());

        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Not found book!"));
        existingBook.setBookTitle(bookRequest.getBookTitle());
        existingBook.setAuthor(bookRequest.getAuthor());
        existingBook.setImage(bookRequest.getImage());
        existingBook.setDescription(bookRequest.getDescription());
        existingBook.setCategoryList(categories);

        bookRepository.save(existingBook);

        return BookResponse.builder()
                .message("Book is updated successfully.")
                .status(HttpStatus.OK.value())
                .payload(existingBook)
                .build();
    }

//    check if the book existed
    public boolean isBookAlreadyExist(Integer bookId) {
        return bookRepository.findById(bookId).isPresent();
    }

}