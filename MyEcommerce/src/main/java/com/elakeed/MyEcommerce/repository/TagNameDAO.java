package com.elakeed.MyEcommerce.repository;

import com.elakeed.MyEcommerce.model.TagName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TagNameDAO extends JpaRepository<TagName, Long> {
}
