package com.example.library_management.controller;

import com.example.library_management.Exception.BlankFieldExceptionHandler;
import com.example.library_management.Exception.IfAlreadyExistValidationException;
import com.example.library_management.Exception.NotFoundException;
import com.example.library_management.model.entity.Book;
import com.example.library_management.model.entity.Category;
import com.example.library_management.model.request.BookRequest;
import com.example.library_management.model.response.BookResponse;
import com.example.library_management.service.BookService;
import com.example.library_management.service.CategoryService;
import com.example.library_management.service.UploadFileImageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")

public class BookController {

    private final BookService bookService;
    private final UploadFileImageService fileImageService;
    private final CategoryService categoryService;

    @GetMapping("/reader/allBooks")
    public List<Book> getAllBooks() {

        if (bookService.getAllBooks().isEmpty()) {
            throw new NotFoundException("There is no books yet.");
        }

        return bookService.getAllBooks();

    }

    //    get book detail by book id
    @GetMapping("/reader/book/{book_id}")
    public ResponseEntity<BookResponse> getBookDetailByBookId(@PathVariable Integer book_id) {

        if (!bookService.isBookAlreadyExist(book_id)) {
            throw new NotFoundException("Sorry! This book is not found.");
        }

        return ResponseEntity.ok(bookService.getBookDetailByBookId(book_id));
    }

    //    get books by categoryId
    @GetMapping("/reader/books/{category_id}")
    public ResponseEntity<List<Book>> getBooksByCategoryId(@PathVariable Integer category_id) {

//        if(!bookService.isBookAlreadyExist(category_id)) {
//            throw new NotFoundException("There is no any matched category id found.");
//        }

        if (bookService.getBooksByCategoryId(category_id).isEmpty()) {
            throw new NotFoundException("There is no any matched category id found.");
        }
//

        return ResponseEntity.ok(bookService.getBooksByCategoryId(category_id));
    }

    @PostMapping("/book")
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest bookRequest) {

//        if(!categoryService.isCategoryNameExist(bookRequest.getCategoryName())) {
//            throw new IfAlreadyExistValidationException("This category name doesn't exist yet. Please create a new one first.");
//        }

        if (bookRequest.getBookTitle().isBlank()) {
            throw new BlankFieldExceptionHandler("Book title cannot be blank");
        }

        if (bookRequest.getAuthor().isBlank()) {
            throw new BlankFieldExceptionHandler("Author cannot be blank");
        }

        if (bookRequest.getCategoryId() == null || categoryService.isCategoryListExist(bookRequest.getCategoryId())) {
            throw new BlankFieldExceptionHandler("Could not found the proper category.");
        }

        if (bookRequest.getDescription().isBlank()) {
            throw new BlankFieldExceptionHandler("Please fill in the description to make a better look.");
        }

        if (!fileImageService.isImageExist(bookRequest.getImage())) {
            throw new NotFoundException("The image doesn't exist in the system.");
        }

        return ResponseEntity.ok(bookService.createBook(bookRequest));

    }

    //    update book by book id
    @PutMapping("/book/{book_id}")
    public ResponseEntity<BookResponse> updateBookByBookId(@PathVariable Integer book_id,
                                                           @RequestBody BookRequest bookRequest) {

        if (!bookService.isBookAlreadyExist(book_id)) {
            throw new NotFoundException("You cannot update inexistent book.");
        }

        if (!fileImageService.isImageExist(bookRequest.getImage())) {
            throw new NotFoundException("The image doesn't exist in the system.");
        }

        if (bookRequest.getBookTitle().isBlank() ||
                bookRequest.getAuthor().isBlank() ||
                bookRequest.getDescription().isBlank()) {
            throw new BlankFieldExceptionHandler("The field cannot be blank");
        }

        if (bookRequest.getCategoryId() == null || categoryService.isCategoryListExist(bookRequest.getCategoryId())) {
            throw new BlankFieldExceptionHandler("Could not found the proper category.");
        }

        return ResponseEntity.ok(bookService.updateBookByBookId(book_id, bookRequest));
    }

    //    delete book by book id
    @DeleteMapping("/book/{book_id}")
    public ResponseEntity<BookResponse> deleteBookByBookId(@PathVariable Integer book_id) {

        if (!bookService.isBookAlreadyExist(book_id)) {
            throw new NotFoundException("You cannot delete inexistent book.");
        }

        return ResponseEntity.ok(bookService.deleteBookByBookId(book_id));
    }

    //    get categories by bookId
//    @GetMapping("/books/{bookId}/categories")
//    public ResponseEntity<List<Category>> getAllCategoriesByBookId(@PathVariable Integer bookId) {
//        return ResponseEntity.ok(categoryService.getAllCategoriesByBookId(bookId));
//    }

    //    get all books by categoryId
//    @GetMapping("/categories/{categoryId}/books")
//    public ResponseEntity<List<Book>> getAllBooksByCategoryId(@PathVariable Integer categoryId) {
//        return ResponseEntity.ok(bookService.getAllBooksByCategoryId(categoryId));
//    }

}