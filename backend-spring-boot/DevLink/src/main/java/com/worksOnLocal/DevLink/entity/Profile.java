package com.worksOnLocal.DevLink.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name="profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    private String displayName;

    private String headerTitle;

    private String headerDescription;


    @Column(columnDefinition = "bytea")
    private byte[] image;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER,mappedBy = "profile")
    private List<SocialLink> socialLinks;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER,mappedBy = "profile")
    private List<ExternalLink> externalLinks;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER,mappedBy = "profile")
    private List<Project> projects;


    @UpdateTimestamp
    private Instant updatedAt;

    private boolean hidden=false;




}