package com.quickstartdev.librarymanagementsystem.Contoller;

import com.knf.dev.librarymanagementsystem.Application;
import com.knf.dev.librarymanagementsystem.controller.AuthorController;
import com.knf.dev.librarymanagementsystem.controller.BookController;
import com.knf.dev.librarymanagementsystem.entity.Author;
import com.knf.dev.librarymanagementsystem.entity.Book;
import com.knf.dev.librarymanagementsystem.service.AuthorService;
import com.knf.dev.librarymanagementsystem.service.BookService;
import com.knf.dev.librarymanagementsystem.service.CategoryService;
import com.knf.dev.librarymanagementsystem.service.PublisherService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes= Application.class)
class BookControllerTest {
    @Autowired
    staticg
    PublisherService publisherService;
    @Autowired
    static
    CategoryService categoryService;
    @Autowired
    static
    AuthorService authorService;
    @Autowired
    static
    BookService bookService;
    static Model model;
    static BookController controller;
    static BindingResult bindingResult;

    @BeforeAll
    private static void init()
    {
        publisherService = mock(PublisherService.class);
        categoryService = mock(CategoryService.class);
        bookService = mock(BookService.class);
        authorService = mock(AuthorService.class);

        model = new ConcurrentModel();
        controller= new BookController(publisherService,categoryService,bookService,authorService);
        bindingResult = mock(BindingResult.class);
    }
    @Test
    void testFindAllBooks() {

        int expectedPage = 1;
        int expectedSize = 10;
        Page<Book> mockPage = mock(Page.class);

        when(bookService.findPaginated(any())).thenReturn(mockPage);
        when(mockPage.getTotalPages()).thenReturn(2);

        String result = controller.findAllBooks(model, Optional.of(expectedPage), Optional.of(expectedSize));
        assertEquals("list-books", result);
        assertEquals(List.of(1,2),model.getAttribute("pageNumbers"));
    }
}
