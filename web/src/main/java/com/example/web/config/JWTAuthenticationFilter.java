package com.example.web.config;


import com.example.web.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * token的校验
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 * @author xxm
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String url = request.getRequestURI();
        String header = request.getHeader(JwtUtil.AUTHORIZATION);

        JSONObject json=new JSONObject();
        //跳过不需要验证的路径
        if(null != WebSecurityConfig.AUTH_WHITELIST&&Arrays.asList(WebSecurityConfig.AUTH_WHITELIST).contains(url)){
            chain.doFilter(request, response);
            return;
        }
        if (StringUtils.isBlank(header) || !header.startsWith(JwtUtil.TOKEN_PREFIX)) {
            json.put("codeCheck", false);
            json.put("msg", "Token为空");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request,response);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);

        }catch (ExpiredJwtException e) {
            //json.put("status", "-2");
            json.put("codeCheck", false);
            json.put("msg", "Token已过期");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
            logger.error("Token已过期: {} " + e);
        } catch (UnsupportedJwtException e) {
            //json.put("status", "-3");
            json.put("codeCheck", false);
            json.put("msg", "Token格式错误");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
            logger.error("Token格式错误: {} " + e);
        } catch (MalformedJwtException e) {
            //json.put("status", "-4");
            json.put("codeCheck", false);
            json.put("msg", "Token没有被正确构造");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
            logger.error("Token没有被正确构造: {} " + e.getMessage());
        } catch (SignatureException e) {
            //json.put("status", "-5");
            json.put("codeCheck", false);
            json.put("msg", "Token签名失败");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
            logger.error("签名失败: {} " + e);
        } catch (IllegalArgumentException e) {
            //json.put("status", "-6");
            json.put("codeCheck", false);
            json.put("msg", "Token非法参数异常");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
            logger.error("非法参数异常: {} " + e);
        }catch (Exception e){
            //json.put("status", "-9");
            System.out.println(e.getMessage());
            json.put("codeCheck", false);
            json.put("msg", "Invalid Token");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
            logger.error("Invalid Token " + e.getMessage());
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(
            HttpServletRequest request,HttpServletResponse response)  {
        String token = request.getHeader(JwtUtil.AUTHORIZATION);
        if (token != null) {
            String userID = "";

            try {
                // 解密Token
                userID = JwtUtil.validateToken(token);
                if (StringUtils.isNotBlank(userID)) {
                    return new UsernamePasswordAuthenticationToken(userID, null, new ArrayList<>());
                }
            }catch (ExpiredJwtException e) {
                throw e;
                //throw new TokenException("Token已过期");
            } catch (UnsupportedJwtException e) {
                throw e;
                //throw new TokenException("Token格式错误");
            } catch (MalformedJwtException e) {
                throw e;
                //throw new TokenException("Token没有被正确构造");
            } catch (SignatureException e) {
                throw e;
                //throw new TokenException("签名失败");
            } catch (IllegalArgumentException e) {
                throw e;
                //throw new TokenException("非法参数异常");
            }catch (Exception e){
                throw e;
                //throw new IllegalStateException("Invalid Token. "+e.getMessage());
            }
            return null;
        }
        return null;
    }

    public static Integer getUID(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer uid = Integer.parseInt((String) auth.getPrincipal());

        return uid;
    }
}

