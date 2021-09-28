package com.example.librarybook.dao;

import com.example.librarybook.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;


@Repository
public interface BookDao extends JpaRepository<Book, Integer> {

    List<Book> findAll();

    Book findById(int id);

    @Query("select b from Book b where lower(b.title) like %:word% or lower(b.author)like %:word% or lower(b.genre)like %:word%")
    List <Book> findByTitleOrAuthorOrGenre(String word);

    @Query("select b from Book b where lower(b.title) like %:word% or lower(b.author)like %:word% or lower(b.genre)like %:word%")
    Page<Book> findByTitleOrAuthorOrGenre(String word, Pageable pageable);
}
