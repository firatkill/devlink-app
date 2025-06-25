package com.worksOnLocal.DevLink.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne
    @JoinColumn(name = "profile_id",referencedColumnName = "id")
    private Profile profile;

    @Column(columnDefinition = "bytea")
    private byte[] image;

    private String name;

    private String description;

    private String deploymentUrl;

    private String sourceCodeUrl;

    private List<String> techsUsed;

    private boolean hidden=false;

}