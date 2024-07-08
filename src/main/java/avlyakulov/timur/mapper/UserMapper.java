package avlyakulov.timur.mapper;

import avlyakulov.timur.dto.UserDto;
import avlyakulov.timur.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserDto userDto);
}
