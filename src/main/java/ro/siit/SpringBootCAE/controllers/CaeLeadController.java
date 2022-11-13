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

import java.util.*;

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
                                    @RequestParam("project_name") String projectName,
                                    @RequestParam("projectMembers")  Set<User> projectMembers){

        Authentication authentication = authenticationFacade.getAuthentication();
        User caeLead = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        Project project = new Project(UUID.randomUUID(),projectName,projectMembers);

        Set<User>projectsMembers=project.getProjectMembers();
        projectsMembers.add(caeLead);
        project.setProjectMembers(projectsMembers);

        projectsRepository.saveAndFlush(project);
        return new RedirectView("/caeLead/add");
    }


    @GetMapping("/add")
    public String addRequestForm(Model model) {
        return "/addFormCae";
    }



    @PostMapping("/add")
    public RedirectView makeRequest(Model model,
                                    @RequestParam("request_name") String name,
                                    @RequestParam("request_description") String description,
                                    @RequestParam("project_id") Project project
    ){


            Authentication authentication = authenticationFacade.getAuthentication();
            Request addedRequest = new Request(UUID.randomUUID(), name, description, project);
            addedRequest.setOwner(((CustomUserDetails) authentication.getPrincipal()).getUser());
            addedRequest.setIndex(generateIndex(project));
            //set the response of the owner
            List<Response>responseList = new ArrayList<>();
            addedRequest.setResponseList(responseList);
            responseList.add(setOwnerResponse(addedRequest));
            requestsRepository.saveAndFlush(addedRequest);

        return new RedirectView("/caeLead/");
    }

    //ser user that own request the response
    private Response setOwnerResponse(Request request){
        Response ownerResponse =new Response(UUID.randomUUID(), ResponseType.APPROVED, "APPROVED",request);
        ownerResponse.setUser(getLoggedUser());
        return ownerResponse;
    }

    //set index method
    private  Double generateIndex(Project project){
        List<Request>requestListOfProject = requestsRepository.findRequestsByProject(project);
        if(requestListOfProject.size()==0){
            return 1.0;
        }else {
            return requestListOfProject.size()+1.0;
        }
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
    @ModelAttribute("nvhUsers")
    public List<User>   displayPossibleNvhMembers(){
        return userRepository.findByRole(Team.NVH);
    }

    @ModelAttribute("projects")
    public List<Project> displayProjectsList(){

        List<Project> projectListToDisplay = new ArrayList<>();
        List<Project> projectList = projectsRepository.findAll();
        User user = getLoggedUser();

        for (Project project:projectList) {
            Set<User> users = project.getProjectMembers();
            for (User userToCheck: users) {
                if( userToCheck.getUserId().toString().equals(user.getUserId().toString())){
                    projectListToDisplay.add(project);
                }
            }
        }

        return projectListToDisplay;
    }


}
