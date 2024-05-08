package com.account.yomankum.accountBook.domain.record;


import com.account.yomankum.accountBook.domain.tag.Tag;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RecordTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "record_id")
    private Record record;
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

}

