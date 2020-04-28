package toeicApp;

import java.sql.*;
import java.util.ArrayList;

public class ConnectDataBase {

    private  Connection conn=null;
    public ConnectDataBase()
    {
        try{
            String query1="use TOEIC_WORD\n";
            String connectcode="jdbc:sqlite:"+"TOIEC_WORD.db";
            //  Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(connectcode);
            if(conn!=null)
            {
                System.out.println("Connected");
//                Statement statement=conn.createStatement();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void closeConnect()  {
        try {
            System.out.println("Connect Closed");
            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<ToeicWords> getWord(int topicnumber)  {
        ArrayList<ToeicWords> data = new ArrayList<>();
        String query="select * from toeicword join topic on toeicword.topicID=topic.topicID where toeicword.topicID="+topicnumber;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            while(resultSet.next())
            {
                String word=resultSet.getString(1);
                String pronounce = resultSet.getString(2);
                String type=resultSet.getString(3);
                String engdes=resultSet.getString(4);
                String viedes=resultSet.getString(5);
                String engex=resultSet.getString(6);
                String vieex=resultSet.getString(7);
                int topicID=resultSet.getInt(8);
                String topic=resultSet.getString(10)+" - "+resultSet.getString(11);
                ToeicWords myword=new ToeicWords(word,pronounce,type,engdes,viedes,engex,vieex,topicID,topic);
                data.add(myword);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return data;
    }
    public ArrayList<String> getVieDescription()
    {
        ArrayList<String> data=new ArrayList<>();
        String query="select viedescription from toeicword";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            while(resultSet.next())
            {
                String viedes=resultSet.getString(1);
                data.add(viedes);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<ToeicWords> searchWord(String word)
    {
        ArrayList<ToeicWords> data = new ArrayList<>();
        String query="select *from toeicword where word like '%"+word+"%';";
        try{
            Statement statement=conn.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            while(resultSet.next())
            {
                String sword=resultSet.getString(1);
                String pronounce = resultSet.getString(2);
                String type=resultSet.getString(3);
                String engdes=resultSet.getString(4);
                String viedes=resultSet.getString(5);
                String engex=resultSet.getString(6);
                String vieex=resultSet.getString(7);
                int topicID=resultSet.getInt(8);
                String topic=resultSet.getString(10)+" - "+resultSet.getString(11);
                ToeicWords myword=new ToeicWords(sword,pronounce,type,engdes,viedes,engex,vieex,topicID,topic);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }

    public boolean addUserWord(UserWords word,int userID)
    {
        String query="INSERT INTO userwords values ('"+word.getWord()+"','"+word.getDescription()+"','"+word.getExample()+"',"+userID+");";
        try{
            Statement statement=conn.createStatement();
            statement.executeUpdate(query);
            return true;
        }catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public int submitLogin(String username,String password)
    {
        try{
            Statement statement=conn.createStatement();
            ResultSet resultSet=statement.executeQuery("select ID from users where userID like '"+username+"' and password='"+password+"'");
            if(resultSet.next())
            {
                return resultSet.getInt(1);
            }
            else return -1;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean newuser(String username,String password)
    {
        try{
            Statement statement=conn.createStatement();
           boolean s=statement.execute("insert into users(userID,password) VALUES ('"+username+"','"+password+"');");
           return s;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<UserWords> getUserWord(int userID)
    {
        ArrayList<UserWords> data=new ArrayList<>();
        String query="Select *from userwords where ID="+userID;
        try{
            Statement statement=conn.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            while(resultSet.next())
            {
                String word=resultSet.getString(1);
                String des=resultSet.getString(2);
                String exa=resultSet.getString(3);
                data.add(new UserWords(word,des,exa));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return data;
    }
}