package com.schedule.Schedule.V1.service.impls;

import com.schedule.Schedule.V1.dto.contacts.ContactsRequest;
import com.schedule.Schedule.V1.dto.contacts.ContactsResponse;
import com.schedule.Schedule.V1.model.Contacts;
import com.schedule.Schedule.V1.model.Schedule;
import com.schedule.Schedule.V1.repository.ContactsRepository;
import com.schedule.Schedule.V1.repository.ScheduleRepository;
import com.schedule.Schedule.V1.service.interfaces.ContactsService;
import com.schedule.Schedule.exceptions.gerenciment.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ContactsServiceImpl implements ContactsService {

    private final ContactsRepository contactsRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public ContactsResponse create(ContactsRequest contactsRequest, UUID scheduleUUID) {
        Optional<Schedule> schedule = scheduleRepository.findById(scheduleUUID);

        if(!schedule.isPresent())
            throw new BadRequestException("Id não encontrado");

        for(Contacts contacts : schedule.get().getContacts()){
            if(contacts.getNickname().equals(contactsRequest.getNickname()))
                throw new BadRequestException("Contato já existe!");
        }

        Contacts contacts = new Contacts();
        BeanUtils.copyProperties(contactsRequest , contacts);

        contactsRepository.save(contacts);
        schedule.get().getContacts().add(contacts);

        scheduleRepository.save(schedule.get());

        return ContactsResponse.builder()
                .uuid(contacts.getUuid())
                .name(contacts.getName())
                .nickname(contacts.getNickname())
                .phoneNumber(contacts.getPhoneNumber())
                .build();
    }

    @Override
    public Map<String, String> deleteContact(UUID uuid) {
        Optional<Contacts> contacts = contactsRepository.findById(uuid);
        if(!contacts.isPresent())
            throw new BadRequestException("Id não encontrado");

        String name = contacts.get().getNickname();
        contactsRepository.deleteById(uuid);

        Map<String, String> response = new HashMap<>();
        response.put("message", name + " deletado com sucesso");

        return response;
    }

    @Override
    public ContactsResponse update(UUID uuid, ContactsRequest contactsRequest) {
        Optional<Contacts> optContacts = contactsRepository.findById(uuid);

        if(!optContacts.isPresent())
            throw new BadRequestException("Contato não encontrado");

        Contacts contacts = Contacts.builder()
                .uuid(uuid)
                .name(contactsRequest.getName() != null ? contactsRequest.getName() : optContacts.get().getName())
                .nickname(contactsRequest.getNickname() != null ? contactsRequest.getNickname() : optContacts.get().getNickname())
                .phoneNumber(contactsRequest.getPhoneNumber() != null ? contactsRequest.getPhoneNumber() : optContacts.get().getPhoneNumber())
                .build();
        contactsRepository.save(contacts);

        ContactsResponse response = new ContactsResponse();
        BeanUtils.copyProperties(contacts,response);
        return response;
    }

    @Override
    public List<Contacts> getAll(UUID scheduleUUID) {
        Optional<Schedule> optSchedule = scheduleRepository.findById(scheduleUUID);

        if(!optSchedule.isPresent())
            throw new BadRequestException("Id da agenda não encontrado");

        return optSchedule.get().getContacts();
    }

    @Override
    public ContactsResponse getById(UUID scheduleUUID) {
        Optional<Contacts> optionalContacts = contactsRepository.findById(scheduleUUID);

        if(!optionalContacts.isPresent())
            throw new BadRequestException("Id do contato não encontrado");

        Contacts contacts = optionalContacts.get();
        ContactsResponse response = new ContactsResponse();
        BeanUtils.copyProperties(contacts,response);

        return response;
    }

    @Override
    public Map<String, String> manyDeleted(List<UUID> idList) {
        long count = 0;
        String name = "";

        for (UUID id : idList){
            Optional<Contacts> contacts = contactsRepository.findById(id);

            if(!contacts.isPresent())
                throw new BadRequestException("Id(s) inválido(os)");

            if(idList.size() == 0)
                name = contacts.get().getNickname();

            contactsRepository.deleteById(id);
            count++;
        }
        Map<String,String> response = new HashMap<>();

        if(count>1){
            response.put("message",count + " contatos deletados com sucesso!");
        }else {
            response.put("message",name + " deletado com sucesso!");
        }

        return response;
    }

}
