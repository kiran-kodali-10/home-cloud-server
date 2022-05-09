package com.cloudforstorage.homecloudserver.service;

import com.cloudforstorage.homecloudserver.bean.UserBean;
import com.cloudforstorage.homecloudserver.entity.UserEntity;
import com.cloudforstorage.homecloudserver.payload.request.SignUpRequest;
import com.cloudforstorage.homecloudserver.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;



    Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("could not find user");

        }
        logger.info("Returned userbean for authentication");
        return new UserBean(userEntity);

    }


    /**
     *
     * @param signUpRequest
     * @return UserBean
     *Takes the SignUpRequest and save it to repository.
     */
    public UserBean registerUser(SignUpRequest signUpRequest) {
        // Perform the server side validations here.
        UserBean userBean = new UserBean();
        try {
            UserEntity userEntity = new UserEntity();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userEntity.setUserName(signUpRequest.getUsername());
            userEntity.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            userEntity.setRole(signUpRequest.getRole());
            userEntity.setActive(true);
            userBean = new UserBean(userRepository.save(userEntity));
            logger.info("added user " + userBean);
        } catch (BeansException e) {
            logger.error(e.getMessage());
        }
        return userBean;
    }
}
