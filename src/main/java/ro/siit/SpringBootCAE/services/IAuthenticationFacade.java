package ro.siit.SpringBootCAE.services;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {

    Authentication getAuthentication();
}