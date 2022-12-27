package demo;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;
public class demo {

    /**
     * 根据userId来查询对应的钱包的余额
     * @param userId
     * @return
     */
    public static BigDecimal getBalance(int userId) {
        //相关的sql语句
        String sql = "select balance from wallet where user_id = ?" ;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //连接数据库
            Connection connection =
                    getConnection("jdbc:mysql://localhost:3306/java_demo?useSSL=true&useUnicode=true&characterEncoding=UTF-8","root","83612457");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //将userId的值赋给sql中的第一个占位符
            preparedStatement.setInt(1, userId);
            //执行sql语句并拿到结果集
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("balance");
                }
            }


        }catch (SQLException e){
            System.out.println("查询时发生错误: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return  null;
    }

    /**
     * 用户消费金额
     * @param userId
     * @param amount
     * @return
     */
    public static  boolean consume(int userId, BigDecimal amount){
        String sql =
                "UPDATE wallet SET balance = balance - ? WHERE user_id = ? AND balance >= ?";
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = getConnection("jdbc:mysql://localhost:3306/java_demo?useSSL=true&useUnicode=true&characterEncoding=UTF-8","root","83612457");
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setBigDecimal(1,amount);
                preparedStatement.setInt(2,userId);
                preparedStatement.setBigDecimal(3, BigDecimal.valueOf(100));
                int flag = preparedStatement.executeUpdate();
                //flag > 0说明sql语句执行成功，接着将交易数据插入wallet_transfer表中
                if (flag > 0){
                    sql = "INSERT INTO wallet_transfer (user_id, amount, type) VALUES (?, ?, 'C')";
                  try {
                      Class.forName("com.mysql.cj.jdbc.Driver");
                      //连接数据库
                       connection =
                              getConnection("jdbc:mysql://localhost:3306/java_demo?useSSL=true&useUnicode=true&characterEncoding=UTF-8","root","83612457");
                       preparedStatement = connection.prepareStatement(sql);

                       preparedStatement.setInt(1,userId);
                       preparedStatement.setBigDecimal(2,amount);
                       preparedStatement.executeUpdate();
                        return true;
                  }catch (SQLException e){
                      System.out.println("查询时发生错误: " + e.getMessage());
                  }
                }

            }
             catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                System.out.println("查询时发生错误: " + e.getMessage());
            }

        return false;
    }

    /**
     * 用户返还金额
     * @param userId
     * @param amount
     * @return
     */
    public static boolean refund(int userId, BigDecimal amount)  {
        String sql =
                "UPDATE wallet SET balance = balance + ? WHERE user_id = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = getConnection("jdbc:mysql://localhost:3306/java_demo?useSSL=true&useUnicode=true&characterEncoding=UTF-8","root","83612457");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBigDecimal(1,amount);
            preparedStatement.setInt(2,userId);

            int flag = preparedStatement.executeUpdate();
            if (flag > 0) {
                sql = "INSERT INTO wallet_transfer (user_id, amount, type) VALUES (?, ?, 'D')";
                try {

                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1,userId);
                    preparedStatement.setBigDecimal(2,amount);
                    preparedStatement.executeUpdate();
                    return  true;


                }catch (SQLException e) {
                    System.out.println("查询时发生错误: " + e.getMessage());
                }
            }

        }catch (SQLException e) {
            System.out.println("查询时发生错误: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        return false;
    }

    /**
     * 根据用户id查询出用户所有的交易记录，另外创建一个对应交易记录的Transaction类，将查询到的结果放入list集合中
     * @param userId
     * @return
     */
    public static List<Transaction> getAllTransactions(int userId) {
        String sql = "SELECT transfer_id, amount, type, transfer_date FROM wallet_transfer WHERE user_id = ?";
        List<Transaction> list = new ArrayList<Transaction>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = getConnection("jdbc:mysql://localhost:3306/java_demo?useSSL=true&useUnicode=true&characterEncoding=UTF-8", "root", "83612457");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            try(ResultSet rs = preparedStatement.executeQuery()){
                while (rs.next()){
                    Transaction transaction = new Transaction();
                    transaction.setId(rs.getInt("transfer_id"));
                    transaction.setAmount(rs.getBigDecimal("amount"));
                    transaction.setType(rs.getString("type"));
                    transaction.setCreatedAt(rs.getTimestamp("transfer_date"));

                    list.add(transaction);
                }
            }
        } catch (SQLException e) {
            System.out.println("查询时发生错误: " + e.getMessage());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return list;
    }




    public static void main(String[] args) {
        boolean consume = consume(1, BigDecimal.valueOf(100));
        System.out.println(consume);

        BigDecimal balance = getBalance(1);
        System.out.println(balance);

        boolean refund = refund(1,BigDecimal.valueOf(20));
        System.out.println(refund);

        List<Transaction> allTransactions = getAllTransactions(1);
        for (int i = 0; i < allTransactions.size(); i++) {
            System.out.println(allTransactions.get(i));
        }
    }
}
