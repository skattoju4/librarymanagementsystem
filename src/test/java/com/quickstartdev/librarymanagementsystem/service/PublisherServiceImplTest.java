package com.quickstartdev.librarymanagementsystem.service;

import com.knf.dev.librarymanagementsystem.Application;
import com.knf.dev.librarymanagementsystem.entity.Publisher;
import com.knf.dev.librarymanagementsystem.repository.PublisherRepository;
import com.knf.dev.librarymanagementsystem.service.PublisherService;
import com.knf.dev.librarymanagementsystem.service.impl.PublisherServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes= Application.class)
class PublisherServiceImplTest {
    static PublisherRepository publisherRepository;
    static PublisherService publisherService;
    @BeforeAll
    public static void init()
    {
        publisherRepository = mock(PublisherRepository.class);
        publisherService = new PublisherServiceImpl(publisherRepository);
    }

    @Test
    void testfindAllPublishers()
    {
        List<Publisher> expectedList = new ArrayList<>();
        expectedList.add(new Publisher());
        when(publisherRepository.findAll()).thenReturn(expectedList);
        List<Publisher> result = publisherService.findAllPublishers();
        assertEquals(expectedList,result);
    }

    @Test
    void testfindPublisherById()
    {
        Publisher expected = new Publisher();
        when(publisherRepository.findById(10L)).thenReturn(Optional.of(expected));
        Publisher result = publisherService.findPublisherById(10L);
        assertEquals(expected,result);
    }
    @Test
    void testupdatePublisher()
    {
        Publisher publisher = new Publisher();
        publisherService.updatePublisher(publisher);
        verify(publisherRepository, times(1)).save(any());
    }
}
