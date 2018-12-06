package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.User;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.UserDto;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.repository.UserRepository;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.util.UserMapper;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserService {

    final static Logger logger = Logger.getLogger(UserService.class);

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void add(String username){
        if(userRepository.findByUsername(username)
                .isPresent()) return;
        User user = User.builder()
                .username(username)
                .build();
        logger.info("add user -> username : " + username);
        userRepository.save(user);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User with username " + username + " not found!"));
    }

    @Transactional
    public void update(String username, UserDto userDto) {
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    User userToUpdate = UserMapper.map(userDto, user);
                    userRepository.save(userToUpdate);
                    logger.info("update user -> username : " + username);
                });
    }
}
