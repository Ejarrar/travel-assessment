package com.afkl.travel.exercise.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="LOCATION")
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Column(name="PARENT_ID")
    private Integer parentCode;
    @Column(name="PARENT_TYPE")
    private String parentType;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private TranslationEntity translation;

}
