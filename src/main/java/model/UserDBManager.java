package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDBManager {


    public void getDatabaseOID(){ //Our database folder number in path "C:\Program Files\PostgreSQL\12\data\base"

        int oid = 0;

        try {
            PreparedStatement ps = DBConnector.getConnection().prepareStatement(" SELECT oid from pg_database WHERE datname = 'userdb';");

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                oid = rs.getInt("oid");
            }

            rs.close();
            ps.close();
            System.out.println("Database oid: " + oid);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error!");
        }

    }

/////////////////////////////////////////////////////////////////////////////////////////////////////

    public void createDB(){

        boolean flag = false;

        try {
            PreparedStatement ps = DBConnector.getConnection().prepareStatement("CREATE DATABASE userdb  ");
            DBConnector.setDb_url("jdbc:postgresql://127.0.0.1:5432/userdb"); //To connect to specific db (here is userdb)
            DatabaseMetaData metadata = DBConnector.getConnection().getMetaData();
            ResultSet result = metadata.getCatalogs();

            while (result.next()) {
                String aDBName = result.getString(1);
                if(aDBName.equals("userdb")); //userdb already exists
                flag = true;
                break;
            }
            if (!flag) {
                ps.execute();
                System.out.println("Database was created!");
            }
            else
                System.out.println("Database already exists!");


        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error! Database wasn't created");
        }

    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void createTable(){

        try {
            PreparedStatement ps = DBConnector.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS usser" + "(" + " ID serial," + " firstName varchar(100) NOT NULL," + " lastName varchar(100) NOT NULL," + " userName varchar(100) NOT NULL UNIQUE," + " password varchar(100) NOT NULL," + " Gender VARCHAR(6) NOT NULL CHECK (Gender IN ('male', 'female'))," + " dateOfBirth Date NOT NULL," + " PRIMARY KEY (ID)" + ")");

            ps.execute();
            System.out.println("Table was created!");


        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error! Table wasn't created");
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void insert(User user){

        try {
            PreparedStatement ps = DBConnector.getConnection().prepareStatement("insert into usser (firstName, lastName, userName, password, gender, dateOfBirth) values(?,?,?,?,?,?)"); //paremeter binding to prevent from sql injection
            ps.setString(1 , user.getFirstName());
            ps.setString(2 , user.getLastName());
            ps.setString(3 , user.getUserName());
            ps.setString(4 , user.getPassword());
            ps.setString(5 , user.getGender());
            ps.setDate(6 , Date.valueOf(user.getDateOfBirth()));

            ps.execute();
            System.out.println("New user was inserted!");

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error! New user wasn't inserted");
        }
    }

///////////////////////////////////////////////////////////////////////////////////////
/*
    public void deleteById(int id){

        try {
            PreparedStatement ps = DBConnector.getConnection().prepareStatement("delete from usser where id=?"); //paremeter binding to prevent from sql injection
            ps.setInt(1,id);
            ps.execute();
            System.out.println("User was deleted!");

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error! User wasn't deleted");
        }
    }
*/
//////////////////////////////////////////////////////////////////////////////////////////////

    public void deleteByUserName(String name){

        try {
            PreparedStatement ps = DBConnector.getConnection().prepareStatement("delete from usser where userName=?"); //paremeter binding to prevent from sql injection
            ps.setString(1,name);
            ps.execute();
            System.out.println("User was deleted!");

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error! User wasn't deleted");
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////
/*
    public User getUserById(int id){

        User result = new User();

        try {
            PreparedStatement ps = DBConnector.getConnection().prepareStatement("select * from usser where id=?"); //paremeter binding to prevent from sql injection
            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){ //rows in db
                result.setId(rs.getInt("id"));
                result.setFirstName(rs.getString("firstName"));
                result.setLastName(rs.getString("lastName"));
                result.setUserName(rs.getString("userName"));
                result.setPassword(rs.getString("password"));
                result.setGender(rs.getString("gender"));
                result.setDateOfBirth((rs.getDate("dateOfBirth")).toLocalDate());
            }

            rs.close();
            ps.close();
            //ps.execute();
            System.out.println("Read from db!");

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error!");
        }
        return result;
    }
*/
//////////////////////////////////////////////////////////////////////////////////////////////

    public boolean existUserByUserName(String name){//To check userName uniqueness during registration

        boolean flag = false;

        try {
            PreparedStatement ps = DBConnector.getConnection().prepareStatement("select * from usser where userName=?"); //paremeter binding to prevent from sql injection
            ps.setString(1,name);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getInt("id") > 0)
                    flag = true; //user already exists
                    break;
            }


            rs.close();
            ps.close();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error!");
        }
        if(flag)
            return true;
        else
            return false;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public List<User> getAllUsers(){

        List<User> users = new ArrayList<>();

        try {
            PreparedStatement ps = DBConnector.getConnection().prepareStatement("select * from usser"); //paremeter binding to prevent from sql injection
            ResultSet rs = ps.executeQuery();

            while (rs.next()){ //rows in db

                User result = new User();

                result.setId(rs.getInt("id"));
                result.setFirstName(rs.getString("firstName"));
                result.setLastName(rs.getString("lastName"));
                result.setUserName(rs.getString("userName"));
                result.setPassword(rs.getString("password"));
                result.setGender(rs.getString("gender"));
                result.setDateOfBirth((rs.getDate("dateOfBirth")).toLocalDate());

                users.add(result);
            }

            rs.close();
            ps.close();
            //ps.execute();
            System.out.println("read books from db!");

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("error!");
        }
        return users;
    }

///////////////////////////////////////////////////////////////////////////////////

    public void update(User newUser){

        try {
            PreparedStatement ps = DBConnector.getConnection().prepareStatement("update usser set firstName=?, lastName=?, userName=?, password=?, gender=?, dateOfBirth=? where id=?"); //paremeter binding to prevent from sql injection
            ps.setString(1 , newUser.getFirstName());
            ps.setString(2 , newUser.getLastName());
            ps.setString(3 , newUser.getUserName());
            ps.setString(4,newUser.getPassword());
            ps.setString(5 ,newUser.getGender());
            ps.setDate(6 , Date.valueOf(newUser.getDateOfBirth()));
            ps.setInt(7 , newUser.getId());

            ps.execute();
            System.out.println("Updated!");

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error!");
        }
    }
}
