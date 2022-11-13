package ro.siit.SpringBootCAE.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ro.siit.SpringBootCAE.models.*;
import ro.siit.SpringBootCAE.repositores.ProjectRepository;
import ro.siit.SpringBootCAE.repositores.ProjectTaskRepository;
import ro.siit.SpringBootCAE.repositores.RequestRepository;
import ro.siit.SpringBootCAE.repositores.ResponseRepository;
import ro.siit.SpringBootCAE.services.IAuthenticationFacade;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProjectRepository projectsRepository;
    @Autowired
    private RequestRepository requestsRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;



    @GetMapping("/")
    public String getRequests(Model model){

        User user = getLoggedUser();
        List<Request> requestList =requestsRepository.sortRequestsByUser(user);
        List<Response> responseList =responseRepository.findAll();

        List<Request> requestsToDisplay =requestsRepository.sortRequestsByUser(user);
      //check for project list
        List<ProjectTask> ProjectTask = projectTaskRepository.findAll();
        for (Request request: requestList) {
            String requestName =request.getRequestName();
            for (ProjectTask projectTask:ProjectTask) {
                if(requestName.equals(projectTask.getRequestName())){
                    requestsToDisplay.remove(request);
                }

            }
        }



// might be not effective method
        for (Request request: requestList) {
           UUID requestID = request.getRequestId();
            for (Response response:responseList) {
                if(requestID.equals(response.getRequest().getRequestId()) && user.getUserId().equals(response.getUser().getUserId()) ){
                    requestsToDisplay.remove(request);

                }

            }
        }
        model.addAttribute("requests", requestsToDisplay);

     return "UI";
    }

    @GetMapping("/myRequests")
    public String getUserRequests(Model model){
        User user = getLoggedUser();
        model.addAttribute("requests", requestsRepository.findRequestsByUser(user));
        return "UI";
    }

    public User getLoggedUser() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return ((CustomUserDetails) authentication.getPrincipal()).getUser();
    }



    @GetMapping("/add")
    public String addRequestForm(Model model) {
        return "/addForm";
    }



    @PostMapping("/add")
    public RedirectView makeRequest(Model model,
                                    @RequestParam("request_name") String name,
                                    @RequestParam("request_description") String description,
                                    @RequestParam("project_id") Project project
                                    ){

        //check if there is any active request
        if(!checkActiveRequest(project)) {
            Authentication authentication = authenticationFacade.getAuthentication();
            Request addedRequest = new Request(UUID.randomUUID(), name, description, project);
            addedRequest.setOwner(((CustomUserDetails) authentication.getPrincipal()).getUser());
            addedRequest.setIndex(generateIndex(project));
            //set the response of the owner
            List<Response>responseList = new ArrayList<>();
            addedRequest.setResponseList(responseList);
            responseList.add(setOwnerResponse(addedRequest));
            requestsRepository.saveAndFlush(addedRequest);
        }else{
            throw new RuntimeException();
        }

        return new RedirectView("/user/");
    }

//check if there is any active request
    private boolean checkActiveRequest(Project project) {
        boolean  response =false;
       List<Request> listToCheck= project.getProjectRequestsList();
       if(listToCheck.size()<5){
           response=true;
       }
       return response;
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




    @GetMapping("/evaluate/{id}")
    public String evaluateRequest(Model model, @PathVariable("id") UUID requestId) {
        Optional<Request> optionalRequest = requestsRepository.findById(requestId);
        Request request = optionalRequest.get();
        model.addAttribute("request", request);
        return "evaluate";
    }

    @PostMapping("/evaluate")
    public RedirectView makeResponse(Model model,

                                    @RequestParam("response_type") ResponseType responseType,
                                    @RequestParam("response_comment") String comment,
                                     @RequestParam("request_id") Request requestId
           ){
        Authentication authentication = authenticationFacade.getAuthentication();
        Response response = new Response(UUID.randomUUID(),responseType,comment, requestId);
        response.setUser(((CustomUserDetails) authentication.getPrincipal()).getUser());

        responseRepository.saveAndFlush(response);

        generateProjectTask(requestId);

        return new RedirectView("/user/");
    }

    /** generates a duplicate entry in requests table*/
    private void generateProjectTask(Request request){
        List<Response> responsesList = request.getResponseList();
        if(responsesList.size()==5){

            List<Response> responseTypeList=responsesList.stream()
                    .filter(response -> response.getResponseType() == ResponseType.APPROVED).collect(Collectors.toList());

            if(responseTypeList.size()==3) {
                LocalDate start = (LocalDate.now());
                LocalDate end = (LocalDate.now().plusWeeks(2));
                ProjectTask projectTask = new ProjectTask(request.getRequestId(), request.getRequestName(), request.getText(), request.getProject(), start, end);
                projectTask.setIndex(request.getIndex());
                projectTask.setOwner(request.getOwner());

                projectTaskRepository.saveAndFlush(projectTask);

            }
        }
    }



    @GetMapping("/update/{name}")
    public String updateRequest(Model model, @PathVariable("name") String requestName) {
       Request requestToEdit = requestsRepository.findRequestsByName(requestName);
       Project project= requestToEdit.getProject();
       model.addAttribute("request", requestToEdit);
       model.addAttribute("project", project);
        return "updateForm";
    }


    @PostMapping("/update")
    public RedirectView makeUpdate(Model model,
                                   @RequestParam("request_name") String name,
                                   @RequestParam("request_description") String description,
                                   @RequestParam("project_id") Project project
    ){
        Authentication authentication = authenticationFacade.getAuthentication();

        Request updatedRequest = new Request(UUID.randomUUID(),name,description,project);
        updatedRequest.setOwner(((CustomUserDetails) authentication.getPrincipal()).getUser());
        updatedRequest.setIndex(generateUpdateIndex(name));
        requestsRepository.saveAndFlush(updatedRequest);
        return new RedirectView("/user/");
    }


    ////set index method
    private double generateUpdateIndex(String requestName){
        Request request = requestsRepository.findRequestsByName(requestName);
        return request.getIndex()+0.1;

    };



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


    @ModelAttribute("user")
    public User  displayUser(){
        User user = getLoggedUser();
        return user;
    }


    /** send to frontend only the projects where the User is member*/

    @ModelAttribute("responseType")
    public Set<ResponseType> displayResponseType(){

        Set<ResponseType> listResponseType = new HashSet<>();
        listResponseType.add(ResponseType.APPROVED);
        listResponseType.add(ResponseType.REJECTED);

        return listResponseType;
    }

    @ModelAttribute("noOfProjectsTasks")
    public Integer noOfProjects(){

       return projectTaskRepository.findAll().size();

    }



}
