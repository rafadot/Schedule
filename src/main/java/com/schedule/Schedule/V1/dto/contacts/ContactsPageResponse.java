package com.schedule.Schedule.V1.dto.contacts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactsPageResponse {

    private int page;

    private int totalPages;

    private int size;

    private List<ContactsResponse> contacts;

}
