package service.impl.identity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import common.exceptions.security.InvalidLoginException;
import model.common.session.SessionData;
import service.api.identity.IdentityManager;

@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    //---------------------------------
    
    @Autowired
    IdentityManager identityManager;

    
    /* ********************************************
     * 
     * */
    @Override
    public Authentication authenticate( Authentication authentication ) throws AuthenticationException
    {
        String userName = authentication.getName().trim();
        String password = authentication.getCredentials().toString().trim();
        
        try
        {
            SessionData sessionData = identityManager.loginUser( userName, password);
            
            if(sessionData != null)
            {
                return new UsernamePasswordAuthenticationToken(sessionData, password, sessionData.getUser().getAuthorities() );        
            }
            else
            {
                throw new InvalidLoginException();
            }
        }
        catch(Exception e)
        {
            logger.error( "Authentication Failed for User:" + userName );
            
            return null;
        }
    }
    

    /* ********************************************
     * 
     * */
    @Override
    public boolean supports( Class<?> authentication )
    {
        return authentication.equals(
                        UsernamePasswordAuthenticationToken.class);
    }
    
    

}
