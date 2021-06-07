package com.cursor.library;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(
            value = "/books",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Book> createBook(
            @RequestBody final CreateBookDto createBookDto
    ) throws CreateBookException {
        final Book book = bookService.createBook(createBookDto.getName(), createBookDto.getAuthor(), createBookDto.getYear(), createBookDto.getGenre());
        return ResponseEntity.ok(book);
    }

    @PutMapping(
            value = "/books/update/{bookId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Book> updateBook(
            @PathVariable("bookId") String bookId,
            @RequestBody final CreateBookDto createBookDto
    ) throws CreateBookException {
        final Book book = bookService.updateBook(bookId, createBookDto.getName(), createBookDto.getAuthor(), createBookDto.getYear(), createBookDto.getGenre());
        return ResponseEntity.ok(book);
    }

    @GetMapping(
            value = "/books",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Book>> getBooks() {
        return new ResponseEntity<>(bookService.getAll(), HttpStatus.OK);
    }

    @GetMapping(
            value = "/books/pagination",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Book>> getPaginBooks(
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return new ResponseEntity<>(bookService.getPaginBook(limit, offset), HttpStatus.OK);
    }

    @GetMapping(
            value = "/books/sorted",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Book>> getSortedBooks(
            @RequestParam(value = "sort", required = false, defaultValue = "name") String sort) {
        return new ResponseEntity<>(bookService.getSortedBooks(sort), HttpStatus.OK);
    }

    @GetMapping(
            value = "/books/filter/{author}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Book>> getFilteredBooks(
            @PathVariable(value = "author", required = false) String author) {
        return author == null ?
                new ResponseEntity<>(bookService.getAll(), HttpStatus.OK) :
                new ResponseEntity<>(bookService.getByAuthor(author), HttpStatus.OK);
    }

    @GetMapping(
            value = "/books/{bookId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Book> getBook(@PathVariable("bookId") String bookId) {
        final Book result = bookService.findById(bookId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
