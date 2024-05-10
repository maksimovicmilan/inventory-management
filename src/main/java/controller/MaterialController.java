package controller;

import entity.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.MaterialService;


import java.util.List;

@RestController
@RequestMapping("/api/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/")
    public ResponseEntity<List<Material>> getAllMaterials(){
        List<Material> materials = materialService.getAllMaterials();
        return ResponseEntity.ok(materials);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Material> getMaterialById(@PathVariable Long id){
        try {
            Material material = materialService.getMaterialById(id)
                    .orElseThrow(() -> new MaterialNotfoundException("Material not found with id: " + id));
            return ResponseEntity.ok(material);
        } catch(MaterialNotfoundException ex) {
            return ResponseEntity.notFound().build();
        } catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/{keyword}")
    public ResponseEntity<List<Material>> getMaterialsByKeyword(@RequestParam String keyword){
        List<Material> materials = materialService.getMaterialsByKeyword(keyword);
        return ResponseEntity.ok(materials);
    }
    @PostMapping("/create")
    public ResponseEntity<Material> createMaterial(@RequestBody Material material){
        try{
            Material createMaterial = materialService.createMaterial(material);
            return ResponseEntity.status(HttpStatus.CREATED).body(createMaterial);
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Material> updateMaterial(@PathVariable Long id, @RequestBody Material updatedMaterial){
        try{
            Material material = materialService.updateMaterial(id, updatedMaterial);
            return ResponseEntity.ok(material);
        }catch(IllegalArgumentException ex){
            return ResponseEntity.notFound().build();
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Material> deleteMaterial(@PathVariable Long id){
     try{
         materialService.deleteMaterial(id);
         return ResponseEntity.noContent().build();
     }catch(MaterialNotfoundException ex){
         return ResponseEntity.notFound().build();
     }catch(Exception ex){
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
