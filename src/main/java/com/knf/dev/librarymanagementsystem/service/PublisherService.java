package com.knf.dev.librarymanagementsystem.service;

import com.knf.dev.librarymanagementsystem.dto.PublisherDTO;
import com.knf.dev.librarymanagementsystem.entity.Publisher;

import java.util.List;

public interface PublisherService {
    List<Publisher> findAllPublishers();

    Publisher findPublisherById(Long id);

    void createPublisher(PublisherDTO publisherDTO);

    void updatePublisher(PublisherDTO publisherDTO);

    void deletePublisher(Long id);

    PublisherDTO findPublisherDTOById(Long id);
}

