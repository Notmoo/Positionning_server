package servlet;

import core.service.CalibrationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Random;

/**
 * Created by Guillaume on 15/05/2017.
 */
public class CalibrationServlet extends HttpServlet{

    public void execute (final HttpServletRequest servletRequest, final HttpServletResponse servletResponse)
            throws ServletException, IOException {

        try {
            String clientMacAddr = servletRequest.getParameter("CLIENT_MAC_ADDR");
            int mapId = Integer.parseInt(servletRequest.getParameter("MAP_ID"));
            double posx = Double.parseDouble(servletRequest.getParameter("X"));
            double posy = Double.parseDouble(servletRequest.getParameter("Y"));

            Integer locationId = new CalibrationService().registerLocation(posx, posy, mapId);
            if(locationId!=-1){
                multicastCalibrationRequest(clientMacAddr, locationId);
            }else{
                try{
                    servletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }catch(IllegalStateException ex){
                    ex.printStackTrace();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            try{
                servletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }catch(IllegalStateException ex){
                ex.printStackTrace();
            }
        }
        servletResponse.setStatus(200);
    }
    
    @Override
    public void doGet(final HttpServletRequest servletRequest, final HttpServletResponse servletResponse)
            throws ServletException, IOException {
        execute(servletRequest, servletResponse);
    }
    
    @Override
    public void doPost(final HttpServletRequest servletRequest, final HttpServletResponse servletResponse)
            throws ServletException, IOException {
        execute(servletRequest, servletResponse);
    }

    private void multicastCalibrationRequest(String clientMacAddress, int locationId){
        try (DatagramSocket socket = new DatagramSocket(4445)){

            byte[] buf = new byte[256];

            String dString = "CALIB=" + clientMacAddress + ";SERV=" + Inet4Address.getLocalHost().getHostAddress()+";LOC_ID="+locationId+";";
            buf = dString.getBytes();

            // send it
            InetAddress group = InetAddress.getByName("230.0.0.1");
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
