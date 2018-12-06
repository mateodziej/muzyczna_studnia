package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.Tag;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.User;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.repository.TagRepository;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TagService {

    final static Logger logger = Logger.getLogger(TagService.class);

    private UserRepository userRepository;
    private TagRepository tagRepository;

    @Autowired
    public TagService(UserRepository userRepository, TagRepository tagRepository) {
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    @Transactional
    public Set<Tag> getTagsByUser(final String username) {
        Set<Tag> tags = new HashSet<>();
        Optional<User> dbUser = userRepository.findByUsername(username);
        if (dbUser.isPresent()) {
            logger.debug("findTagsByUser username : " + username);
            tags = tagRepository.findAllByUsers(dbUser.get());
        }
        return tags;
    }

    @Transactional
    public void addTagToUser(final String tagName, final String username) {
        Optional<Tag> dbTag = tagRepository.findByName(tagName);
        if (!dbTag.isPresent()) dbTag = Optional.of(Tag.builder()
                .name(tagName)
                .build());

        Optional<User> dbUser = userRepository.findByUsername(username);
        if (dbUser.isPresent()) {
            logger.info("add tag to user -> user : " + username + " tag : " + dbTag.get().getName());
            User user = dbUser.get();
            user.getTags().add(dbTag.get());
            userRepository.save(user);
        }
    }

    @Transactional
    public void removeTagFromUser(@PathVariable final String tagName, @PathVariable final String username) {
        Optional<Tag> dbTag = tagRepository.findByName(tagName);
        if (!dbTag.isPresent()) return;

        Optional<User> dbUser = userRepository.findByUsername(username);
        if (dbUser.isPresent()) {
            logger.info("remove tag from user -> user : " + username + " tag : " + dbTag.get().getName());
            User user = dbUser.get();
            user.getTags().remove(dbTag.get());
            userRepository.save(user);
        }
    }
}
