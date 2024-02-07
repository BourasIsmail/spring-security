package ma.entraide.glpi.service;

import ma.entraide.glpi.entity.Materiel;
import ma.entraide.glpi.entity.UserInfo;
import ma.entraide.glpi.repository.MaterielRepository;
import ma.entraide.glpi.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MaterielService {
    @Autowired
    private MaterielRepository materielRepository;

    @Autowired
    private UserInfoService userInfoService;

    public Materiel getMaterielByMarqueEtRef(String marqueEtRef) {
        Optional<Materiel> materielOp = materielRepository.findMaterielByMarqueEtRef(marqueEtRef);
        Materiel materiel = null;
        if (materielOp.isPresent()) {
            materiel = materielOp.get();
        } else {
            throw new ResourceNotFoundException("Materiel not found");
        }
        return materiel;
    }

    public List<Materiel> getAllMateriel() {
        return materielRepository.findAll();
    }

    public Materiel getMaterielById(Long id) {
        Optional<Materiel> materielOp = materielRepository.findById(id);
        Materiel materiel = null;
        if (materielOp.isPresent()) {
            materiel = materielOp.get();
        } else {
            throw new ResourceNotFoundException("Materiel not found");
        }
        return materiel;
    }

    public Materiel addMateriel(Integer id, Materiel materiel) {
        UserInfo user = userInfoService.getUserById(id);
        materiel.setUserInfo(user);
        return materielRepository.save(materiel);

    }

    public String deleteMateriel(Long id) {
        materielRepository.deleteById(id);
        return "materiel supprimé";
    }

    public List<Materiel> getMaterielByUser(String user) {
        return materielRepository.getMaterielByUserName(user);
    }

    public List<Materiel> getMaterielByDivision(String division) {
        return materielRepository.getMaterielByDivDuService(division);
    }

    public List<Materiel> getMaterielByType(String type) {
        return materielRepository.getMaterielByType(type);
    }

    public List<Materiel> getMaterielByDispo(Boolean dispo) {
        return materielRepository.getMaterielByDisponibilite(dispo);
    }

    public List<Materiel> getMaterielByEtat(String etat) {
        return materielRepository.getMaterielByEtat(etat);
    }

    public Materiel updateMateriel(Long id, Materiel newMat) {
        Materiel materiel = materielRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        materiel.setEtat(newMat.getEtat());
        materiel.setDispo(newMat.getDispo());
        materiel.setType(newMat.getType());
        materiel.setDateAchat(newMat.getDateAchat());
        materiel.setDateAffectation(newMat.getDateAffectation());
        materiel.setUserInfo(newMat.getUserInfo());
        materiel.setMarqueEtRef(newMat.getMarqueEtRef());

        return materielRepository.save(materiel);
    }

    public Page<Materiel> findMaterielsWithPaginationAndSortingByType(int offset, int pageSize, String type) {
        Page<Materiel> materiels = materielRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(type)));
        return materiels;
    }

    public List<Materiel> getMaterialsByType(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Materiel> pagedResult = materielRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Materiel>();
        }
    }
}
