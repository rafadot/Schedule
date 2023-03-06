package com.schedule.Schedule.V1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    private String creatorName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_uuid")
    private List<Contacts> contacts;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_uuid")
    private List<Notes> notes;

    @ManyToMany(cascade = {CascadeType.ALL,CascadeType.REMOVE})
    @JoinTable(
            name = "schedule_events",
            joinColumns = @JoinColumn(name = "schedule_uuid"),
            inverseJoinColumns = @JoinColumn (name = "events_uuid")
    )
    private List<Events> events;
}
