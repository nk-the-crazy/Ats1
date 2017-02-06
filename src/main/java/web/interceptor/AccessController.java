package web.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class AccessController extends HandlerInterceptorAdapter
{
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler )
            throws Exception
    {
        // String appURI = "/"+SystemSettings.APP_NAME+"/";
        /*
         * 
         * if( !request.getRequestURI().equals(appURI) &&
         * !request.getRequestURI().equals(appURI+"login") &&
         * !request.getRequestURI().equals(appURI+"login_user") &&
         * !request.getRequestURI().equals(appURI+"login.jsp")) {
         * if(request.getSession().getAttribute( "userlData" ) == null) {
         * response.sendRedirect(appURI); return false; } }
         */
        return true;

    }
}
