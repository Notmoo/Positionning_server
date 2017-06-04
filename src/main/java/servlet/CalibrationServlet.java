package servlet;

import core.service.CalibrationService;

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

        try {
            int locId = Integer.parseInt(servletRequest.getParameter("LOCATION_ID"));
            String apMacAddr = servletRequest.getParameter("AP_MAC_ADDRESS");
            double avg = Double.parseDouble(servletRequest.getParameter("AVG"));
            double stdDev = Double.parseDouble(servletRequest.getParameter("STD_DEV"));

            new CalibrationService().addCalibrationData(locId, apMacAddr, avg, stdDev);
        }catch(Exception e){
            e.printStackTrace();
            try{
                servletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }catch(IllegalStateException ex){
                ex.printStackTrace();
            }
        }
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
