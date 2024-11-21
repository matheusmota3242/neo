package com.m2g2.neo.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private Set<PositionRecord> positionRecords;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<PositionRecord> getPositionRecords() {
        return positionRecords;
    }

    public void setPositionRecords(Set<PositionRecord> positionRecords) {
        this.positionRecords = positionRecords;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", positionRecords=" + positionRecords +
                '}';
    }
}
