package avlyakulov.timur.mapper;

import avlyakulov.timur.dto.UserUpdateDto;
import avlyakulov.timur.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserUpdateDto userUpdateDto);
}
