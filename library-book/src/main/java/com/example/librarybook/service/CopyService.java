package com.example.librarybook.service;

import com.example.librarybook.dao.CopyDao;
import com.example.librarybook.model.Copy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopyService {

    @Autowired
    private CopyDao copyDao;

    public List<Copy> findByBook(int book){
        return copyDao.findByBook(book);
    }

    public Copy findById(int id){
        return copyDao.findById(id);
    }
}
