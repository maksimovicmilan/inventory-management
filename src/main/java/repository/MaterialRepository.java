package repository;

import entity.Material;
import entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

    List<Material> findByCategory(String category);

    List<Material> findByKeywordContainingIgnoreCase(String keyword);

    List<Material> findByName(String name);
}
