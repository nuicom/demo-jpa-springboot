package com.example.demo.rest;

import com.example.demo.model.GenericRequest;
import com.example.demo.model.GenericResponse;
import com.example.demo.model.LoginUser;
import com.example.demo.model.Token;
import com.example.demo.model.dto.FwUserSecurityBean;
import com.example.demo.repository.DemoQueryRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    @Autowired
    private DemoQueryRepository repository;

    @PostMapping(path = "/user/verify")
    public GenericResponse<Token> getVerify(@NotNull @RequestBody GenericRequest<LoginUser> body) throws Exception{

        boolean isLogin = true;

        if(isLogin){
            //refreshToken
            Token token = new Token();
            token.setRefreshToken(repository.addFwUserToken(body.getRequest().getUserId()));
            GenericResponse response = new GenericResponse();
            response.setResult(token);
            return response;
        }
        return null;
    }

    @GetMapping(path = "/accessToken")
    public GenericResponse<Token> getAccessToken(@RequestHeader(value = "Authorization") String bearerBody) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Token token = new Token();
        boolean isSuccess = bearerBody != null && bearerBody.length() > 7
                && (bearerBody.charAt(0) == 'b' | bearerBody.charAt(0) == 'B') && bearerBody.startsWith("earer ", 1);
        if (isSuccess) {
            final String refreshToken = bearerBody.substring(7);
            final FwUserSecurityBean user = repository.getAccessTokenByRefreshToken(refreshToken);
            if (user != null) {
                final Calendar calendar = Calendar.getInstance();
                final Date now = calendar.getTime();
                calendar.add(Calendar.MINUTE,1);
                final Date expire = calendar.getTime();
                final Claims claims = Jwts.claims().setSubject(user.getUserId());
                claims.put("userId", user.getUserId());
                claims.put("roleId", -1);
                claims.put("type", user.getType());
                final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                final String privateKey = user.getPrivateKey()
                        .replaceAll("([\\s\\r\\n]*\\-+(BEGIN|END) (RSA)* PRIVATE KEY\\-+[\\s\\r\\n]*)|[\\r\\n]+", "");
                final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
                final String accessToken = Jwts.builder().setClaims(claims).setId(refreshToken)
                        .setIssuer(user.getUserId()).setIssuedAt(now).setNotBefore(now).setExpiration(expire)
                        .signWith(SignatureAlgorithm.RS256, keyFactory.generatePrivate(spec)).compact();

//                result.add("accessToken", accessToken).add("type", "Bearer");
                token.setAccessToken(accessToken);
                token.setType("Bearer");
            } else {
                isSuccess = false;
//                MessageUtils.messagesToJsonBuilder(responseBody, Stream
//                        .of(new GenericErrorMessage("BUXX000", "เซสชันหมดอายุ กรุณาเข้าสู่ระบบใหม่อีกครั้ง", null)));
            }
        } else {
//            MessageUtils.messagesToJsonBuilder(responseBody,
//                    Stream.of(new GenericErrorMessage("BUXX000", "รูปแบบการส่งไม่ถูกต้อง", null)));
        }
        GenericResponse response = new GenericResponse();
        response.setResult(token);
        return response;
    }

}
