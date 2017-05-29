import repository.Location;
import repository.Map;
import service.MapService;
import service.PositioningService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static java.lang.Thread.sleep;

/**
 * Created by Guillaume on 29/05/2017.
 */
public class MapScaleServlet extends HttpServlet {
        @Override
        public void service (final HttpServletRequest servletRequest, final HttpServletResponse servletResponse)
                throws ServletException, IOException {
    
            MapService mapService = new MapService();
            int mapId = Integer.parseInt(servletRequest.getParameter("MAP_ID"));
            Map map = mapService.getMap(mapId);
            
            servletResponse.setContentType("text/plain");
            servletResponse.getWriter().print(String.format("{x1:%f;y1:%f;x2:%f;y2:%f}", map.getX_topLeft()
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
