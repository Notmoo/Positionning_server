package servlet;

import core.repository.Map;
import core.service.MapService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet designed to access to map scale, which can be use to convert real-life coordinates to on-screen coordinates. This kind of request is usually sent by clients.
 *
 * Created by Guillaume on 29/05/2017.
 */
public class MapScaleServlet extends HttpServlet {
        @Override
        public void service (final HttpServletRequest servletRequest, final HttpServletResponse servletResponse)
                throws ServletException, IOException {
    
            MapService mapService = new MapService();
            int mapId = Integer.parseInt(servletRequest.getParameter("MAP_ID"));
            Map map = mapService.getMap(mapId);
            
            servletResponse.setContentType("application/json");
            servletResponse.getWriter().print(String.format("{\"x1\":\"%f\",\"y1\":\"%f\",\"x2\":\"%f\",\"y2\":\"%f\"}", map.getX_topLeft()
                    , map.getY_topLeft(), map.getX_bottomRight(), map.getY_bottomRight()));
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
