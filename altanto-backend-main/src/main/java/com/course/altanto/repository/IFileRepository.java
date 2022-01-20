package com.course.altanto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.course.altanto.entity.File;

@Repository
public interface IFileRepository extends JpaRepository<File, String> {}
