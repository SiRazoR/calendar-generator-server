package com.calendargenerator.dao;

import com.calendargenerator.model.UekGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupsDAO extends JpaRepository<UekGroup, UUID> {
}
