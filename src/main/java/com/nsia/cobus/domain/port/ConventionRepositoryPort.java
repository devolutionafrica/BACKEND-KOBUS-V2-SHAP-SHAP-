package com.nsia.cobus.domain.port;

import java.util.List;
import java.util.Map;

import com.nsia.cobus.domain.models.Contract;

public interface ConventionRepositoryPort {

    public Map<String, ?> getAllConventions(String username);

    public Map<String, Object> getAllContratForConvention(String username, int conventionId);

}
