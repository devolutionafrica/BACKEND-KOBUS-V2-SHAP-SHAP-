package com.nsia.cobus.domain.port;

import java.util.Map;

public interface ConventionRepositoryPort {

    public Map<String, ?> getAllConventions(String username);

    public Map<String, Object> getAllContratForConvention(String username, int conventionId);

    public Object getAvisSituationForConvention(String username, String convention, String annee);

}
