package br.unitins.ecommerce.service.produto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.itextpdf.commons.actions.IEvent;
import com.itextpdf.commons.actions.IEventHandler;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;
import br.unitins.ecommerce.dto.produto.ProdutoDTO;
import br.unitins.ecommerce.dto.produto.ProdutoResponseDTO;
import br.unitins.ecommerce.model.produto.produto.Produto;
import br.unitins.ecommerce.repository.CategoriaRepository;
import br.unitins.ecommerce.repository.FornecedorRepository;
import br.unitins.ecommerce.repository.MarcaRepository;
import br.unitins.ecommerce.repository.ProdutoRepository;
import br.unitins.ecommerce.service.avaliacao.AvaliacaoService;
import br.unitins.ecommerce.service.usuario.UsuarioService;

@ApplicationScoped
public class ProdutoImplService implements ProdutoService {

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    MarcaRepository marcaRepository;

    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    CategoriaRepository categoriaRepository;

    @Inject
    AvaliacaoService avaliacaoService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    Validator validator;

    // @Override
    // public List<ProdutoResponseDTO> getAll() {

    // return produtoRepository.findAll()
    // .stream()
    // .map(ProdutoResponseDTO::new)
    // .toList();
    // }

    @Override
    public List<ProdutoResponseDTO> getAll(int page, int pageSize) {
        List<Produto> list = produtoRepository.findAll().page(page, pageSize).list();

        return list.stream().map(e -> ProdutoResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    @Override
    public ProdutoResponseDTO getById(Long id) throws NotFoundException {

        Produto produto = produtoRepository.findById(id);

        if (produto == null)
            throw new NotFoundException("Não encontrado");

        return ProdutoResponseDTO.valueOf(produto);
    }

    @Override
    @Transactional
    public ProdutoResponseDTO insert(@Valid ProdutoDTO produtoDto) throws ConstraintViolationException {

        Produto entity = new Produto();

        entity.setNome(produtoDto.nome());

        entity.setDescricao(produtoDto.descricao());

        entity.setMarca(marcaRepository.findById(produtoDto.idMarca()));

        entity.setFornecedor(fornecedorRepository.findById(produtoDto.idFornecedor()));

        entity.setCategoria(categoriaRepository.findById(produtoDto.idCategoria()));

        entity.setPreco(produtoDto.preco());

        entity.setEstoque(produtoDto.estoque());

        produtoRepository.persist(entity);

        return ProdutoResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public ProdutoResponseDTO update(Long id, @Valid ProdutoDTO produtoDto) throws ConstraintViolationException {

        validar(produtoDto);

        Produto entity = produtoRepository.findById(id);

        entity.setNome(produtoDto.nome());

        entity.setDescricao(produtoDto.descricao());

        entity.setMarca(marcaRepository.findById(produtoDto.idMarca()));

        entity.setFornecedor(fornecedorRepository.findById(produtoDto.idFornecedor()));

        entity.setCategoria(categoriaRepository.findById(produtoDto.idCategoria()));

        entity.setPreco(produtoDto.preco());

        entity.setEstoque(produtoDto.estoque());

        return ProdutoResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public void update(Long id, String nomeImagem) {

        Produto entity = produtoRepository.findById(id);

        if (entity == null)
            throw new NullPointerException("Nenhum café encontrado");

        entity.setNomeImagem(nomeImagem);
    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException {

        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Produto produto = produtoRepository.findById(id);

        avaliacaoService.delete(produto);

        usuarioService.deleteProdutoFromListaDesejo(produto);

        if (produtoRepository.isPersistent(produto))
            produtoRepository.delete(produto);

        else
            throw new NotFoundException("Nenhum ração encontrado");
    }

    @Override
    public Long count() {

        return produtoRepository.count();
    }

    @Override
    public Long countByNome(String nome) {

        return produtoRepository.findByNome(nome).count();
    }

    @Override
    public List<ProdutoResponseDTO> getByNome(String nome, int page, int pageSize) throws NullPointerException {

        List<Produto> list = produtoRepository.findByNome(nome).page(page, pageSize).list();

        if (list == null)
            throw new NullPointerException("nenhum ração encontrado");

        return list.stream().map(e -> ProdutoResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProdutoResponseDTO salveImage(Long id, String nomeImagem) {

        Produto entity = produtoRepository.findById(id);
        entity.setNomeImagem(nomeImagem);

        return ProdutoResponseDTO.valueOf(entity);
    }

    // @Override
    // public List<ProdutoResponseDTO> getByMarca(String nome) throws
    // NullPointerException {

    // List<Produto> list =
    // produtoRepository.findByMarca(marcaRepository.findByNome(nome).get(0));

    // if (list == null)
    // throw new NullPointerException("Nenhuma marca encontrada");

    // return list.stream()
    // .map(ProdutoResponseDTO::new)
    // .collect(Collectors.toList());
    // }

    // @Override
    // public List<ProdutoResponseDTO> filterByPrecoMin(Double preco) throws
    // NullPointerException {

    // List<Produto> list = produtoRepository.filterByPrecoMinimo(preco);

    // if (list == null)
    // throw new NullPointerException("Nenhum ração encontrada");

    // return list.stream()
    // .map(ProdutoResponseDTO::new)
    // .collect(Collectors.toList());
    // }

    // @Override
    // public List<ProdutoResponseDTO> filterByPrecoMax(Double preco) {

    // List<Produto> list = produtoRepository.filterByPrecoMaximo(preco);

    // if (list == null)
    // throw new NullPointerException("Nenhum ração encontrada");

    // return list.stream()
    // .map(ProdutoResponseDTO::new)
    // .collect(Collectors.toList());
    // }

    // @Override
    // public List<ProdutoResponseDTO> filterByEntrePreco(Double precoMin, Double
    // precoMax) {

    // List<Produto> list = produtoRepository.filterByEntrePreco(precoMin,
    // precoMax);

    // if (list == null)
    // throw new NullPointerException("Nenhum ração encontrada");

    // return list.stream()
    // .map(ProdutoResponseDTO::new)
    // .collect(Collectors.toList());
    // }

    private void validar(ProdutoDTO produtoDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<ProdutoDTO>> violations = validator.validate(produtoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    public byte[] gerarPdf(List<Produto> produtos) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (PdfWriter pdfWriter = new PdfWriter(baos);
                PdfDocument pdfDocument = new PdfDocument(pdfWriter);
                Document document = new Document(pdfDocument)) {

            Paragraph title = new Paragraph("Relatório de Produtos")
                    .setFontColor(new DeviceRgb(0, 0, 0)) // Cor preta
                    .setFontSize(18f)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);

            document.add(title);
            LocalDateTime agora = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String dataHoraFormatada = agora.format(formatter);

            Paragraph dataHora = new Paragraph("Gerado em: " + dataHoraFormatada)
                    .setFontColor(new DeviceRgb(128, 128, 128)) // Cor cinza
                    .setFontSize(12f)
                    .setTextAlignment(TextAlignment.CENTER);

            document.add(dataHora);

            Table table = new Table(3)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER).setWidth(UnitValue.createPercentValue(100)); // 3
                                                                                                                     // colunas
                                                                                                                     // para
                                                                                                                     // ID,
                                                                                                                     // Nome
                                                                                                                     // e
                                                                                                                     // Preço

            for (Produto produto : produtos) {
                Text idText = new Text("ID: " + produto.getId())
                        .setFontSize(12f)
                        .setBold();

                Text nomeText = new Text("Nome: " + produto.getNome())

                        .setFontSize(14f)
                        .setItalic();

                Text precoText = new Text("Preço: " + produto.getPreco())

                        .setFontSize(16f);

                // Adicione as células à tabela
                table.addCell(new Cell().add(new Paragraph().add(idText)));
                table.addCell(new Cell().add(new Paragraph().add(nomeText)));
                table.addCell(new Cell().add(new Paragraph().add(precoText)));
            }

            // Adicione a tabela ao documento
            document.add(table);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }

    @Override

    public byte[] createReportProdutos(String filterNome) {
        List<Produto> produtos = produtoRepository.findAll().list();
        return gerarPdf(produtos);
    }

}
