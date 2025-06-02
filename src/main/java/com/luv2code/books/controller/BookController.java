package com.luv2code.books.controller;

import com.luv2code.books.entity.Book;
import com.luv2code.books.entity.RequestBook;
import com.luv2code.books.exception.BookNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Books Rest API Endpoints ",description = "Operation Related to books")
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

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get All Books")
    @GetMapping("books")
    public List<Book> getBooks() {
        return books;
    }


    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get Books By Id")
    @GetMapping("books/{id}")
    public Book getBookById(@PathVariable @Min(value = 1) long   id ){

        return books.stream()
                .filter(book -> book.getId()== id)
                .findFirst()
                .orElseThrow(
                        ()-> new BookNotFoundException("Book Not Found -"+ id )
                );

    }




    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create New Book")
    @PostMapping("books")
    public void createBook( @Valid @RequestBody RequestBook requestBook ){
        long id = books.isEmpty() ? 1 :( books.getLast().getId() +1 );
        books.add(convertRequestToBook(id,requestBook));
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update Book")
    @PutMapping("books/{id}")
    public Book updateBook(
            @PathVariable  @Min(value = 1) long id,
            @Valid @RequestBody RequestBook requestBook
    ){

       for (int i=0 ; i< books.size() ; i++){
           if (books.get(i).getId() == id){
               Book updatedBook = convertRequestToBook(id,requestBook);
               books.set(i,updatedBook);
               return updatedBook;
           }
       }

      throw new BookNotFoundException("Book Not Found - "+ id );

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update Book By Id")
    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable  @Min(value = 1) long id){
        books.stream()
                .filter(book -> book.getId()== id)
                .findFirst()
                .orElseThrow(
                        ()-> new BookNotFoundException("Book Not Found -"+ id )
                );
        books.removeIf(book -> book.getId() == id);
    }


    public Book convertRequestToBook(long id , RequestBook book){
        return  new Book(id,book.getRating(), book.getCategory(), book.getAuthor(), book.getTitle());
    }

}
