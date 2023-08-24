package com.example.library_management.controller;

import com.example.library_management.Exception.IfAlreadyExistValidationException;
import com.example.library_management.Exception.NotFoundException;
import com.example.library_management.model.entity.Book;
import com.example.library_management.model.entity.Category;
import com.example.library_management.model.request.BookRequest;
import com.example.library_management.model.response.BookResponse;
import com.example.library_management.service.BookService;
import com.example.library_management.service.CategoryService;
import com.example.library_management.service.UploadFileImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final UploadFileImageService fileImageService;
    @GetMapping("/book")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("/book")
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest bookRequest) {

//        if(!categoryService.isCategoryNameExist(bookRequest.getCategoryName())) {
//            throw new IfAlreadyExistValidationException("This category name doesn't exist yet. Please create a new one first.");
//        }


        if(!fileImageService.isImageExist(bookRequest.getImage())) {
            throw new NotFoundException("The image doesn't exist in the system.");
        }

        return ResponseEntity.ok(bookService.createBook(bookRequest));

    }

    //    get categories by bookId
    @GetMapping("/books/{bookId}/categories")
    public ResponseEntity<List<Category>> getAllCategoriesByBookId(@PathVariable Integer bookId) {
        return ResponseEntity.ok(categoryService.getAllCategoriesByBookId(bookId));
    }

    //    get all books by categoryId
    @GetMapping("/categories/{categoryId}/books")
    public ResponseEntity<List<Book>> getAllBooksByCategoryId(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(bookService.getAllBooksByCategoryId(categoryId));
    }

}