/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package JDBC_Assignment;
import java.sql.Connection;
import java.sql.*;

public class Library {
    public boolean someLibraryMethod() {
        System.out.println("hello world");
    	return true;
    }
    public static void main(String[] args) {
    	Library libObj = new Library();
    	boolean result = libObj.someLibraryMethod();
	}
}
