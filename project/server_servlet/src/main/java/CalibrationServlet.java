import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Guillaume on 15/05/2017.
 */
public class CalibrationServlet extends HttpServlet{
    @Override
    public void service (final HttpServletRequest servletRequest, final HttpServletResponse servletResponse)
            throws ServletException, IOException {
        //TODO
    }
    
    @Override
    public void doGet(final HttpServletRequest servletRequest, final HttpServletResponse servletResponse)
            throws ServletException, IOException {
        service(servletRequest, servletResponse);
    }
    
    @Override
    public void doPost(final HttpServletRequest servletRequest, final HttpServletResponse servletResponse)
            throws ServletException, IOException {
        service(servletRequest, servletResponse);
    }
}
