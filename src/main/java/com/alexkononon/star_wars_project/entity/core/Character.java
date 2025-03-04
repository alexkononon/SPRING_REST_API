package com.alexkononon.star_wars_project.entity.core;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE characters SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@Table(name = "Characters")
public class Character {

    @Id
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false)
    private int current_xp;

    @Column(name = "character_rank", length = 50)
    private String rank;

    private Integer max_simultaneous_missions;

    private Integer completed_missions;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isDeleted;


    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    private EntityObject entityObject;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "mission_characters",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "mission_id")
    )
    private Set<Mission> missions = new HashSet<>();

    @ManyToMany(mappedBy = "members", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Faction> factions = new HashSet<>();

    @ManyToOne(optional = true)
    @JoinColumn(name = "major_id")
    private Character supreme;

    @OneToMany(mappedBy = "supreme")
    private Set<Character> subordinates = new HashSet<>();

    @ManyToOne(optional = true)
    @JoinColumn(name = "base_location_id")
    private Location baseLocation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "current_location_id")
    private Location currentLocation;

}
