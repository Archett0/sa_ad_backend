package sg.edu.nus.ad_backend.service;

import sg.edu.nus.ad_backend.security.model.LoginUser;

import java.util.Optional;

public interface IUserService {
    Optional<LoginUser> getUserByUsername(String username);
}
