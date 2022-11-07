package ro.siit.SpringBootCAE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ro.siit.SpringBootCAE.models.*;
import ro.siit.SpringBootCAE.repositores.ProjectRepository;
import ro.siit.SpringBootCAE.repositores.RequestRepository;
import ro.siit.SpringBootCAE.repositores.ResponseRepository;
import ro.siit.SpringBootCAE.repositores.UserRepository;
import ro.siit.SpringBootCAE.services.IAuthenticationFacade;

import java.util.HashSet;
import java.util.List;
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
    private UserRepository userRepository;


    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @GetMapping("/")
    public String getRequests(Model model){
        User user = getLoggedUser();
        model.addAttribute("requests", requestsRepository.sortRequestsByUser(user));
        return "caeUI";
    }

    private User getLoggedUser() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return ((CustomUserDetails) authentication.getPrincipal()).getUser();
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
        User caeLead = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        Project project = new Project(UUID.randomUUID(),projectName);

        Set<User>projectsMembers= new HashSet<>();
        projectsMembers.add(caeLead);
        project.setProjectMembers(projectsMembers);

        projectsRepository.saveAndFlush(project);
        return new RedirectView("/caeLead/");
    }

    /** method to send User info to frontend */
    @ModelAttribute("user")
    public User  displayUser(){
        User user = getLoggedUser();
        return user;
    }


    /** method to send User info with cfd role to frontend */
    @ModelAttribute("cfdUsers")
    public List<User>   displayPossibleCfdMembers(){
        return userRepository.findByRole(Team.CFD);
    }

    /** method to send User info with validation role to frontend */
    @ModelAttribute("validationUsers")
    public List<User>   displayPossibleValidationMembers(){
        return userRepository.findByRole(Team.VALIDATION);
    }

    /** method to send User info with mechanical role to frontend */
    @ModelAttribute("mechanicalUsers")
    public List<User>   displayPossibleMechanicalMembers(){
        return userRepository.findByRole(Team.MECHANICAL);
    }

    /** method to send User info with nvh role to frontend */
    @ModelAttribute("mechanicalUsers")
    public List<User>   displayPossibleNvhMembers(){
        return userRepository.findByRole(Team.NVH);
    }


}
