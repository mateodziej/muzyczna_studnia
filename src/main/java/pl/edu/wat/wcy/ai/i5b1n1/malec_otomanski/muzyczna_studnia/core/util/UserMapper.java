package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.util;

import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.User;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.UserDto;

public class UserMapper {
    public static User map(UserDto userDto, User user) {
        if(userDto.getCity() != null) user.setCity(userDto.getCity());
        if(userDto.getLastFmUsername() != null) user.setLastFmUsername(userDto.getLastFmUsername());
        if(userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        return user;
    }
}
