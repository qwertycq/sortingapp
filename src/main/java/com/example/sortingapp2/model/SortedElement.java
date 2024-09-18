package com.example.sortingapp2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sorted_elements")
public class SortedElement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "element_value")
    private int elementValue;

    private Long sortId;

    public SortedElement() {
    }

    public SortedElement(int elementValue, Long sortId) {
        this.elementValue = elementValue;
        this.sortId = sortId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getElementValue() {
        return elementValue;
    }

    public void setElementValue(int elementValue) {
        this.elementValue = elementValue;
    }

    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }
}
