package amandaqsena.professor;

import amandaqsena.professor.dto.ProfessorResponseDto;
import amandaqsena.professor.model.Professor;
import amandaqsena.professor.dto.ProfessorRequestDto;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/professores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfessorController {

    final private ProfessorRepository repositorio;

    public ProfessorController(ProfessorRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GET
    public List<ProfessorResponseDto> buscarProfessors(@QueryParam("prefix") Optional<String> prefix) {

        return prefix
                .map(s -> repositorio
                        .findComPrefixo(s)
                        .stream()
                        .map(ProfessorResponseDto::from)
                        .collect(Collectors.toList()))
                .orElseGet(() -> Professor
                        .findAll()
                        .list()
                        .stream()
                        .map(it -> (Professor) it)
                        .map(ProfessorResponseDto::from)
                        .collect(Collectors.toList()));
    }

    @GET
    @Path("/{id}")
    public ProfessorResponseDto encontrarProfessor(@PathParam("id") final int id) {
        return ProfessorResponseDto.from((Professor) Professor
                .findByIdOptional(id)
                .orElseThrow(NotFoundException::new));
    }

    @POST
    @Transactional
    public ProfessorResponseDto criarProfessor(final ProfessorRequestDto request) {

        Professor Professor = new Professor();
        Professor.setNome(request.getNome());

        repositorio.persist(Professor);
        return ProfessorResponseDto.from(Professor);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void apagarProfessor(@PathParam("id") int id) {
        Professor.findByIdOptional(id)
                .orElseThrow(NotFoundException::new).delete();
    }

    @PATCH
    @Path("/{id}")
    @Transactional
    public ProfessorResponseDto atualizarProfessor(
            @PathParam("id") int id,
            final ProfessorRequestDto request) {
        Professor professor = (Professor) Professor.findByIdOptional(id).orElseThrow(NotFoundException::new);
        professor.setNome(request.getNome());
        professor.persist();
        return ProfessorResponseDto.from(professor);
    }

}
