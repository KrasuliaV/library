package com.cursor.library.repository;

import com.cursor.library.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class BookRepo {

    private final Map<String, Book> books = new HashMap<>();

    public BookRepo() {
        final String id1 = UUID.randomUUID().toString();
        books.put(id1, new Book(id1, "1984", "George Orwell", 1949, "Novel"));
        final String id2 = UUID.randomUUID().toString();
        books.put(id2, new Book(id2, "To Kill a Mockingbird", "Harper Lee", 1960, "Novel"));
        final String id3 = UUID.randomUUID().toString();
        books.put(id3, new Book(id3, "Harry Potter and the Philosopher’s Stone", "J.K. Rowling", 1997, "Fantasy"));
        final String id4 = UUID.randomUUID().toString();
        books.put(id4, new Book(id4, "The Lord of the Rings", "J.R.R. Tolkien", 1964, "Adventure"));
        final String id5 = UUID.randomUUID().toString();
        books.put(id5, new Book(id5, "The Great Gatsby", "F. Scott Fitzgerald", 1925, "Novel"));
    }

    public List<Book> getAll() {
        return new ArrayList<>(books.values());
    }

    public Book saveBook(
            final String name,
            final String author,
            final Integer year,
            final String genre
    ) {
        final Book newBook = new Book(UUID.randomUUID().toString(), name, author, year, genre);
        return books.put(newBook.getId(), newBook);
    }

    public Book findById(String bookId) {
        return books.get(bookId);
    }

    public List<Book> getPaginationBooks(Integer limit, Integer offset) {
        final ArrayList<Book> listBook = new ArrayList<>(books.values());
        final int size = listBook.size();
        if (offset >= size) {
            return Collections.emptyList();
        } else if (limit > size || offset + limit > size) {
            return listBook.subList(offset, size);
        }
        return listBook.subList(offset, offset + limit);
    }

    public List<Book> getSortedBooks(String sort) {
        final ArrayList<Book> listBook = new ArrayList<>(books.values());
        if (sort.equals("name")) {
            listBook.sort(Comparator.comparing(Book::getName).thenComparing(Book::getYear));
        } else if (sort.equals("author")) {
            listBook.sort(Comparator.comparing(Book::getAuthor).thenComparing(Book::getYear));
        } else if (sort.equals("genre")) {
            listBook.sort(Comparator.comparing(Book::getGenre).thenComparing(Book::getYear));
        } else {
            listBook.sort(Comparator.comparing(Book::getYear));
        }
        return listBook;
    }

    public List<Book> getByAuthor(String author) {
        return books.values().stream()
                .filter(book -> book.getAuthor().equals(author))
                .collect(Collectors.toList());
    }

    public Book updateBook(String bookId, String name, String author, Integer year, String genre) {
        final Book newBook = new Book(bookId, name, author, year, genre);
        return books.put(newBook.getId(), newBook);
    }
}
