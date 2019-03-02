package com.javalec.ex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.javalec.ex.dto.BDto;

public class BDao {
	
	DataSource dataSource;
	
	public BDao() {
		try {
			
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/mysql");	// connection pool���� connection�� ������
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	} // BDao() END
	
	
	// �Խñ� �Է� ���� //
	public void write(String bName, String bTitle, String bContent) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			// �Խñ� ��ȣ�� ��� �� (bId)�� ��ȸ
			int bId = selectIdGroup("mvc_board");
			
			conn = dataSource.getConnection();
	
			String query = "INSERT INTO mvc_board(bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent) "
					+ "VALUES (?,?,?,?,0,0,0,0)";
			pstmt = conn.prepareStatement(query);		
			pstmt.setInt(1,bId);
			pstmt.setString(2,bName);
			pstmt.setString(3,bTitle);
			pstmt.setString(4,bContent);
			
			pstmt.executeUpdate();
			
			// �Խñ� ��ȣ�� ��� �� (bId)�� ������Ʈ
			updateIdGroup("mvc_board");
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}finally {
			
			try {
				if(pstmt != null) pstmt.close();
				if(conn !=null) conn.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	} // write() END
	
	
	
	public ArrayList<BDto> list(){
		
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = dataSource.getConnection();	

			String query = "SELECT * FROM mvc_board ORDER BY bGroup DESC, bStep ASC";
			pstmt = conn.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");
				
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				
				dtos.add(dto);
			}
		
		}catch(Exception e) {
			
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn !=null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();				
			}
		}
		return dtos;
		
	} // list() END
	
	
	
	
	public BDto contentView(String strID) {
		
		// ��ȸ���� ������Ű�� ����
		upHit(strID);
		
		BDto dto = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();

			String query = "SELECT * FROM mvc_board WHERE bId = ?";
			pstmt = conn.prepareStatement(query);			
			pstmt.setInt(1, Integer.parseInt(strID));
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");
				
				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn !=null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();				
			}
			
		}
		
		return dto;
		
	} // contentView() END
	
	
	
	
	
	public void modify(String bId, String bName, String bTitle, String bContent) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();

			String query = "UPDATE mvc_board SET bName = ?, bTitle=? , bContent=? WHERE bId = ? ";
			pstmt = conn.prepareStatement(query);		
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bId));
			
			pstmt.executeUpdate();
		
		}catch(Exception e) {
			
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn !=null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();				
			}
		}
	} // modify() END
	
	
	
	
	public void delete(String bId) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = dataSource.getConnection();

			String query = "DELETE FROM mvc_board WHERE bId = ? ";
			pstmt = conn.prepareStatement(query);			
			pstmt.setInt(1, Integer.parseInt(bId));

			pstmt.executeUpdate();
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn !=null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();				
			}
		}
		
	} // delete() END
	
	
	
	
	
	public BDto reply_view(String Id) {
		
		BDto dto = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = dataSource.getConnection();

			String query = "SELECT * FROM mvc_board WHERE bId = ?";
			pstmt = conn.prepareStatement(query);			
			pstmt.setInt(1, Integer.parseInt(Id));

			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");
				
				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);				
			}
		
		}catch(Exception e) {
			e.printStackTrace();	
			
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn !=null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();				
			}
		}
		return dto;
		
	}// reply_view() END
	
	
	
	// DB table�� �亯�� ����ϴ� method // 
	public void reply(String bName, String bTitle, String bContent, String bGroup, String bStep, String bIndent) {
		
		// �亯�� �����ϱ����� ��ġȮ��
		replyShape(bGroup, bStep);
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();

			// �亯���� ��� �� ��ȣ(bId) �� ��ȸ
			int bId = selectIdGroup("mvc_board");
			
			String query = "INSERT INTO mvc_board(bId, bName, bTitle, bContent, bGroup, bStep, bIndent) VALUES(?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(query);			
			pstmt.setInt(1, bId);				
			pstmt.setString(2, bName);
			pstmt.setString(3, bTitle);
			pstmt.setString(4, bContent);
			pstmt.setInt(5, Integer.parseInt(bGroup));
			pstmt.setInt(6, Integer.parseInt(bStep)+1);		// �亯�� ��� �Խñۺ��� step�� ����
			pstmt.setInt(7, Integer.parseInt(bIndent)+1);	// �亯�� ��� �Խñۺ��� indent�� ����
			
			pstmt.executeUpdate();
			
			// �亯���� ��� �� (bId) �� ������Ʈ
			updateIdGroup("mvc_board");
		
			
		}catch(Exception e) {
			e.printStackTrace();	
			
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn !=null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();				
			}
		}
		
	} // reply() END
	
	
	
	
	// id ��ȸ //
	// idGroup table���� id ����
	private int selectIdGroup(String tableName) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int result = 0;

		try {
			
			conn = dataSource.getConnection();

			String query = "SELECT bid FROM idGroup WHERE tableName = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, tableName);				

			rs = pstmt.executeQuery();

			if(rs.next()) {
				result = rs.getInt("bid");
			}
		
		}catch(Exception e) {
			e.printStackTrace();	
			
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn !=null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();				
			}
		}
		
		return result;
		
	}// selectId() END
	
	
	
	
	// record ������ ������Ʈ //
	// recordCount table���� record���� ����
	private void updateIdGroup(String tableName) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();

			String query = "UPDATE idGroup SET bid = bid + 1 WHERE tableName = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, tableName);
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();	
				
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn !=null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();				
			}
		}
	}//updateId() END
	
	

	
	// �亯�� �����ϱ����� ��ġ�� Ȯ���ϴ� method//
	private void replyShape(String strGroup, String strStep) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		
		try {
			
			conn = dataSource.getConnection();
			
			String query = "UPDATE mvc_board SET bStep = bStep + 1 WHERE bGroup=? AND bStep > ?";
			// ���� �׷��̰� ������ step���� ���� ū ��ü bStepĮ���� ���� 1�� ����
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(strGroup));
			pstmt.setInt(2, Integer.parseInt(strStep));			
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();	
			
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn !=null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();				
			}
		}
		
	}// replyShape() END
	
	
	
	
	// �Խñ� ��ȸ������ ���� //
	private void upHit(String bId) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			String query = "UPDATE mvc_board SET bHit = bHit + 1 WHERE bId = ?";
			pstmt = conn.prepareStatement(query);			
			pstmt.setInt(1, Integer.parseInt(bId));
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();	
			
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn !=null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();				
			}
		}
	}// upHit() END
	
	
} // BDao END
