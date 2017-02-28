package model.common.session;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import model.identity.User;


public class SessionData
{
	private User user = null;
	private String token = "";
	private Date lastLogin = null;
	
	
	/* ********************************
	 * 
	 */
	public SessionData()
    {
    }
	
	/* ********************************
     * 
     */
    public SessionData(User user)
	{
		if(user != null)
		{
		    this.user = user;
		    this.lastLogin = user.getLastLogin();
		    
		    if(user.getLastLogin() != null)
	            this.lastLogin = user.getLastLogin();
		    else
		        this.lastLogin = new Date(System.currentTimeMillis());
		}
	}
	
    
	public String getToken()
    {
        return token;
    }


    public void setToken( String token )
    {
        this.token = token;
    }


	
	public User getUser()
	{
	    return this.user;
	}


    public Date getLastLogin()
    {
        return lastLogin;
    }


    public void setLastLogin( Date lastLogin )
    {
        this.lastLogin = lastLogin;
    }
    
    
    public String getUsername()
    {
        return this.user.getUserName();
    }

    public long getId()
    {
        return this.user.getId();
    }
    
    
    public List<GrantedAuthority> getAuthorities()
    {
        return this.user.getAuthorities();
    }



}
