package projet_groupe4.dto.request;

import jakarta.validation.constraints.NotBlank;

public class SubcribeEmployeRequest {
    @NotBlank
    private String job;
    
    
	private boolean gameMaster;
    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public boolean isGameMaster() {
        return gameMaster;
    }
    public void setGameMaster(boolean gameMaster) {
        this.gameMaster = gameMaster;
    }

    

}
