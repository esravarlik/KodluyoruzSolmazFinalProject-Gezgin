package com.jojo.gezginservice.service;

import com.jojo.gezginservice.config.RabbitMQConfiguration;
import com.jojo.gezginservice.config.enums.NotificationType;
import com.jojo.gezginservice.converter.UserConverter;
import com.jojo.gezginservice.exceptions.GeneralException;
import com.jojo.gezginservice.exceptions.Message;
import com.jojo.gezginservice.exceptions.UserNotFoundException;
import com.jojo.gezginservice.model.User;
import com.jojo.gezginservice.model.enums.ErrorCode;
import com.jojo.gezginservice.repository.UserRepository;
import com.jojo.gezginservice.response.UserResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserService {

    Logger logger = Logger.getLogger(UserService.class.getName());
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter converter;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQConfiguration rabbitMQConfiguration;

    private static Supplier<GeneralException> notFoundUser(HttpStatus unauthorized) {
        throw new GeneralException(Message.USER_NOT_FOUND, HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND);
    }

    @Transactional
    public User createUser(User user) throws Exception {
        isEmptyEmailAndPassword(user.getEmail(), user.getPassword());
        isEmptyUserTypeAndPhone(user.getUserType().toString(), user.getRole().toString());
        user.setNotificationType(NotificationType.EMAIL);
        User saved = userRepository.save(user);


        rabbitTemplate.convertAndSend(rabbitMQConfiguration.getQueueName(), user);
        logger.log(Level.INFO,
                "[GezginService UserService] -- Sent message with user data: {}", user);

        return saved;
    }

    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = converter.convert(users);
        return userResponses;
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> getById(Integer userId) throws Exception {
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isPresent()) {
            return userRepository.findById(userId);
        }
        throw new UserNotFoundException();
    }

    public void delete(Integer id) {
        User deleteUser = userRepository.findById(id)
                .orElseThrow(() -> new GeneralException(Message.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND,
                        ErrorCode.NOT_FOUND));
        userRepository.deleteById(id);
    }

    public UserResponse getUserDto(String email) {
        var user = getByEmail(email);
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    private void isEmptyEmailAndPassword(String email, String password) throws Exception {
        if (email.isEmpty() || password.isEmpty() || email == null || password == null) {
            logger.log(Level.WARNING, "Email and password cannot be empty.");
            throw new GeneralException(Message.NOT_NULL_EMAIL_AND_PASSWORD,
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NOT_NULL);
        }
    }

    private void isEmptyUserTypeAndPhone(String userType, String phoneNumber) throws Exception {
        if (userType == null || phoneNumber.isEmpty()) {
            logger.log(Level.WARNING, "User type and phoneNumber cannot be empty.");
            throw new GeneralException(Message.NOT_BLANK_PHONE_AND_USERTYPE,
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.BAD_REQUEST);
        }
    }
}
