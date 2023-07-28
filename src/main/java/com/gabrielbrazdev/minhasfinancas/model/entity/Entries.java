package com.gabrielbrazdev.minhasfinancas.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;



import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.gabrielbrazdev.minhasfinancas.model.enums.StatusEntry;
import com.gabrielbrazdev.minhasfinancas.model.enums.TypeEntry;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "lancamento", schema = "financas")
@Builder
@Data
public class Entries {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private User usuario;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "mes")
	private Integer mes;

	@Column(name = "ano")
	private Integer ano;

	@Column(name = "tipo")
	@Enumerated(value = EnumType.STRING)
	private TypeEntry tipo;

	@Column(name = "status")
	@Enumerated(value = EnumType.STRING)
	private StatusEntry status;

	@Column(name = "valor")
	private BigDecimal valor;

	@Column(name = "data_cadastro")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dataCadastro;

}
