package com.example.sortingapp2.repository;

import com.example.sortingapp2.model.SortedElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SortedElementRepository extends JpaRepository<SortedElement, Long> {
    List<SortedElement> findBySortId(Long sortId);
}

