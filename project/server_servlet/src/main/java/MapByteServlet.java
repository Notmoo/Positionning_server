import repository.Map;
import service.MapService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Guillaume on 29/05/2017.
 */
public class MapByteServlet extends HttpServlet {
    @Override
    public void service (final HttpServletRequest servletRequest, final HttpServletResponse servletResponse)
            throws ServletException, IOException {
        
        MapService mapService = new MapService();
        int mapId = Integer.parseInt(servletRequest.getParameter("MAP_ID"));
        Map map = mapService.getMap(mapId);
        
        servletResponse.setContentType("image/png");
        servletResponse.getWriter().print(map.getContent());
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