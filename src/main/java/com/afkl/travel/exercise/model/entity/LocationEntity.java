package com.afkl.travel.exercise.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name="LOCATION")
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;
    @Column(name="CODE")
    private String code;
    @Column(name="TYPE")
    private String type;
    @Column(name="LATITUDE")
    private Double latitude;
    @Column(name="LONGITUDE")
    private Double longitude;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private LocationEntity parentId;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "location", referencedColumnName = "id")
    private Set<TranslationEntity> translation;
}
