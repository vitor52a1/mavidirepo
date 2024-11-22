package br.com.mavidenergy.usecases.interfaces;

import br.com.mavidenergy.domains.Cidade;
import br.com.mavidenergy.gateways.responses.DadosClimaticosResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface BuscarDadosClimaticos {
    Map<String, Object> buscarPorCidade(Cidade cidade);
}
