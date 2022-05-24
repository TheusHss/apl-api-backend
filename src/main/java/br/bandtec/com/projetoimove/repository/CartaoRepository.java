package br.bandtec.com.projetoimove.repository;

import br.bandtec.com.projetoimove.domains.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Integer> {
}
