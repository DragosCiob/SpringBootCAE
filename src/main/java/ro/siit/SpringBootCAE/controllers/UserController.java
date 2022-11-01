package ro.siit.SpringBootCAE.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ro.siit.SpringBootCAE.models.*;
import ro.siit.SpringBootCAE.repositores.RequestRepository;
import ro.siit.SpringBootCAE.repositores.ResponseRepository;
import ro.siit.SpringBootCAE.services.IAuthenticationFacade;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/requests")
public class UserController {
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
     return "UI";
    }

    @GetMapping("/myRequests")
    public String getUserRequests(Model model){
        Authentication authentication = authenticationFacade.getAuthentication();
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        model.addAttribute("requests", requestsRepository.findRequestsByUser(user));
        return "UI";
    }



    @GetMapping("/add")
    public String addRequestForm(Model model) {
        return "/addForm";
    }

    @PostMapping("/add")
    public RedirectView makeRequest(Model model,
                                    @RequestParam("request_name") String name,
                                    @RequestParam("request_description") String description
                                  /* @RequestParam("user_id") User owner */ ){

        Authentication authentication = authenticationFacade.getAuthentication();
        Request addedRequest = new Request(UUID.randomUUID(),name,description);
        addedRequest.setOwner(((CustomUserDetails) authentication.getPrincipal()).getUser());
        requestsRepository.saveAndFlush(addedRequest);
        return new RedirectView("/requests/");
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
      //need to be added th id request

        responseRepository.saveAndFlush(response);
        return new RedirectView("/requests/");
    }



}
