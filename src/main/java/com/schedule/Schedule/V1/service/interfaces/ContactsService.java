package com.schedule.Schedule.V1.service.interfaces;

import com.schedule.Schedule.V1.dto.contacts.ContactsPageResponse;
import com.schedule.Schedule.V1.dto.contacts.ContactsRequest;
import com.schedule.Schedule.V1.dto.contacts.ContactsResponse;
import com.schedule.Schedule.V1.model.Contacts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ContactsService {
    ContactsResponse create(ContactsRequest contactsRequest , UUID scheduleUUID);

    Map<String, String> deleteContact(UUID uuid);

    ContactsResponse update(UUID scheduleUUID,UUID contactUUID, ContactsRequest contactsRequest);

    List<ContactsResponse> getAll(UUID scheduleUUID);

    ContactsResponse getById(UUID uuid);

    Map<String,String> manyDeleted(List<UUID> idList);

    ContactsPageResponse contactsPage(UUID scheduleId, Pageable pageable);
}
