package ma.entraide.glpi.controller;

import ma.entraide.glpi.entity.Materiel;
import ma.entraide.glpi.entity.UserInfo;
import ma.entraide.glpi.service.MaterielService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/materiel")
public class MaterielController {
    @Autowired
    private MaterielService materielService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN_ROLES') OR hasAuthority('USER_ROLES')")
    public ResponseEntity<List<Materiel>> findAllProducts(){
        return new ResponseEntity<>(materielService.getAllMateriel(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES') OR hasAuthority('USER_ROLES')")
    public ResponseEntity<Materiel> findMaterielById(@PathVariable("id") Long id){
        return new ResponseEntity<>(materielService.getMaterielById(id), HttpStatus.OK);
    }

    @PostMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public ResponseEntity<Materiel> addProduct(@PathVariable("id") Integer id,@RequestBody Materiel materiel){
        return new ResponseEntity<>(materielService.addMateriel(id,materiel), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public ResponseEntity<String> deleteMateriel(@PathVariable("id") Long id){
        materielService.deleteMateriel(id);
        return ResponseEntity.ok("Product deleted successfully!.");
    }

    @GetMapping("typeFilter/{type}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES') OR hasAuthority('USER_ROLES')")
    public ResponseEntity<List<Materiel>> findMaterielByType(@PathVariable("type") String type){
        return new ResponseEntity<>(materielService.getMaterielByType(type), HttpStatus.OK);
    }

    @GetMapping("dispoFilter/{dispo}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES') OR hasAuthority('USER_ROLES')")
    public ResponseEntity<List<Materiel>> findMaterielByDispo(@PathVariable("dispo") Boolean dispo){
        return new ResponseEntity<>(materielService.getMaterielByDispo(dispo), HttpStatus.OK);
    }

    @GetMapping("userFilter/{user}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public ResponseEntity<List<Materiel>> findMaterielByUser(@PathVariable("user") String user){
        return new ResponseEntity<>(materielService.getMaterielByUser(user), HttpStatus.OK);
    }

    @GetMapping("marqueRefFilter/{ref}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES') OR hasAuthority('USER_ROLES')")
    public ResponseEntity<Materiel> findMaterielByRef(@PathVariable("ref") String ref){
        return new ResponseEntity<>(materielService.getMaterielByMarqueEtRef(ref), HttpStatus.OK);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public ResponseEntity<Materiel> updateMateriel(@PathVariable("id") Long id, @RequestBody Materiel materiel){
        return new ResponseEntity<>(materielService.updateMateriel(id, materiel), HttpStatus.OK);
    }

    @GetMapping("/paginationAndSortWithType/{offset}/{pageSize}/{type}")
    @PreAuthorize("hasAuthority('ADMIN_ROLES') OR hasAuthority('USER_ROLES')")
    public ResponseEntity<Page<Materiel>> getMaterielWithPaginationAndSortByType(
            @PathVariable int offset, @PathVariable int pageSize, @PathVariable String type) {
        Page<Materiel> materielsWithPagination = materielService.findMaterielsWithPaginationAndSortingByType(offset, pageSize, type);
        return new ResponseEntity<>(materielsWithPagination, HttpStatus.OK);
    }

    @GetMapping("/pages")
    public ResponseEntity<List<Materiel>> getAllMaterialsByType(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "type") String sortBy)
    {
        List<Materiel> list = materielService.getMaterialsByType(pageNo, pageSize, sortBy);

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }
}
