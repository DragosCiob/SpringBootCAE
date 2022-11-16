package ro.siit.SpringBootCAE.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ro.siit.SpringBootCAE.exceptions.ApiRequestException;
import ro.siit.SpringBootCAE.models.*;
import ro.siit.SpringBootCAE.repositores.ProjectRepository;
import ro.siit.SpringBootCAE.repositores.ProjectTaskRepository;
import ro.siit.SpringBootCAE.repositores.RequestRepository;
import ro.siit.SpringBootCAE.repositores.ResponseRepository;
import ro.siit.SpringBootCAE.services.IAuthenticationFacade;

import java.text.DecimalFormat;
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
     return "UI";
    }


    @GetMapping("/myRequests")
    public String getUserRequests(Model model){
        User user = getLoggedUser();

        boolean displayMyRequests = true;

         List<Project> projectList = projectsRepository.findAll();
         List <Request> requestListToDisplay = new ArrayList<>();


        //check if there is any update of the request and show only that last update
        checkForUpdates(user, projectList, requestListToDisplay);

        model.addAttribute("myRequests", requestListToDisplay);
        model.addAttribute("displayMyRequests", displayMyRequests);

        return "UI";
    }

    private static void checkForUpdates(User user, List<Project> projectList, List<Request> requestListToDisplay) {
        for (Project project: projectList) {
            TreeSet <Request> orderedRequestsList = new TreeSet<>(new UpdateIndexComparator());

            List<Request> requestList = project.getProjectRequestsList().stream()
                            .filter(request ->request.getOwner().getUsername().equals(user.getUsername())).collect(Collectors.toList());

            orderedRequestsList.addAll(requestList);

            if(!requestList.isEmpty()) {

                requestListToDisplay.add((orderedRequestsList).last());
            }

        }
    }

    @GetMapping("/Statistics")
    public String getUserProjectsInfo(Model model){
        User user = getLoggedUser();
        boolean displayStatistics = true;

        List<Request> requestList =requestsRepository.findAll()
                .stream()
                .filter(request -> request.getProject().getProjectMembers().contains(user)).collect(Collectors.toList());

        List<Project> projectList =projectsRepository.findAll()
                .stream()
                .filter(project -> project.getProjectMembers().contains(user)).collect(Collectors.toList());

        List<ProjectTask> ProjectTask = projectTaskRepository.findAll();

        List<Request> listOfProjectTask = new ArrayList<>();

        //check if the request is currently a project task
        for (Request request : requestList) {
            String requestName = request.getRequestName();
            for (ProjectTask projectTask : ProjectTask) {
                if (requestName.equals(projectTask.getRequestName())) {
                    listOfProjectTask.add(request);
                }

            }

        }

        model.addAttribute("noOfProjects", listOfProjectTask.size());
        model.addAttribute("totalNoOfRequests", requestsRepository.findAll().size());
        model.addAttribute("projects", projectList);
        model.addAttribute("displayStatistics", displayStatistics);
        return "UI";
    }


    @GetMapping("/ProjectTasks")
    public String getProjectTasksInfo(Model model){
        User user = getLoggedUser();
        boolean displayProjectTasks = true;

        List<ProjectTask> projectTasks = projectTaskRepository.findAll().stream()
                .filter(projectTask -> projectTask.getProject().getProjectMembers().contains(user)).collect(Collectors.toList());



        model.addAttribute("projectTasks", projectTasks);
        model.addAttribute("displayProjectTasks", displayProjectTasks);
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
            throw new ApiRequestException("Please check if there is any proposal on this project that requires your answer or if there is any ongoing Project task active");
        }

        return new RedirectView("/user/myRequests");
    }

//check if there is any active request
    private boolean checkActiveRequest(Project project) {
        boolean  response =false;

       List<Request> listToCheck= project.getProjectRequestsList().stream()
               .filter(request -> request.getResponseList().size()<5).collect(Collectors.toList());

        for (Request request: listToCheck) {
            if( request.getProject().getProjectName().equals(project.getProjectName())){
                response =true;
            }
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

        Request request = requestsRepository.findRequestsById(requestId);
       List <Request> list = requestsRepository.findRequestsByName(request.getRequestName());

       TreeSet<Request> set = new TreeSet<>( new UpdateIndexComparator());
        set.addAll(list);

        if(!set.isEmpty()){
          if(set.last().getIndex()>request.getIndex()){

              throw new ApiRequestException("Please be sure that you make evaluation to th last update, check also all the previous status");
          }else{

              String requestName= request.getRequestName();
              model.addAttribute("request", request);
              model.addAttribute("requestName", requestName);

          }

        }

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
        generateProjectTask(requestId.getRequestId(), responseType);

        return new RedirectView("/user/");
    }

    /** generates a duplicate entry in requests table*/
    private void generateProjectTask(UUID requestId, ResponseType responseType){

        Request request = requestsRepository.findRequestsById(requestId);
        List<Response> responseList = request.getResponseList();
        Set<Response> responseSet = new HashSet<>(responseList);

        //Persistence Bag????
        Set<Response> items = new HashSet<>();
        Set<Response> filteredList = responseList.stream()
                .filter(response -> !items.add(response))
                .collect(Collectors.toSet());



        if(filteredList.size()==4){

            Set<ResponseType> responseTypeSet = items.stream().map(Response::getResponseType)
                    .filter(responseType1 -> responseType1==ResponseType.APPROVED).collect(Collectors.toSet());

                    responseTypeSet.add(responseType);

            if (responseSet.size() >= 3) {
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
        TreeSet<Request> orderedRequestsList = getRequestsOrderedList(requestName);
        Request requestToEdit = orderedRequestsList.last();
        Project project= requestToEdit.getProject();

       model.addAttribute("request", requestToEdit);
       model.addAttribute("project", project);
       return "updateForm";
    }

    private TreeSet<Request> getRequestsOrderedList(String requestName) {
        List<Request>  requestsList = requestsRepository.findRequestsByName(requestName);
        TreeSet<Request> orderedRequestsList= new TreeSet<>(new UpdateIndexComparator());
        orderedRequestsList.addAll(requestsList);
        return orderedRequestsList;
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
        updatedRequest.setText("UPDATED!" + " " + description);
        //set the response of the owner
        List<Response>responseList = new ArrayList<>();
        updatedRequest.setResponseList(responseList);
        responseList.add(setOwnerResponse(updatedRequest));

        requestsRepository.saveAndFlush(updatedRequest);
        return new RedirectView("/user/myRequests");
    }


    ////set index method
    private double generateUpdateIndex(String requestName){

        TreeSet<Request> orderedRequestsList = getRequestsOrderedList(requestName);
        Request requestToEdit = orderedRequestsList.last();
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(requestToEdit.getIndex()+0.1));

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


    @ModelAttribute("user")
    public User  displayUser(){
        return  getLoggedUser();
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
