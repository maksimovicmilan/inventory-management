package service;

import entity.Material;
import entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import repository.MaterialRepository;

import java.util.List;
import java.util.Optional;


@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    @Autowired
    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @CachePut(cacheNames = "materials", key = "#id")
    public Material createMaterial(Material material){
        return materialRepository.save(material);
    }

    @Cacheable(cacheNames = "materials", key = " #id")
    public List<Material> getAllMaterials(){
        return materialRepository.findAll();
    }

    @CachePut(cacheNames = "materials", key = "#id")
    public Material updateMaterial(Long id, Material updatedMaterial){
        if(materialRepository.existsById(id)){
            updatedMaterial.setId(id);
            return materialRepository.save(updatedMaterial);
        } else{
            throw new IllegalArgumentException("Material not found with id: " + id);
        }
    }

    @CacheEvict(cacheNames = "materials", key = "#id")
    public void deleteMaterial(Long id){
        if(materialRepository.existsById(id)){
            materialRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("Material not found with this id: " + id);
        }
    }

    public Optional<Material> getMaterialById(Long id){
        return materialRepository.findById(id);
    }

    public List<Material> getMaterialsByCategory(String category){
        return materialRepository.findByCategory(category);
    }

    public List<Material> getMaterialsByKeyword(String keyword){
        return materialRepository.findByKeywordContainingIgnoreCase(keyword);
    }


}
