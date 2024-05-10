package service;

import entity.Material;
import entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Material> getAllMaterials(){
        return materialRepository.findAll();
    }

    public Optional<Material> getMaterialById(Long id){
        return materialRepository.findById(id);
    }

    public List<Material> getMaterialsByCategory(String category){
        return materialRepository.findByCategory(category);
    }

    public Material updateMaterial(Long id, Material updatedMaterial){
        if(materialRepository.existsById(id)){
            updatedMaterial.setId(id);
            return materialRepository.save(updatedMaterial);
        } else{
            throw new IllegalArgumentException("Material not found with id: " + id);
        }
    }

    public List<Material> getMaterialsByKeyword(String keyword){
        return materialRepository.findByKeywordContainingIgnoreCase(keyword);

    }

    public Material createMaterial(Material material){
        return materialRepository.save(material);
    }

    public void deleteMaterial(Long id){
        if(materialRepository.existsById(id)){
            materialRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("Material not found with this id: " + id);
        }
    }

}
