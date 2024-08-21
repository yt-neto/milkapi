package com.oficinadobrito.api.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "tb_recolhimentos")
public class Recolhimento implements Serializable, Comparable<Recolhimento> {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long recolhimentoId;

	private LocalDate data;

	@Column(length = 300)
	private String local;

	private int quantidade;

	private double kilometros;

	@ManyToOne
	@JoinColumn(name = "vendedorId")
	private Vendedor vendedor;

	@ManyToOne
	@JoinColumn(name = "gerenteId")
	private Gerente gerente;

	@ManyToMany(mappedBy = "recolhimentos")
	private List<Produto> produtos;

	@ManyToMany(mappedBy = "recolhimentos")
	private List<Optimization>  optimizations;

	public Recolhimento() {

		produtos = new ArrayList<Produto>();
		optimizations = new ArrayList<Optimization>();
	}

	@Override
	public int compareTo(Recolhimento outroRecolhimento) {
		return Long.compare(this.recolhimentoId, outroRecolhimento.recolhimentoId);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Recolhimento recolhimento = (Recolhimento) o;
		return recolhimentoId == recolhimento.recolhimentoId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(recolhimentoId);
	}
}
