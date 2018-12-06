package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service;

import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.User;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.UserDto;

public interface UserService {

    void add(final String username);

    User getUser(final String username);

    void update(final String username, final UserDto userDto);
}
