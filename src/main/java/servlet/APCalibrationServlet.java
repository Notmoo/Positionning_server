package servlet;

import core.service.CalibrationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Guillaume on 07/06/2017.
 */
public class APCalibrationServlet extends HttpServlet {

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

    private void execute(final HttpServletRequest servletRequest, final HttpServletResponse servletResponse) throws ServletException, IOException {

        try {
            int locId = Integer.parseInt(servletRequest.getParameter("LOCATION_ID"));
            String apMacAddr = servletRequest.getParameter("AP_MAC_ADDRESS");
            double val = Double.parseDouble(servletRequest.getParameter("VAL"));

            new CalibrationService().addCalibrationData(locId, apMacAddr, val);
            servletResponse.setStatus(200);
        }catch(Exception e){
            e.printStackTrace();
            try{
                servletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }catch(IllegalStateException ex){
                ex.printStackTrace();
            }
        }
    }
}
