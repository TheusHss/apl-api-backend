package br.bandtec.com.projetoimove.controller;

import br.bandtec.com.projetoimove.domains.Bicicleta;
import br.bandtec.com.projetoimove.domains.Cartao;
import br.bandtec.com.projetoimove.domains.Locacao;
import br.bandtec.com.projetoimove.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cartao")
public class CartaoController {

    @Autowired
    private CartaoRepository CartaoRepository;

    @PostMapping
    public ResponseEntity cadastrarCartao(@RequestBody Cartao cartao) {
        if (cartao.getPreferencial()){
            List<Cartao> cartoes = CartaoRepository.findAll();
            for (Cartao c : cartoes){
                if (c.getPreferencial()){
                   c.setPreferencial(false);
                    CartaoRepository.save(c);
                }
            }

        }
        CartaoRepository.save(cartao);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removerCartao(@PathVariable int id) {
        CartaoRepository.deleteById(id);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity consultarCartao(@PathVariable String cpf){
        List<Cartao> cartoes = CartaoRepository.findAll();

        if (cartoes.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            List<Cartao> cartoesRetorno = new ArrayList<>();

            for (Cartao c : cartoes){
                if (c.getCpf().equals(cpf)){
                    Cartao cartaoModificado = new Cartao();
                    cartaoModificado.setId(c.getId());
                    cartaoModificado.setValidade(c.getValidade());
                    cartaoModificado.setNome(c.getNome());
                    cartaoModificado.setNumero(c.getNumero().substring(12, 16));
                    cartaoModificado.setPreferencial(c.getPreferencial());
                    cartoesRetorno.add(cartaoModificado);
                }
            }

            return ResponseEntity.status(200).body(cartoesRetorno);
        }
    }

    @GetMapping("/preferencial/{cpf}")
    public ResponseEntity consultarCartaoPreferencia(@PathVariable String cpf){
        List<Cartao> cartoes = CartaoRepository.findAll();

        if (cartoes.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            List<Cartao> cartoesRetorno = new ArrayList<>();

            for (Cartao c : cartoes){
                if (c.getCpf().equals(cpf) && c.getPreferencial()){
                    Cartao cartaoModificado = new Cartao();
                    cartaoModificado.setId(c.getId());
                    cartaoModificado.setValidade(c.getValidade());
                    cartaoModificado.setNome(c.getNome());
                    cartaoModificado.setNumero(c.getNumero().substring(12, 16));
                    cartaoModificado.setPreferencial(c.getPreferencial());
                    cartoesRetorno.add(cartaoModificado);
                }
            }

            return ResponseEntity.status(200).body(cartoesRetorno);
        }
    }

}
