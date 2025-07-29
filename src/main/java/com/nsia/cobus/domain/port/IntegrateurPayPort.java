package com.nsia.cobus.domain.port;

import java.util.List;

import com.nsia.cobus.domain.models.IntegrateurPayModel;

public interface IntegrateurPayPort {

    List<IntegrateurPayModel> allIntegrateurs();
}
