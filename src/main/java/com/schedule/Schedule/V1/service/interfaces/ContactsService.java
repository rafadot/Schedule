package com.schedule.Schedule.V1.service.interfaces;

import com.schedule.Schedule.V1.dto.contacts.ContactsRequest;
import com.schedule.Schedule.V1.dto.contacts.ContactsResponse;
import com.schedule.Schedule.V1.model.Contacts;

import java.util.Map;
import java.util.UUID;

public interface ContactsService {
    ContactsResponse create(ContactsRequest contactsRequest , UUID scheduleUUID);

    Map<String, String> deleteContact(UUID uuid);

    ContactsResponse update(UUID uuid, ContactsRequest contactsRequest);
}
