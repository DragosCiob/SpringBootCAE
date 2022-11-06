package ro.siit.SpringBootCAE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ro.siit.SpringBootCAE.models.CustomUserDetails;
import ro.siit.SpringBootCAE.models.Project;
import ro.siit.SpringBootCAE.models.Request;
import ro.siit.SpringBootCAE.models.User;
import ro.siit.SpringBootCAE.repositores.ProjectRepository;
import ro.siit.SpringBootCAE.repositores.RequestRepository;
import ro.siit.SpringBootCAE.repositores.ResponseRepository;
import ro.siit.SpringBootCAE.services.IAuthenticationFacade;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/caeLead")
public class CaeLeadController {

    @Autowired
    private ProjectRepository projectsRepository;

    @Autowired
    private RequestRepository requestsRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @GetMapping("/")
    public String getRequests(Model model){
        Authentication authentication = authenticationFacade.getAuthentication();
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        model.addAttribute("requests", requestsRepository.sortRequestsByUser(user));
        return "caeUI";
    }


    @GetMapping("/createProject")
    public String createProjectForm(Model model) {
        return "/createProjectForm";
    }

    /** only USER with CAELEAD role can create a project*/
    @PostMapping("/createProject")
    public RedirectView makeRequest(Model model,
                                    @RequestParam("project_name") String projectName
                                    ){

        Authentication authentication = authenticationFacade.getAuthentication();
        Project project = new Project(UUID.randomUUID(),projectName);
        User caeLead = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        Set<User>projectsMembers= new HashSet<>();
        projectsMembers.add(caeLead);
        project.setProjectMembers(projectsMembers);

        projectsRepository.saveAndFlush(project);
        return new RedirectView("/caeLead/");
    }

    @ModelAttribute("user")
    public User  displayUser(){
        Authentication authentication = authenticationFacade.getAuthentication();
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        return user;
    }

    //method to create project team
}
