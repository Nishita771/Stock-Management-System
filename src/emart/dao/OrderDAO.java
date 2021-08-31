/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.dao;

import emart.dbutil.DBConnection;
import emart.pojo.ProductsPojo;
import emart.pojo.UserProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author DELL
 */
public class OrderDAO {
    public static String getNextOrderId() throws SQLException
    {
      Connection conn=DBConnection.getConnection();
      Statement st=conn.createStatement();
      ResultSet rs=st.executeQuery("Select max(order_id) from orders");
      rs.next();
      String ordId=rs.getString(1);
      if(ordId==null)
      {
          return "O-101";
      }
      int ordno=Integer.parseInt(ordId.substring(2));
      ordno++;
      return "O-"+ordno;
    }
    public static boolean addOrder(ArrayList<ProductsPojo> al,String oid)throws SQLException
    {
      Connection conn=DBConnection.getConnection();
      PreparedStatement ps=conn.prepareStatement("Insert into orders values(?,?,?,?)");
      int count=0;
      for(ProductsPojo p: al)
      {
          ps.setString(1,oid);
          ps.setString(2,p.getProductId());
          ps.setInt(3,p.getQuantity());
          ps.setString(4,UserProfile.getUserid());
          count+=ps.executeUpdate();
      }
      return count==al.size();
      
    }

    public static Set<String> getOrderId()throws SQLException
    {
      Connection conn=DBConnection.getConnection();
      Statement st=conn.createStatement();     
      ResultSet rs=st.executeQuery("Select order_id from orders");
      Set<String> list=new HashSet<>();
      while(rs.next())
      {
          list.add(rs.getString(1));
      }
      return list;
       
      }
      public static ArrayList<ProductsPojo> getProductDetailsByOrderId(String oid)throws SQLException
      {
      Connection conn=DBConnection.getConnection();
      PreparedStatement ps=conn.prepareStatement("Select quantity,p_id from orders where order_id=?");
      ps.setString(1,oid);
      ResultSet rs=ps.executeQuery();      
      ArrayList<ProductsPojo> list=new ArrayList<>();
      while(rs.next())
      {
         String pid=rs.getString(2);
         int quan=rs.getInt(1);
         PreparedStatement psn=conn.prepareStatement("select * from products where p_id=?");
         ps.setString(1,pid);
         ResultSet r=ps.executeQuery();
         r.next();
         ProductsPojo p=new ProductsPojo();
         p.setProductId(pid);
         p.setProductName(r.getString(2));
         p.setProductCompany(r.getString(3));
         p.setProductPrice(r.getDouble(4));
         p.setOurPrice(r.getDouble(5));
         p.setTax(r.getInt(6));
         p.setQuantity(quan);
         double total=quan*r.getDouble(5)*r.getInt(6)/100;
         p.setTotal(total);
         list.add(p);      
      }
      return list;
     
    }
}

