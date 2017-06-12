package servlet;

import core.repository.Map;
import core.service.MapService;

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
        Byte[] mapBytes = mapService.getMapContent(mapId);
        
        servletResponse.setContentType("image/png");
        servletResponse.getWriter().print(byteToString(mapBytes));
    }

    private String byteToString(Byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for(Byte currByte : bytes){
            sb.append(currByte.toString());
        }
        return sb.toString();
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