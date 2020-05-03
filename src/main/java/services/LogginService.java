package services;

import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import model.entities.Usuario;
import spark.Request;

import javax.naming.AuthenticationException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class LogginService {
    private static LogginService instace;
    private static UsuarioService usuarioService = UsuarioService.getInstance();

    private LogginService() {

    }

    public static LogginService getInstance() {
        if (instace == null) {
            return new LogginService();
        }
        return null;
    }

    public Usuario getActualUser(Request req) throws AuthenticationException {
        try {
            String token = req.headers("token");
            int idUsuario = Integer.valueOf(decodeToken(token));
            return (Usuario) usuarioService.findById(idUsuario);

        } catch (Exception e) {
            throw new AuthenticationException();
        }

        // return (Usuario) usuarioService.findById(1).get();
    }

    public String codeToken(String userId) {

        // Build an HMAC signer using a SHA-256 hash
        Signer signer = HMACSigner.newSHA256Signer("julian papa");

        JWT jwt = new JWT().setIssuer("guardarropasApp")
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setSubject(userId)
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusYears(2));

        return JWT.getEncoder().encode(jwt, signer);
    }

    public String decodeToken(String token) {
        Verifier verifier = HMACVerifier.newVerifier("julian papa");
        JWT jwt = JWT.getDecoder().decode(token, verifier);
        return jwt.subject;
    }

}
