package br.com.mavidenergy.usecases.impl;

import br.com.mavidenergy.domains.Fornecedor;
import br.com.mavidenergy.gateways.repositories.FornecedorRepository;
import br.com.mavidenergy.gateways.responses.FornecedorPaginadoResponseDTO;
import br.com.mavidenergy.gateways.responses.FornecedorResponseDTO;
import br.com.mavidenergy.usecases.interfaces.BuscarFornecedor;
import br.com.mavidenergy.usecases.interfaces.ConverteFornecedorEmDTO;
import br.com.mavidenergy.usecases.interfaces.ConverteFornecedorPaginadoEmDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuscarFornecedorImpl implements BuscarFornecedor {

    private final FornecedorRepository fornecedorRepository;
    private final PaginarResultados paginarResultados;
    private final ConverteFornecedorPaginadoEmDTO converteFornecedorPaginadoEmDTO;
    private final ConverteFornecedorEmDTO converteFornecedorEmDTO;


    @Override
    public List<Fornecedor> buscarFornecedores() {

        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        if (fornecedores.isEmpty()) {
            throw new RuntimeException("Nenhum fornecedor encontrado");
        }

        return fornecedores;
    }

    @Override
    public List<FornecedorResponseDTO> buscarFornecedorMaisProximo(Double latitude, Double longitude,List<Fornecedor> fornecedores) {

        // Usar o use case
        // Garante que o fornecedor tenha endereço
        // Ordena pelo mais próximo

        return fornecedores.stream()
                .map(fornecedor -> converteFornecedorEmDTO.executa(fornecedor, latitude, longitude)) // Usar o use case
                .filter(fornecedor -> fornecedor.getEndereco() != null) // Garante que o fornecedor tenha endereço
                .sorted(Comparator.comparingDouble(FornecedorResponseDTO::getDistancia)) // Ordena pelo mais próximo
                .toList();
    }

    @Override
    public Page<FornecedorPaginadoResponseDTO> buscarFornecedorMaisProximoPaginado(Double latitude, Double longitude, List<Fornecedor> fornecedores, Pageable pageable) {
        // Utilizar o use case para converter fornecedores
        List<FornecedorPaginadoResponseDTO> fornecedorPaginadoResponseDTOS = fornecedores.stream()
                .map(fornecedor -> converteFornecedorPaginadoEmDTO.executa(fornecedor, latitude, longitude)) // Usar o use case
                .filter(fornecedor -> fornecedor.getEndereco() != null) // Garante que o fornecedor tenha endereço
                .sorted(Comparator.comparingDouble(FornecedorPaginadoResponseDTO::getDistancia)) // Ordena pelo mais próximo
                .collect(Collectors.toList());

        // Paginar os resultados manualmente
        return paginarResultados.paginarResultados(fornecedorPaginadoResponseDTOS, pageable);
    }




}
