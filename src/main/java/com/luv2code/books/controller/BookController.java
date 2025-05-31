package com.luv2code.books.controller;

import com.luv2code.books.entity.Book;
import com.luv2code.books.entity.RequestBook;
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

//    @GetMapping("books/{id}")
//    public Book getBookByIf(@PathVariable long   id ){
//        for (Book book : books){
//            if (book.getId()== id){
//                return book;
//            }
//        }
//        return null;
//    }

    @GetMapping("books/{id}")
    public Book getBookById(@PathVariable long   id ){
        return books.stream()
                .filter(book -> book.getId()== id)
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
    public void createBook(@RequestBody RequestBook requestBook ){
        long id = books.isEmpty() ? 1 :( books.getLast().getId() +1 );
        Book newBook = new Book(id,requestBook.getRating(), requestBook.getCategory(), requestBook.getAuthor(), requestBook.getTitle());
        books.add(newBook);
    }


    @PutMapping("books/{id}")
    public void updateBook(
            @PathVariable long id,
            @RequestBody Book updatedBook
    ){

       for (int i=0 ; i< books.size() ; i++){
           if (books.get(i).getId() == id){
               books.set(i,updatedBook);
               return;
           }
       }

    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable long id){
        books.removeIf(book -> book.getId() == id);
    }


}
