package com.cos.findprotein.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.findprotein.model.Wish;

public interface WishRepository extends JpaRepository<Wish, Integer> {
	Optional<Wish> findByUserId(int id);

	Optional<Wish> findById(int id);
}