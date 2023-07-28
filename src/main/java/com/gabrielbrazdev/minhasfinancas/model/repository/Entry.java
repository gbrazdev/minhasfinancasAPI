package com.gabrielbrazdev.minhasfinancas.model.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gabrielbrazdev.minhasfinancas.model.enums.StatusEntry;
import com.gabrielbrazdev.minhasfinancas.model.enums.TypeEntry;



public interface Entry extends JpaRepository<Entry, Long>{
	
	@Query( value = 
			  " select sum(l.valor) from Lancamento l join l.usuario u "
			+ " where u.id = :idUsuario and l.tipo =:tipo and l.status = :status group by u " )
	BigDecimal obterSaldoPorTipoLancamentoEUsuarioEStatus(
			@Param("idUsuario") Long idUsuario, 
			@Param("tipo") TypeEntry tipo,
			@Param("status") StatusEntry status);

}
