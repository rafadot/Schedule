package com.schedule.Schedule.V1.service.impls;

import com.schedule.Schedule.V1.dto.contacts.ContactsPageResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        response.put("message", name + " deletado com sucesso!");

        return response;
    }

    @Override
    public ContactsResponse update(UUID scheduleUUID,UUID contactUUID, ContactsRequest contactsRequest) {
        Optional<Schedule> optSchedule = scheduleRepository.findById(scheduleUUID);

        if(!optSchedule.isPresent())
            throw new BadRequestException("Id da agenda não encontrado");

        Optional<Contacts> optContacts = contactsRepository.findById(contactUUID);

        if(!optContacts.isPresent())
            throw new BadRequestException("Contao não existe");

        for(Contacts c : optSchedule.get().getContacts()){
            if(c.getNickname().equals(contactsRequest.getNickname()) && (!c.getUuid().equals(contactUUID)))
                throw new BadRequestException("Esse apelido já existe, por favor escolher outro.");
        }

        Contacts contacts = Contacts.builder()
                .uuid(contactUUID)
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
    public List<ContactsResponse> getAll(UUID scheduleUUID) {
        Optional<Schedule> optSchedule = scheduleRepository.findById(scheduleUUID);

        if(!optSchedule.isPresent())
            throw new BadRequestException("Id da agenda não encontrado");

        return optSchedule.get().getContacts().stream()
                .map(m -> ContactsResponse
                        .builder()
                        .uuid(m.getUuid())
                        .nickname(m.getNickname())
                        .name(m.getName())
                        .phoneNumber(m.getPhoneNumber())
                        .build())
                .collect(Collectors.toList());
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
        Map<String,String> response = new HashMap<>();

        for (UUID id : idList){
            Optional<Contacts> contacts = contactsRepository.findById(id);

            if(!contacts.isPresent())
                throw new BadRequestException("Id(s) inválido(os)");

            if(idList.size() == 1){
                String name = contacts.get().getNickname();
                response.put("message",name + " deletado com sucesso!");
                System.out.println(name);
            }

            contactsRepository.deleteById(id);
            count++;
        }

        if(count>1)
            response.put("message",count + " contatos deletados com sucesso!");

        return response;
    }

    @Override
    public ContactsPageResponse contactsPage(UUID scheduleId, Pageable pageable) {
        Page<Contacts> contacts = contactsRepository.findAllByScheduleUuid(scheduleId,pageable);
        ContactsPageResponse contactsPageResponse = new ContactsPageResponse();

        contactsPageResponse.setPage(pageable.getPageNumber());
        contactsPageResponse.setSize(pageable.getPageSize());
        contactsPageResponse.setTotalPages(contacts.getTotalPages());
        contactsPageResponse.setContacts(contacts
                .stream()
                .map(m->ContactsResponse.builder()
                        .uuid(m.getUuid())
                        .nickname(m.getNickname())
                        .name(m.getName())
                        .phoneNumber(m.getPhoneNumber())
                        .build()
                ).collect(Collectors.toList()));

        return contactsPageResponse;
    }
}
