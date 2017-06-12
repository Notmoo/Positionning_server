package servlet;

import core.repository.Location;
import core.service.PositioningService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;

import static java.lang.Thread.sleep;

/**
 * Created by Guillaume on 15/05/2017.
 */
public class PositioningServlet extends HttpServlet{
    @Override
    public void service (final HttpServletRequest servletRequest, final HttpServletResponse servletResponse)
            throws ServletException, IOException {
    
        
        String clientMacAddr = servletRequest.getParameter("CLIENT_MAC_ADDR");
        PositioningService posServ = new PositioningService();
        
        multicastPositioningRequest(clientMacAddr);
        
        
        Location loc = null;
        do{
            try{
                sleep(500l);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
    
            loc = posServ.getLocation(clientMacAddr);
        }while(loc==null);
        
        servletResponse.setContentType("application/json");
        servletResponse.getWriter().print(String.format("{\"x\": \"%f\", \"y\": \"%f\", \"map\": \"%d\"}", loc.getX(), loc.getY(), loc.getMap().getId()));
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
    
    private void multicastPositioningRequest(String clientMacAddress){
        try (DatagramSocket socket = new DatagramSocket(4445)){
            
            byte[] buf = new byte[256];
        
            String dString = "{\"LOCATE\": \"" + clientMacAddress + "\", \"SERV\": \"" + Inet4Address.getLocalHost().getHostAddress()+"\"}";
            buf = dString.getBytes();
        
            // send it
            InetAddress group = InetAddress.getByName("10.255.255.255");
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
