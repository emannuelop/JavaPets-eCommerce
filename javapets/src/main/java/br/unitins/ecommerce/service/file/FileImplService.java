package br.unitins.ecommerce.service.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import br.unitins.ecommerce.model.produto.produto.Produto;
import br.unitins.ecommerce.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FileImplService implements FileService {

    // ex. /user/janio/quarkus/images/produto/
    private final String PATH_USER = System.getProperty("user.home")
            + File.separator + "quarkus"
            + File.separator + "images"
            + File.separator + "produto" + File.separator;


    @Inject
    ProdutoRepository produtoRepository;

    @Override
    @Transactional
    public void salvar(Long id, String nomeImagem, byte[] imagem)  throws IOException {
        Produto produto = produtoRepository.findById(id);

        try {
            String novoNomeImagem = salvarImagem(nomeImagem, imagem);
            produto.setNomeImagem(novoNomeImagem);
            // excluir a imagem antiga (trabalho pra quem????)
        } catch (IOException e) {
            throw e;
        }
    }
        


    public String salvarImagem(String nomeImagem, byte[] imagem) throws IOException {

        // verificando o tipo da imagem
        String mimeType = Files.probeContentType(new File(nomeImagem).toPath());

        List<String> listMimeType = Arrays.asList("image/jpg", "image/jpeg", "image/png", "image/gif");

        if (!(listMimeType.contains(mimeType))) {

            throw new IOException("Tipo de imagem não suportada.");
        }

        // verificando o tamanho do arquivo, não permitor maior que 10 megas
        if (imagem.length > (1024 * 1024 * 10))
            throw new IOException("Arquivo muito grande.");

        // criando as pastas quando não existir
        File diretorio = new File(PATH_USER);

        if (!diretorio.exists())
            diretorio.mkdirs();

        // gerando o nome do arquivo
        String nomeArquivo = UUID.randomUUID()
                + "." + mimeType.substring(mimeType.lastIndexOf("/") + 1);

        String path = PATH_USER + nomeArquivo;

        // salvando o arquivo
        File file = new File(path);

        // alunos (melhorar :)
        if (file.exists())
            throw new IOException("O nome gerado da imagem está repedido.");

        // criando um arquivo no S.O.
        file.createNewFile();

        FileOutputStream fos = new FileOutputStream(file);

        fos.write(imagem);

        // garantindo o envio do ultimo lote de bytes
        fos.flush();

        fos.close();

        return nomeArquivo;
    }

    @Override
    public File download(String nomeArquivo) {

        File file = new File(PATH_USER + nomeArquivo);

        return file;
    }

}
