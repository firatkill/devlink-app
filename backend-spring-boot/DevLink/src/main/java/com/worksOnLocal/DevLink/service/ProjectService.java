package com.worksOnLocal.DevLink.service;

import com.worksOnLocal.DevLink.dto.request.CreateProjectRequestDTO;
import com.worksOnLocal.DevLink.dto.request.UpdateProjectRequestDTO;
import com.worksOnLocal.DevLink.dto.response.CreateProjectResponseDTO;
import com.worksOnLocal.DevLink.dto.response.UpdateProjectResponseDTO;
import com.worksOnLocal.DevLink.entity.Profile;
import com.worksOnLocal.DevLink.entity.Project;
import com.worksOnLocal.DevLink.entity.User;
import com.worksOnLocal.DevLink.repository.ProfileRepository;
import com.worksOnLocal.DevLink.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;

    public UpdateProjectResponseDTO updateProject(UpdateProjectRequestDTO updateProjectRequestDTO, User user) {
        Project project = projectRepository.findByIdAndProfile_User_Id(updateProjectRequestDTO.id(),user.getId()).orElseThrow(()->new EntityNotFoundException("Güncellenecek Proje bulunamadı."));


        project.setTechsUsed(updateProjectRequestDTO.techsUsed());
        project.setName(updateProjectRequestDTO.name());
        project.setHidden(updateProjectRequestDTO.hidden());
        project.setDeploymentUrl(updateProjectRequestDTO.deploymentUrl());
        project.setSourceCodeUrl(updateProjectRequestDTO.sourceCodeUrl());
        project.setImage(updateProjectRequestDTO.image() != null ? updateProjectRequestDTO.image().getBytes():null);
        project.setDescription(updateProjectRequestDTO.description());

        Project savedProject=projectRepository.save(project);

        return new UpdateProjectResponseDTO(savedProject.getId(), savedProject.getImage()!=null? new String(savedProject.getImage()):null, savedProject.getName(),
                savedProject.getDescription(), savedProject.getDeploymentUrl(), savedProject.getSourceCodeUrl(), savedProject.getTechsUsed(), savedProject.isHidden()
                );


    }


    public void deleteProject(Long projectId, User user) {
        Project project = projectRepository.findByIdAndProfile_User_Id(projectId, user.getId()).orElseThrow(()->new EntityNotFoundException("Silinecek Proje bulunamadı."));
        projectRepository.deleteById(project.getId());

    }

    public CreateProjectResponseDTO createProject(CreateProjectRequestDTO createProjectRequestDTO, User user) {
       Profile profile= profileService.getProfileByUserId(user.getId()).orElseThrow(()->new EntityNotFoundException("Profil bulunamadı."));

       if(profile.getProjects().size()>=3) throw new IllegalArgumentException("Profile Maksimum 3 proje eklenebilir.");

       Project project = new Project();
       project.setProfile(profile);
       project.setTechsUsed(createProjectRequestDTO.techsUsed());
       project.setName(createProjectRequestDTO.name());
       project.setHidden(createProjectRequestDTO.hidden());
       project.setDeploymentUrl(createProjectRequestDTO.deploymentUrl());
       project.setSourceCodeUrl(createProjectRequestDTO.sourceCodeUrl());
       project.setImage(createProjectRequestDTO.image() != null ? createProjectRequestDTO.image().getBytes():null);
       project.setDescription(createProjectRequestDTO.description());

       Project savedProject=projectRepository.save(project);
       return new CreateProjectResponseDTO(savedProject.getId(), savedProject.getImage()!=null? new String(savedProject.getImage()):null, savedProject.getName(), savedProject.getDescription(), savedProject.getDeploymentUrl(), savedProject.getSourceCodeUrl(), savedProject.getTechsUsed(),savedProject.isHidden());





    }

    public Long getViewsCount(User user) {
         Profile profile=profileRepository.findByUser_Id(user.getId()).orElseThrow(()->new EntityNotFoundException("Profil Bulunamadı"));
        return profile.getViews();
    }
}
