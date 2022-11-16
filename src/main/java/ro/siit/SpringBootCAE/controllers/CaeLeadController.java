package ro.siit.SpringBootCAE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ro.siit.SpringBootCAE.models.*;
import ro.siit.SpringBootCAE.repositores.*;
import ro.siit.SpringBootCAE.services.IAuthenticationFacade;

import java.util.*;
import java.util.stream.Collectors;

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
    private ProjectTaskRepository projectTaskRepository;


    @Autowired
    private IAuthenticationFacade authenticationFacade;


    @GetMapping("/")
    public String getRequests(Model model){

        User user = getLoggedUser();

        List<Request> requestList =requestsRepository.findAll()
                .stream()
                .filter(request -> request.getProject().getProjectMembers().contains(user)).collect(Collectors.toList());


        List<ProjectTask> ProjectTask = projectTaskRepository.findAll();


        List<Request> listOfProjectTaskToRemove = new ArrayList<>();

        //check if the request is currently a project task
        for (Request request : requestList) {
            String requestName = request.getRequestName();
            for (ProjectTask projectTask : ProjectTask) {
                if (requestName.equals(projectTask.getRequestName())) {
                    listOfProjectTaskToRemove.add(request);
                }

            }
        }
        requestList.removeAll(listOfProjectTaskToRemove);


        List<Request> listOfRequestsWithAnswerToRemove = new ArrayList<>();

        //check if the request has a response from the logged user
        for (Request request : requestList) {
            UUID requestID = request.getRequestId();
            List<Response> responseList= request.getResponseList();
            for (Response response : responseList) {
                if (requestID.equals(response.getRequest().getRequestId()) && user.getUserId().equals(response.getUser().getUserId())) {
                    listOfRequestsWithAnswerToRemove.add(request);
                }

            }
        }

        requestList.removeAll(listOfRequestsWithAnswerToRemove);


        boolean displayRequests = true;

        model.addAttribute("requests", requestList);
        model.addAttribute("displayRequests", displayRequests);
        return "caeUI";
    }

    private User getLoggedUser() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return ((CustomUserDetails) authentication.getPrincipal()).getUser();
    }


    @GetMapping("/ProjectTasks")
    public String getProjectTasksInfo(Model model){
        User user = getLoggedUser();
        boolean displayProjectTasks = true;

        List<ProjectTask> projectTasks = projectTaskRepository.findAll();
        List<ProjectTask> projectTasksToDisplay = new ArrayList<>();
        for (ProjectTask task:projectTasks) {
            if(task.getOwner().equals(user)){
                if(task.isOngoing()) {
                    projectTasksToDisplay.add(task);
                }
            }
        }



        model.addAttribute("projectTasksToDisplay", projectTasksToDisplay);
        model.addAttribute("displayProjectTasks", displayProjectTasks);
        return "caeUI";
    }


    @GetMapping("/ProjectTasks/{id}")
    public RedirectView deleteTask(Model model, @PathVariable("id") UUID taskId) {

        projectTaskRepository.deleteById(taskId);

        return new RedirectView(" ");
    }




    /** set project task ongoing false*/
//    @PostMapping("/ProjectTasks/")
//    public RedirectView setProjectTaskStatus(Model model,
//                                    @RequestParam("projectTaskName") UUID id
//                                    ){
//
//        ProjectTask project = projectTaskRepository.getReferenceById(id);
//        project.setOngoing(false);
//        projectTaskRepository.saveAndFlush(project);
//
//        return new RedirectView("/caeLead/ProjectTasks");
//    }


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



    /** send to frontend only the projects where the User is member*/

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

    /** send to frontend only the projects tasks where the User is member*/

    @ModelAttribute("projectsTasks")
    public List<ProjectTask> displayProjectsTaskList(){

        List<ProjectTask> displayProjectsTaskList = new ArrayList<>();
        List<ProjectTask> projectList = projectTaskRepository.findAll();
        User user = getLoggedUser();

        for (ProjectTask project:projectList) {
            Set<User> users = project.getProject().getProjectMembers();
            for (User userToCheck: users) {
                if( userToCheck.getUserId().toString().equals(user.getUserId().toString())){
                    displayProjectsTaskList.add(project);
                }
            }
        }

        return displayProjectsTaskList;
    }



    /** send to frontend only the projects where the User is member*/

    @ModelAttribute("responseType")
    public Set<ResponseType> displayResponseType(){

        Set<ResponseType> listResponseType = new HashSet<>();
        listResponseType.add(ResponseType.APPROVED);
        listResponseType.add(ResponseType.REJECTED);

        return listResponseType;
    }





}
