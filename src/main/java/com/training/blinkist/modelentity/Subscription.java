package com.training.blinkist.modelentity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

//Entity class that defines a particular subscription that was created.
// Identifier to a particular User's resources



@Entity
@Getter
@Setter
@Table(name = "subscription")
public class Subscription {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subscription_id")
    private UUID subscriptionId;


    //if a subscription has expired
    @Column(name = "is_finished")
    private boolean isFinished;

    //creation date for this subscription
    @Column(name = "creation_date")
    private Date creationDate;

    //date on which subscription will end
    @Column(name = "finish_date")
    private Date finishDate;


}
