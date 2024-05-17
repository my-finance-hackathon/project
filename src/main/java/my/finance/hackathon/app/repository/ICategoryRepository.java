package my.finance.hackathon.app.repository;

import my.finance.hackathon.app.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    /**
     * ILIKE = ignore case
     */
    @Query(value = "SELECT name FROM category WHERE name ILIKE %:partName%", nativeQuery = true)
    List<String> findByPartName(String partName);

    boolean existsByNameIgnoreCase(String name);
}
