package com.autoexam.apiserver.dao;

import com.autoexam.apiserver.beans.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao extends JpaRepository<Admin, Long> {
}
