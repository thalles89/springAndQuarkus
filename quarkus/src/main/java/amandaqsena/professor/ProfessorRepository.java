package amandaqsena.professor;

import amandaqsena.disciplinas.model.Disciplina;
import amandaqsena.professor.model.Professor;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ProfessorRepository implements PanacheRepositoryBase<Professor, Integer> {
    public List<Professor> findComPrefixo(String prefixo) {
        return find("nome like ?1", prefixo + "%").list();
    }
}
