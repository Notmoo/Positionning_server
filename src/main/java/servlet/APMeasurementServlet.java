package servlet;

import core.service.MeasurementService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet designed to receive registration requests about positioning data.
 *
 * Created by Guillaume on 29/05/2017.
 */
public class APMeasurementServlet extends HttpServlet {
    @Override
    public void service (final HttpServletRequest servletRequest, final HttpServletResponse servletResponse)
            throws ServletException, IOException {
        
        String apMacAddr = servletRequest.getParameter("AP_MAC");
        String clientMacAddr = servletRequest.getParameter("CLIENT_MAC");
        Double val = Double.parseDouble(servletRequest.getParameter("VAL"));
        
        if(apMacAddr!=null && !apMacAddr.isEmpty() && clientMacAddr!=null && !clientMacAddr.isEmpty() && val!=null) {
            MeasurementService mServ = new MeasurementService();
            mServ.registerMeasurement(apMacAddr, clientMacAddr, val);
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