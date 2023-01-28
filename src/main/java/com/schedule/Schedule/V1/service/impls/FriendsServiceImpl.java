package com.schedule.Schedule.V1.service.impls;

import com.schedule.Schedule.V1.dto.users.UserFriendsResponse;
import com.schedule.Schedule.V1.dto.users.UsersResponse;
import com.schedule.Schedule.V1.mapper.UsersMapper;
import com.schedule.Schedule.V1.model.Users;
import com.schedule.Schedule.V1.repository.UsersRepository;
import com.schedule.Schedule.V1.service.interfaces.FriendsService;
import com.schedule.Schedule.exceptions.gerenciment.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendsServiceImpl implements FriendsService {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;

    @Override
    public UsersResponse addFriend(UUID user, UUID friend) {
        Optional<Users> optUsers = usersRepository.findById(user);

        if(!optUsers.isPresent())
            throw new BadRequestException("Id do usuário inválido");

        Optional<Users> optFriends = usersRepository.findById(friend);

        if(!optFriends.isPresent())
            throw new BadRequestException("Id do friend inválido");

        if(optUsers.get().getFriends().contains(optFriends.get()))
            throw new BadRequestException(optFriends.get().getNickName() + " já é seu amigo");

        optUsers.get().getFriends().add(optFriends.get());
        usersRepository.save(optUsers.get());

        UsersResponse response = new UsersResponse();
        BeanUtils.copyProperties(optFriends.get(),response);
        response.setScheduleUUID(optFriends.get().getSchedule().getUuid());

        return response;
    }

    @Override
    public UserFriendsResponse getFriendsUser(UUID userUUID) {
        Optional<Users> optUsers = usersRepository.findById(userUUID);

        if(!optUsers.isPresent())
            throw new BadRequestException("Id do usuário inválido");

        UserFriendsResponse response = new UserFriendsResponse();

        response.setTotalFriends(optUsers.get().getFriends().size());
        response.setFriendsResponse(optUsers.get().getFriends().stream().map(m->{
            UsersResponse response1 = new UsersResponse();
            usersMapper.usersResponseMapper(m,response1);
            response1.setScheduleUUID(m.getSchedule().getUuid());
            return response1;
        }).collect(Collectors.toList()));

        return response;
    }

    @Override
    public Map<String, String> deleteFriend(UUID userUUID, UUID friendUUID) {
        Optional<Users> optUsers = usersRepository.findById(userUUID);

        if(!optUsers.isPresent())
            throw new BadRequestException("Id do usuário inválido");

        Optional<Users> optFriend = usersRepository.findById(friendUUID);

        if(!optFriend.isPresent())
            throw new BadRequestException("Id do friend inválido");

        List<Users> friendList = optUsers.get().getFriends();
        Map<String,String> response = new HashMap<>();

        for(Users f : friendList){
            if(f.equals(optFriend.get())){
                optUsers.get().getFriends().remove(optFriend.get());
                usersRepository.save(optUsers.get());
                response.put("message",optFriend.get().getNickName() + " deletado com sucesso!");
                return response;
            }
        }
        response.put("message",optFriend.get().getNickName() + " já foi excluído");
        return response;
    }
}
