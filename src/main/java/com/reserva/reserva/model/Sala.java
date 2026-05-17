package com.reserva.reserva.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "salas")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    // NO PostgreSQL, Oracle e DB2, a anotação abixo autoincrementa o valor de
    // registrado na coluna numero quando uma
    // nova sala é cadastrada
    // @jakarta.persistence.Column(columnDefinition = "GENERATED ALWAYS AS
    // IDENTITY", insertable = false, updatable = false)
    private Integer numero;
    private Integer capacidade = 0;
    private Boolean ativa = true;

    public Sala(String nome) {
        this.nome = nome;
        
    }

    // NO MYSQL autoincrementa o valor de registrado na coluna numero quando uma
    // nova sala é cadastrada
    @PostPersist
    public void posPersist() {
                if (this.numero == null || this.numero == 0 ) {
            // Nota: Como o ID IDENTITY é gerado no banco, para usar o ID exato aqui
            // a alternativa de banco (Opção 2) ou uma consulta prévia é necessária.
            // Para garantir incremento simples sem nulos:
            this.numero = this.id.intValue();
        }
    }

}
