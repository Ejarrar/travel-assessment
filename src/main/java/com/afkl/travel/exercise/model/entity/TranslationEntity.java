package com.afkl.travel.exercise.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="TRANSLATION")
public class TranslationEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name="ID")
        private Long id;
        @Column(name="LOCATION")
        private Integer location;
        @Column(name="NAME")
        private String name;
        @Column(name="LANGUAGE")
        private String language;
        @Column(name="DESCRIPTION")
        private String description;
        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "id", referencedColumnName = "id")
        private LocationEntity locationEntity;


}
