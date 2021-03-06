package com.setu.assignment.splitwisepocassignment.dao;

import java.io.Serializable;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartiesHistory implements Serializable {

    private String party1;
    private String party2;
    private Date createdOn;

}
