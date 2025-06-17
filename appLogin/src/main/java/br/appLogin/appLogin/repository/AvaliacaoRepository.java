package br.appLogin.appLogin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.appLogin.appLogin.model.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
	
	List<Avaliacao> findByCarroId(Long carroId);
	List<Avaliacao> findByUsuarioId(Long usuarioId);

}
