package com.course.altanto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.course.altanto.entity.File;

public interface IFileRepository extends JpaRepository<File, String> {}
