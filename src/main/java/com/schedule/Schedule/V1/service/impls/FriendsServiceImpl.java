package com.schedule.Schedule.V1.service.impls;

import com.schedule.Schedule.V1.dto.friends.FriendsResponse;
import com.schedule.Schedule.V1.dto.friends.UserFriendsResponse;
import com.schedule.Schedule.V1.dto.users.UsersResponse;
import com.schedule.Schedule.V1.mapper.UsersMapper;
import com.schedule.Schedule.V1.model.Events;
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
    public FriendsResponse addFriend(UUID user, String friendNickName) {
        Optional<Users> optUsers = usersRepository.findById(user);

        if(!optUsers.isPresent())
            throw new BadRequestException("Usuário inválido");

        if(optUsers.get().getNickName().equals(friendNickName))
            throw new BadRequestException("Não é possível adicionar seu próprio usuário!");

        Optional<Users> optFriends = usersRepository.findByNickName(friendNickName);

        if(!optFriends.isPresent())
            throw new BadRequestException("Apelido do amigo inválido");

        if(optUsers.get().getFriends().contains(optFriends.get()))
            throw new BadRequestException(friendNickName + " já é seu amigo");

        optUsers.get().getFriends().add(optFriends.get());
        usersRepository.save(optUsers.get());

        FriendsResponse response = new FriendsResponse();
        BeanUtils.copyProperties(optFriends.get(),response);

        return response;
    }

    @Override
    public UserFriendsResponse getFriendsUser(UUID userUUID) {
        Optional<Users> optUsers = usersRepository.findById(userUUID);

        if(!optUsers.isPresent())
            throw new BadRequestException("Id do usuário inválido");

        UserFriendsResponse response = new UserFriendsResponse();

        response.setTotalFriends(optUsers.get().getFriends().size());
        response.setFriendsResponse(optUsers.get().getFriends().stream()
                .map(m->{
                    long commonEvents = 0;

                    for(Events eventUser : m.getSchedule().getEvents()){
                        for(Events eventFriend: optUsers.get().getSchedule().getEvents()){
                            if(eventFriend.getTitle().equals(eventUser.getTitle()) && eventFriend.getCreator().equals(eventUser.getCreator()))
                                commonEvents++;
                        }
                    }

                    return FriendsResponse.builder()
                            .uuid(m.getUuid())
                            .email(m.getEmail())
                            .fullName(m.getFullName())
                            .nickName(m.getNickName())
                            .birthDate(m.getBirthDate())
                            .genre(m.getGenre())
                            .commonEvents(commonEvents)
                            .build();
                        }
                ).collect(Collectors.toList()));

        return response;
    }

    @Override
    public Map<String, String> deleteFriend(UUID userUUID, String friendNickName) {
        Optional<Users> optUsers = usersRepository.findById(userUUID);

        if(!optUsers.isPresent())
            throw new BadRequestException("Id do usuário inválido");

        Optional<Users> optFriend = usersRepository.findByNickName(friendNickName);

        if(!optFriend.isPresent())
            throw new BadRequestException("Apelido do amigo inválido");

        List<Users> friendList = optUsers.get().getFriends();
        Map<String,String> response = new HashMap<>();

        for(Users f : friendList){
            if(f.equals(optFriend.get())){
                optUsers.get().getFriends().remove(optFriend.get());
                usersRepository.save(optUsers.get());
                response.put("message",friendNickName + " deletado com sucesso!");
                return response;
            }
        }
        response.put("message",friendNickName + " já foi excluído");
        return response;
    }
}
