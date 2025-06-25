package com.worksOnLocal.DevLink.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="external_links")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExternalLink {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "profile_id",referencedColumnName = "id")
    private Profile profile;

    @Column(columnDefinition = "bytea")
    private byte[] icon;

    private String title;

    private String url;

    private boolean hidden=false;


}