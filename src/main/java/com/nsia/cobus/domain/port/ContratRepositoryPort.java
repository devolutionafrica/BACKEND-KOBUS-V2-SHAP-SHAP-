package com.nsia.cobus.domain.port;

import java.util.List;
// import java.util.Map;
import java.util.Map;

public interface ContratRepositoryPort {

    public List<?> getAllContratByUsername(String username);

    public Map<String, Object> getContratById(String contratId, String username);

    public Object getSinistreForContrat(String contratId, String username);

    public Object getAllCotisationByData(String policeId, String dateDeb, String dateFin);

    public Object getAvisSituationForContract(String codeFiliale, String police, String date);

}
