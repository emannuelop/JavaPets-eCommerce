package br.unitins.ecommerce.service.hash;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HashImplService implements HashService {

    private String salt = "#blahxyz17";
    private Integer iterationCount = 405;
    private Integer keyLength = 512;

    @Override
    public String getHashSenha(String senha) {
        
        try {

            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(
                            new PBEKeySpec(senha.toCharArray(), salt.getBytes(), iterationCount, keyLength))
                    .getEncoded();

            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {

            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        HashService service = new HashImplService();
        System.out.println();
        System.out.println(service.getHashSenha("joao1234"));
        System.out.println(service.getHashSenha("senha1234"));
        System.out.println(service.getHashSenha("pa1000ulo"));
        System.out.println(service.getHashSenha("julia1234"));
        System.out.println(service.getHashSenha("lucas890"));
        System.out.println(service.getHashSenha("JohnDev"));
    }
}
