package amandaqsena.professor.model;

import amandaqsena.cursos.model.Curso;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Professor extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;
    private String nome;

//    @ManyToMany
//    private Curso curso;
}
