package com.calendargenerator.dao;

import com.calendargenerator.model.CombinedUekGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CombinedGroupsDAO extends JpaRepository<CombinedUekGroups, UUID> {
}
