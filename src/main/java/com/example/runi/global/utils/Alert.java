/*
    사용자 정의 함수 모음 클래스
*/

package com.example.runi.global.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class Alert {
    public static void init(HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
    }

    public static void alert(HttpServletResponse response, String alertText) {
        init(response);
        try (PrintWriter out = response.getWriter()) {
            out.println("<script>alert('" + alertText + "');</script> ");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void alertAndMovePage(HttpServletResponse response, String alertText, String nextPage) {
        init(response);
        try (PrintWriter out = response.getWriter()) {
            out.println("<script>alert('" + alertText + "'); location.href='" + nextPage + "';</script> ");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void alertAndBackPage(HttpServletResponse response, String alertText) {
        init(response);
        try (PrintWriter out = response.getWriter()) {
            out.println("<script>alert('" + alertText + "'); history.back();</script>");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
