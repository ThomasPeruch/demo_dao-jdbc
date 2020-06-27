package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
	
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn=conn;
	}
	
	@Override
	public void insert(Department obj) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(
				"INSERT INTO department" 
				+ "(Name) "
				+ "VALUES "
				+ "(?)",
				Statement.RETURN_GENERATED_KEYS
				);
				
			pst.setString(1, obj.getName());
			
			int rowsAffected = pst.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = pst.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
					DB.closeResultSet(rs);
				}
				else {
					throw new DbException("Unexpected error! No rows affected!");
				}
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(pst);
		}
		
	}

	@Override
	public void update(Department obj) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement( "UPDATE department SET Name = ? WHERE Id = ? ");
				
			pst.setString(1, obj.getName());
			pst.setInt(2, obj.getId());
			
			pst.executeUpdate();	
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(pst);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
				
			pst.setInt(1, id);
			
			int rows = pst.executeUpdate();	
			
			if(rows == 0) {
				throw new DbException("Nonexistent Id");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(pst);
		}	
		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement("SELECT  * FROM department WHERE Id = ? ");
			
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if(rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("Id"));
				dep.setName(rs.getString("Name"));
				return dep;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}


	@Override
	public List<Department> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement( "SELECT * FROM department ORDER BY Name");
			
			rs = pst.executeQuery();
			
			List<Department> list = new ArrayList<>();
			
			while(rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("Id"));	
				dep.setName(rs.getString("Name"));	
			
				list.add(dep);
			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
			
		}
	}

}
