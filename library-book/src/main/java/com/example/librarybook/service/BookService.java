package com.example.librarybook.service;

import com.example.librarybook.dao.BookDao;
import com.example.librarybook.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    public  List<Book> findAll(){
        return bookDao.findAll();
    }

    public Book findById(int id){
        return bookDao.findById(id);
    }

    public List<Book> findByString(String word){
        return bookDao.findByTitleOrAuthorOrGenre(word.toLowerCase());

    }

    public Page<Book> findPaginated(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return bookDao.findAll(pageable);
    }

    public Page<Book> findSearchPaginated(String word, int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return bookDao.findByTitleOrAuthorOrGenre(word.toLowerCase(), pageable);
    }

}
