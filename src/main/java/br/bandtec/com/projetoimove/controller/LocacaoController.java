package br.bandtec.com.projetoimove.controller;


import br.bandtec.com.projetoimove.domains.Bicicleta;
import br.bandtec.com.projetoimove.domains.Locacao;
import br.bandtec.com.projetoimove.domains.Usuario;
import br.bandtec.com.projetoimove.repository.BicicletaRepository;
import br.bandtec.com.projetoimove.repository.LocacaoRepository;
import br.bandtec.com.projetoimove.repository.UsuarioRepository;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/locacao")
public class LocacaoController {

    @Autowired
    private LocacaoRepository repository;

    @Autowired
    private BicicletaRepository bicicletaRepository;

    @Autowired
    private UsuarioRepository repositoryUser;

    @PostMapping("/cadastrar-locacao")
    public ResponseEntity cadastrarLocacao(@RequestBody Locacao locacao) {
        List<Locacao> locacaoB = repository.findAll();

        for (Locacao l : locacaoB) {
            if (l.getBicicleta() != null) {
                if (l.getBicicleta().getId().equals(locacao.getBicicleta().getId()))
                    return ResponseEntity.status(404).body("Bicicleta não disponivel");
            }
        }

        Bicicleta b = bicicletaRepository.getById(locacao.getBicicleta().getId());
        b.setAlocada(true);

        Usuario u = repositoryUser.getById(locacao.getUsuarioLocatario().getId());

        if (u.getSaldo() < locacao.getUsuarioLocatario().getSaldo())
            return ResponseEntity.status(404).body("Não há saldo disponivel");
        else
            u.setSaldo(u.getSaldo() - locacao.getUsuarioLocatario().getSaldo());


        bicicletaRepository.save(b);
        repository.save(locacao);
        repositoryUser.save(u);
        return ResponseEntity.status(201).body(locacao.getId());
    }

    @GetMapping("/consultar-locacao/{id}")
    public ResponseEntity consultarLocacao(@PathVariable int id) {
        Optional<Locacao> locacao = repository.findById(id);
        return ResponseEntity.status(200).body(locacao);
    }

    @GetMapping("/consultar-locacao-uso/{id}")
    public ResponseEntity consultarLocacaoUso(@PathVariable int id) {
        List<Locacao> locacao = repository.findAll();
        List<Locacao> lista = new ArrayList<>();

        for (Locacao l : locacao) {
            if (l.getUsuarioLocatario().getId().equals(id)) {
                lista.add(l);
            }
        }
        if (lista.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(locacao);
    }

    @GetMapping("/consultar-locacao-uso-ultima/{id}")
    public ResponseEntity consultarLocacaoUsoUltima(@PathVariable int id) {
        List<Locacao> locacao = repository.findAll();
        Locacao ultima = new Locacao();

        if (locacao.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        for (Locacao l : locacao) {
            if (l.getUsuarioLocatario().getId().equals(id)) {
                ultima = l;
            }
        }

        if (ultima != null)
            return ResponseEntity.status(200).body(ultima);
        else
            return ResponseEntity.status(404).build();

    }

    @GetMapping("/consultar-locacao-total/{id}")
    public ResponseEntity totalLocacaoUsoUltima(@PathVariable int id) {
        List<Locacao> locacao = repository.findAll();
        int ultima = 0;

        for (Locacao l : locacao) {
            if (l.getUsuarioLocatario().getId().equals(id)) {
                ultima++;
            }
        }

        return ResponseEntity.status(200).body(ultima);

    }

    @GetMapping("/consultar-locacao-locador/{id}")
    public ResponseEntity consultarLocador(@PathVariable int id) {
        List<Locacao> locacao = repository.findAll();

        for (Locacao l : locacao) {
            if (l.getBicicleta().getUsuario().getId().equals(id)) {
                return ResponseEntity.status(200).body(l);
            }
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("cancelar/{id}")
    public ResponseEntity removerLocacao(@PathVariable int id) {
        if (repository.existsById(id)) {
            Locacao locacao = repository.getById(id);
            repository.deleteById(id);
            Bicicleta b = bicicletaRepository.getById(locacao.getBicicleta().getId());
            b.setAlocada(false);
            bicicletaRepository.save(b);
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }
}
