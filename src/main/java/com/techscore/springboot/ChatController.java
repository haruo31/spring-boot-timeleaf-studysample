package com.techscore.springboot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChatController {

    @GetMapping(value = "/ChatScreen")
    public String showChatScreen(@ModelAttribute ChatMessage chat, Map<String, Object> mav) {
        // データベース接続と結果取得のための変数
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        System.out.println("処理到達1");

        // リスト表示
        List<String> chatList = new ArrayList<String>();

        try {
            con = DriverManager.getConnection(Application.H2_URL);
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from chatrec;");

            while (rs.next()) {
                String message = rs.getString("message");
                System.out.println(rs.getString("message"));
                chatList.add(message);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        mav.put("chatList", chatList);
        mav.put("newMessage", "");
        return "ChatScreen";
    }

    // インサート
    @PostMapping(value = "/ChatScreen")
    public String send1(@ModelAttribute ChatMessage chat, Map<String, Object> mav) {

        // データベース接続と結果取得のための変数
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        System.out.println("処理到達2");

        System.out.println(chat.getMessage());

        try {
            con = DriverManager.getConnection(Application.H2_URL);
            // データベースへのインサート
            String sql = "insert into chatrec (message) values (?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, chat.getMessage());
            pstmt.executeUpdate();

            System.out.println("処理到達3");

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return showChatScreen(chat, mav);
    }
}