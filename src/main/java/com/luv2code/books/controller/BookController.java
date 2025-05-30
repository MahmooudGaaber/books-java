package com.luv2code.books.controller;

import com.luv2code.books.entity.Book;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController(){
        initializeBooks();
    }

    private void initializeBooks(){
        books.addAll(List.of(

                new Book(1,5,"Computer Science","Chad Derby","Computer Science Pro"),
                new Book(2,5,"Computer Science","Eric Roby","Java Spring Master"),
                new Book(3,5,"Math","Adil A.","Why 1+1 Rocks"),
                new Book(4,2,"Science","Bob B.","How Bears Hibernate"),
                new Book(5,3,"History","Curt C.","A Pirate Treasure"),
                new Book(6,1,"Math","Dan D.","Why 2+2 is Better")

                ));
    }

    @GetMapping("books")
    public List<Book> getBooks() {
        return books;
    }

    @GetMapping("books/{title}")
    public Book getBookByTitle(@PathVariable String title ){
       for (Book book : books){
           if (book.getTitle().equalsIgnoreCase(title)){
               return book;
           }
       }
        return null;
    }

    @GetMapping("books/{title}/ByStreamsAndLambda")
    public Book getBookByTitleWithStreamAndLambda(@PathVariable String title ){
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }



    @GetMapping("books/withQueryParameter")
    public List<Book> getBookByCategoryWithQueryParameter(@RequestParam(required = false) String category ){
        if (category == null ){
            return  books;
        }
        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @PostMapping("books")
    public void createBook(@RequestBody Book newBook ){
      boolean inNewBook = books.stream()
              .noneMatch(book -> book.getTitle().equalsIgnoreCase(newBook.getTitle()));
      if (inNewBook){
          books.add(newBook);
      }
    }


    @PutMapping("books/{title}")
    public void updateBook(
            @PathVariable String title,
            @RequestBody Book updatedBook
    ){

       for (int i=0 ; i< books.size() ; i++){
           if (books.get(i).getTitle().equalsIgnoreCase(title)){
               books.set(i,updatedBook);
               return;
           }
       }

    }

    @DeleteMapping("/api/books/{title}")
    public void deleteBook(@PathVariable String title){
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }


}
