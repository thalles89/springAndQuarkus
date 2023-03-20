package amandaqsena.alunos;

import amandaqsena.alunos.dto.AlunoRequestDto;
import amandaqsena.alunos.dto.AlunoResponseDto;
import amandaqsena.alunos.model.Aluno;
import amandaqsena.alunos.model.AlunoRepositorio;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/alunos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlunosController {
    @Inject
    AlunoRepositorio repositorio;

//    public AlunosController(AlunoRepositorio repositorio) {
//        this.repositorio = repositorio;
//    }

    @GET
    public List<AlunoResponseDto> buscarAlunos(@QueryParam("prefix") Optional<String> prefix) {

        return prefix
                .map(s -> repositorio
                        .findComPrefixo(s)
                        .stream()
                        .map(AlunoResponseDto::from)
                        .collect(Collectors.toList()))
                .orElseGet(() -> Aluno
                        .findAll()
                        .list()
                        .stream()
                        .map(it -> (Aluno) it)
                        .map(AlunoResponseDto::from)
                        .collect(Collectors.toList()));
    }

    @GET
    @Path("/{id}")
    public AlunoResponseDto encontrarAlunoPorId(@PathParam("id") final int id) {
        return AlunoResponseDto.from((Aluno) Aluno
                .findByIdOptional(id)
                .orElseThrow(NotFoundException::new));
    }

    @POST
    @Transactional
    public AlunoResponseDto criarAluno(final AlunoRequestDto request) {
        final Aluno aluno = new Aluno();
        aluno.setNome(request.getNome());
        repositorio.persist(aluno);
        return AlunoResponseDto.from(aluno);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void apagarAluno(@PathParam("id") int id) {
        Aluno.findByIdOptional(id)
                .orElseThrow(NotFoundException::new).delete();
    }

    @Transactional
    @PATCH
    @Path("/{id}")
    public AlunoResponseDto atualizarAluno(@PathParam("id") int id, final AlunoRequestDto request) {
        final Aluno aluno = (Aluno) Aluno
                .findByIdOptional(id)
                .orElseThrow(NotFoundException::new);
        aluno.setNome(request.getNome());
        aluno.persist();
        return AlunoResponseDto.from(aluno);
    }

}
