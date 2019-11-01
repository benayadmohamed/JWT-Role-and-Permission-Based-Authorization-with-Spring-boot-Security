package com.configs.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.models.Privilege;
import com.models.User;
import com.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.configs.security.SecurityConstants.HEADER_STRING;
import static com.configs.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtOutils jwtOutils;

    public JWTAuthorizationFilter(AuthenticationManager authManager, JwtOutils jwtOutils) {
        super(authManager);
        this.jwtOutils = jwtOutils;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = null;
            try {
                DecodedJWT decodedJWT = jwtOutils.verify(token);
                user = decodedJWT.getSubject();
                Collection<Privilege> privilegeCollections = new ArrayList<>();
                String[] privileges = decodedJWT.getClaim("privileges").asArray(String.class);
                for (String s : privileges) {
                    privilegeCollections.add(new Privilege(s));
                }
                System.out.println("request = [" + privilegeCollections + "]");
                return new UsernamePasswordAuthenticationToken(user, null, privilegeCollections);
            } catch (AlgorithmMismatchException e) {
                logger.error("Algorithm Mismatch Exception", e);
            } catch (TokenExpiredException e) {
                logger.warn("Token Expired Exception", e);
            } catch (SignatureVerificationException e) {
                logger.error("Signature Verification Exception.");
            }
        }
        return null;
    }
}
