package com.example.librarybook.controller;

import com.example.librarybook.exception.CopyNotFoundException;
import com.example.librarybook.model.Copy;
import com.example.librarybook.service.CopyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CopyRestController {

    private Logger logger = LoggerFactory.getLogger(CopyRestController.class);

    @Autowired
    private CopyService copyService;

    //list of copies for one book
    //exception : not found ou empty
    @GetMapping(value = "/copies/{book}")
    @PreAuthorize("isAuthenticated()")
    public List<Copy> listCopies(@PathVariable int book) {
        List<Copy> copies = copyService.findByBook(book);
        logger.info(copies.toString());
        return copies;
    }

    // select one copy
    @GetMapping(value = "/copy/{id}")
    @PreAuthorize("isAuthenticated()")
    public Copy selectCopy(@PathVariable int id)    {
        Copy copy = copyService.findById(id);
       if (copy==null) throw new CopyNotFoundException ("copy not found");
        return copy;
    }

}
