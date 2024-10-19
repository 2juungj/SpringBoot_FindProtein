package com.cos.findprotein.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.findprotein.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
