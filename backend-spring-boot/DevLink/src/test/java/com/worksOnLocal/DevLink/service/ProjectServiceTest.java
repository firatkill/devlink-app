package com.worksOnLocal.DevLink.service;

import com.worksOnLocal.DevLink.dto.request.CreateProjectRequestDTO;
import com.worksOnLocal.DevLink.dto.request.UpdateProjectRequestDTO;
import com.worksOnLocal.DevLink.dto.response.CreateProjectResponseDTO;
import com.worksOnLocal.DevLink.dto.response.UpdateProjectResponseDTO;
import com.worksOnLocal.DevLink.entity.Profile;
import com.worksOnLocal.DevLink.entity.Project;
import com.worksOnLocal.DevLink.entity.User;
import com.worksOnLocal.DevLink.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private  ProjectRepository projectRepository;

    @Mock
    private  ProfileService profileService;

    @InjectMocks
    private ProjectService projectService;

    private User user;
    private Project project;
    private Profile profile;
    private UpdateProjectRequestDTO updateProjectRequestDTO;
    private CreateProjectRequestDTO createProjectRequestDTO;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setId(1L);
        user.setUsername("firatkil");
        user.setEmail("firatkil@gmail.com");

        profile = new Profile();
        profile.setId(1L);
        profile.setUser(user);
        profile.setProjects(new ArrayList<>());

        project = new Project();
        project.setId(1L);
        project.setName("name");
        project.setDescription("description");
        project.setProfile(profile);
        project.setHidden(false);
        project.setImage("image".getBytes());
        project.setTechsUsed(List.of("tech1","tech2"));
        project.setSourceCodeUrl("github.com/firatkil");
        project.setDeploymentUrl("vercel.app/firatkil");

        updateProjectRequestDTO= new UpdateProjectRequestDTO(project.getId(),new String(project.getImage()),project.getName(),project.getDescription(),project.getDeploymentUrl(),project.getSourceCodeUrl(),project.getTechsUsed(),project.isHidden());
        createProjectRequestDTO = new CreateProjectRequestDTO(new String(project.getImage()),project.getName(),project.getDescription(),project.getDeploymentUrl(),project.getSourceCodeUrl(),project.getTechsUsed(),project.isHidden());

    }

    @Test
    void updateProject_success(){
        Mockito.when(projectRepository.findByIdAndProfile_User_Id(updateProjectRequestDTO.id(), user.getId())).thenReturn(Optional.of(project));
        Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenReturn(project);

        UpdateProjectResponseDTO result = projectService.updateProject(updateProjectRequestDTO,user);

        assertNotNull(result);
        assertEquals(project.getName(),result.name());
        assertEquals(project.getDescription(),result.description());
        assertEquals(project.getTechsUsed(),result.techsUsed());
        assertEquals(new String(project.getImage()),result.image());
        assertEquals(project.getTechsUsed(),result.techsUsed());
        assertEquals(project.getSourceCodeUrl(),result.sourceCodeUrl());
        assertEquals(project.getTechsUsed(),result.techsUsed());
        assertEquals(project.getId(),result.id());

        Mockito.verify(projectRepository).save(project);

    }

    @Test
    void updateProject_throwsExceptionWhenProjectNotFound(){
        Mockito.when(projectRepository.findByIdAndProfile_User_Id(updateProjectRequestDTO.id(), user.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,()->projectService.updateProject(updateProjectRequestDTO,user));
    }

    @Test
    void deleteProject_success(){
        Mockito.when(projectRepository.findByIdAndProfile_User_Id(1L, user.getId())).thenReturn(Optional.of(project));

        projectService.deleteProject(1L, user);
        Mockito.verify(projectRepository).deleteById(1L);

    }

    @Test
    void deleteProject_throwsExceptionWhenProjectNotFound(){
        Mockito.when(projectRepository.findByIdAndProfile_User_Id(1L, user.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()->projectService.deleteProject(1L, user));
    }

    @Test
    void createProject_success(){
        Mockito.when(profileService.getProfileByUserId(user.getId())).thenReturn(Optional.of(profile));
        Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenReturn(project);

        CreateProjectResponseDTO result = projectService.createProject(createProjectRequestDTO,user);
        assertNotNull(result);
        assertEquals(project.getName(),result.name());
        assertEquals(project.getDescription(),result.description());
        assertEquals(project.getTechsUsed(),result.techsUsed());
        assertEquals(new String(project.getImage()),result.image());
        assertEquals(project.getTechsUsed(),result.techsUsed());
        assertEquals(project.getSourceCodeUrl(),result.sourceCodeUrl());
        assertEquals(project.getTechsUsed(),result.techsUsed());
        assertEquals(project.getId(),result.id());
        Mockito.verify(projectRepository).save(Mockito.any(Project.class));
    }

    @Test
    void createProject_throwsExceptionWhenProfileNotFound(){
        Mockito.when(profileService.getProfileByUserId(user.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()->projectService.createProject(createProjectRequestDTO,user));
    }
    @Test
    void createProject_throwsExceptionWhenProjectLimitExceeded(){
        profile.setProjects(Arrays.asList(new Project(),new Project(),new Project()));
        Mockito.when(profileService.getProfileByUserId(user.getId())).thenReturn(Optional.of(profile));

        assertThrows(IllegalArgumentException.class,()->projectService.createProject(createProjectRequestDTO,user));

    }

  
}