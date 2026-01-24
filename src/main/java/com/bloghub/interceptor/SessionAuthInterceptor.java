package com.bloghub.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component// Session authentication interceptor to check user sessions
public class SessionAuthInterceptor implements HandlerInterceptor {// Implement preHandle, postHandle, afterCompletion methods as needed


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //Step1: get the session (if it exists)
        //false= don't create a new session if one doesn't exist
        HttpSession session = request.getSession(false);

        //debug
        System.out.println("Path:" + request.getRequestURI());
        System.out.println("method:" + request.getMethod());
        System.out.println("Session exists?:" + (session != null));
        if (session != null) {
            System.out.println("id:" + session.getId());
            System.out.println("UserId:" + session.getAttribute("userId"));

        }

        //Step2: check if user is logged in
        //if no session or no userId in session=  user is not logged in
        if (session == null || session.getAttribute("userId") == null) {
            //Send error message and Stop the request processing
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//401
            response.setContentType("application/json");

            response.getWriter().write("{\"error\":\"Unauthorized: Please log in to access this resource.\"}");
            return false;//stop further processing
        }

        //Step3: user is logged in, allow the request to proceed get their info from session if needed
        Long userId = (Long) session.getAttribute("userId");
        String userRole = (String) session.getAttribute("userRole");
        //You can set these details in a ThreadLocal or Request attribute for later use in controllers

        //Step4: Pass user info to the Controller
        request.setAttribute("currentUserId", userId);
        request.setAttribute("currentUserRole", userRole);

        //Step5: Check if this endpoint requires ADMIN role
        String path = request.getRequestURI();
        String method = request.getMethod();
        // Allow OPTIONS (Swagger + CORS)
        if ("OPTIONS".equalsIgnoreCase(method)) {
            return true;
        }

        //Categories: GET is Allowed for all authenticated users, but POST?PUT?DELETE Requires ADMIN
        if (path.startsWith("/api/categories")) {
            if (!method.equals("GET") && !"ADMIN".equals(userRole)) {
                //Only Admin can Create/update /delete categories
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);//403
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"You do not have permission to perform this action.\"}");
                return false;//stop further processing


            }
        }
        //Step6 EveryThing is ok! let the request continue to controller
        return true;//true= continue to controller

    }

}
