package amandaqsena.professor.dto;

import amandaqsena.cursos.model.Curso;
import amandaqsena.disciplinas.model.Disciplina;
import amandaqsena.professor.model.Professor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ProfessorResponseDto {
    private int id;
    private String nome;

    public static ProfessorResponseDto from(Professor professor) {
        return new ProfessorResponseDto(
                professor.getId(), professor.getNome()
        );
    }
}
