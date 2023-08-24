package com.example.library_management.service;

import com.example.library_management.model.entity.Book;
import com.example.library_management.model.entity.Category;
import com.example.library_management.model.request.BookRequest;
import com.example.library_management.model.response.BookResponse;
import com.example.library_management.model.response.CategoryResponse;
import com.example.library_management.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

//    get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

//    create new book
    public BookResponse createBook(BookRequest bookRequest) {
        var book = Book.builder()
                .bookTitle(bookRequest.getBookTitle())
                .author(bookRequest.getAuthor())
                .description(bookRequest.getDescription())
                .image(bookRequest.getImage())
                .build();
        bookRepository.save(book);

        return BookResponse.builder()
                .message("Book is created successfully.")
                .status(HttpStatus.OK.value())
                .build();
    }

}
