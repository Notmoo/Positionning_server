import repository.Location;
import service.MeasurementService;
import service.PositioningService;

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
 * Created by Guillaume on 29/05/2017.
 */
public class APMeasurementServlet extends HttpServlet {
    @Override
    public void service (final HttpServletRequest servletRequest, final HttpServletResponse servletResponse)
            throws ServletException, IOException {
        
        String apMacAddr = servletRequest.getParameter("AP_MAC");
        String clientMacAddr = servletRequest.getParameter("CLIENT_MAC");
        Double avg = Double.parseDouble(servletRequest.getParameter("AVG"));
        Double stdDev = Double.parseDouble(servletRequest.getParameter("STD_DEV"));
        
        if(apMacAddr!=null && !apMacAddr.isEmpty() && clientMacAddr!=null && !clientMacAddr.isEmpty() && avg!=null && stdDev!=null) {
            MeasurementService mServ = new MeasurementService();
            mServ.registerMeasurement(apMacAddr, clientMacAddr, avg, stdDev);
            servletResponse.setStatus(200);
        }else{
            servletResponse.setStatus(400);
        }
        
    }
    
    @Override
    public void doGet (final HttpServletRequest servletRequest, final HttpServletResponse servletResponse)
            throws ServletException, IOException {
        service(servletRequest, servletResponse);
    }
    
    @Override
    public void doPost (final HttpServletRequest servletRequest, final HttpServletResponse servletResponse)
            throws ServletException, IOException {
        service(servletRequest, servletResponse);
    }
}