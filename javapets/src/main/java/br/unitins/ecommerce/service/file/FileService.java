package br.unitins.ecommerce.service.file;

import java.io.File;
import java.io.IOException;

public interface FileService {
    
    String salvar(byte[] imagem, String nomeImagem) throws IOException;

    File download(String nomeArquivo); 

}
