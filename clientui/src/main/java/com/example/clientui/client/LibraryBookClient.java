package com.example.clientui.client;

import com.example.clientui.beans.BookBean;
import com.example.clientui.beans.CopyBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "library-book")
public interface LibraryBookClient {

    @GetMapping(value = "/library-book/books")
    List<BookBean> listBooks();

    @GetMapping(value = "/library-book/books/select/{id}")
    BookBean displayBook(@PathVariable int id);

    @GetMapping(value = "/library-book/copies/{book}")
    List<CopyBean> listCopies(@PathVariable int book);

    @GetMapping(value = "/library-book/copy/{id}")
    CopyBean selectCopy(@PathVariable int id);


    @GetMapping(value = "/library-book/books/search/page/{pageNo}")
    List<BookBean> getBooks(@PathVariable(value = "pageNo") int pageNo,
                            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                            @RequestParam(value = "word", required = false, defaultValue = "") String word);

    @GetMapping("/library-book/books/page/{pageNo}")
    List<BookBean> findBooksPaginated(@PathVariable(value = "pageNo") int pageNo,
                                      @RequestParam(value = "pageSize", defaultValue = "5") int pageSize);

    @GetMapping("/library-book/books/search")
    List<BookBean> searchBooks(@RequestParam(value = "word", required = false, defaultValue = "") String word);

}

