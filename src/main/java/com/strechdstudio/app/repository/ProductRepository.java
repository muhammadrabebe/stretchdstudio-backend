package com.strechdstudio.app.repository;

import com.strechdstudio.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
//    List<Product> findByCategoryId(int categoryId);
//    List<Product> findByCategory_categoryId(int categoryId);
    List<Product> findByCategory_CodeLkupId(int categoryId);

}
