package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service;

import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.Tag;

import java.util.Set;

public interface TagService {

    Set<Tag> getTagsByUser(final String username);

    void addTagToUser(final String tagName, final String username);

    void removeTagFromUser(final String tagName, final String username);
}
