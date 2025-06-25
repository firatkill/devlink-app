package com.worksOnLocal.DevLink.entity;

import com.worksOnLocal.DevLink.enums.Platform;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="social_links")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SocialLink {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id",referencedColumnName = "id")
    private Profile profile;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    private String url;

    private boolean hidden=false;





}