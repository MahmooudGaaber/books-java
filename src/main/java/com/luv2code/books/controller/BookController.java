package com.luv2code.books.controller;

import com.luv2code.books.entity.Book;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController(){
        initializeBooks();
    }

    private void initializeBooks(){
        books.addAll(List.of(
                new Book("title one","author two","science"),
                new Book("title two","author two","science"),
                new Book("title three","author three","history"),
                new Book("title four","author four","math"),
                new Book("title five","author five","math"),
                new Book("title six","author six","math")
        ));
    }

    @GetMapping("/api/books")
    public List<Book> getBooks() {
        return books;
    }

    @GetMapping("/api/books/{title}")
    public Book getBookByTitle(@PathVariable String title ){
       for (Book book : books){
           if (book.getTitle().equalsIgnoreCase(title)){
               return book;
           }
       }
        return null;
    }

    @GetMapping("/api/books/{title}/ByStreamsAndLambda")
    public Book getBookByTitleWithStreamAndLambda(@PathVariable String title ){
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }



    @GetMapping("/api/books/withQueryParameter")
    public List<Book> getBookByCategoryWithQueryParameter(@RequestParam(required = false) String category ){
        if (category == null ){
            return  books;
        }
        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @PostMapping("/api/books")
    public void createBook(@RequestBody Book newBook ){
      boolean inNewBook = books.stream()
              .noneMatch(book -> book.getTitle().equalsIgnoreCase(newBook.getTitle()));
      if (inNewBook){
          books.add(newBook);
      }
    }


}
