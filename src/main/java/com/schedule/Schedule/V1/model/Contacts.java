package com.schedule.Schedule.V1.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contacts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    private String name;

    private String nickname;

    private Long phoneNumber;

    @ManyToOne
    @JoinColumn(name = "schedule_uuid")
    private Schedule schedule;

}
