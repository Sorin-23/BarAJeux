package projet_groupe4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet_groupe4.dao.IDAOEmploye;
import projet_groupe4.model.Employe;

@Service
public class EmployeService {

    @Autowired
    private IDAOEmploye daoEmploye;

    
    public Employe getEmployeByMail(String mail) {
        return daoEmploye.findByMail(mail)
                .orElseThrow(() -> new RuntimeException("Logged in employee not found in DB"));
    }

    
    public Employe updateMyProfile(String currentPrincipalEmail, Employe updates) {
        
        Employe existing = getEmployeByMail(currentPrincipalEmail);

        
        existing.setNom(updates.getNom());
        existing.setPrenom(updates.getPrenom());
        existing.setTelephone(updates.getTelephone());
        existing.setJob(updates.getJob()); 
        
       
        if (updates.getMdp() != null && !updates.getMdp().isEmpty()) {
            existing.setMdp(updates.getMdp()); 
        }

        return daoEmploye.save(existing);
    }
}