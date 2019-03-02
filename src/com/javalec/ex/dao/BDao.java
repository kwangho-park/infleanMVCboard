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
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/mysql");	// connection pool에서 connection을 뺴오기
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	} // BDao() END
	
	
	// 게시글 입력 로직 //
	public void write(String bName, String bTitle, String bContent) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			// 게시글 번호로 사용 할 (bId)를 조회
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
			
			// 게시글 번호로 사용 할 (bId)를 업데이트
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
		
		// 조회수를 증가시키는 로직
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
	
	
	
	// DB table에 답변을 등록하는 method // 
	public void reply(String bName, String bTitle, String bContent, String bGroup, String bStep, String bIndent) {
		
		// 답변을 생성하기위한 위치확보
		replyShape(bGroup, bStep);
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();

			// 답변으로 사용 할 번호(bId) 를 조회
			int bId = selectIdGroup("mvc_board");
			
			String query = "INSERT INTO mvc_board(bId, bName, bTitle, bContent, bGroup, bStep, bIndent) VALUES(?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(query);			
			pstmt.setInt(1, bId);				
			pstmt.setString(2, bName);
			pstmt.setString(3, bTitle);
			pstmt.setString(4, bContent);
			pstmt.setInt(5, Integer.parseInt(bGroup));
			pstmt.setInt(6, Integer.parseInt(bStep)+1);		// 답변의 대상 게시글보다 step을 내림
			pstmt.setInt(7, Integer.parseInt(bIndent)+1);	// 답변의 대상 게시글보다 indent를 넣음
			
			pstmt.executeUpdate();
			
			// 답변으로 사용 할 (bId) 를 업데이트
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
	
	
	
	
	// id 조회 //
	// idGroup table에서 id 관리
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
	
	
	
	
	// record 갯수를 업데이트 //
	// recordCount table에서 record갯수 관리
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
	
	

	
	// 답변을 생성하기위한 위치를 확보하는 method//
	private void replyShape(String strGroup, String strStep) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		
		try {
			
			conn = dataSource.getConnection();
			
			String query = "UPDATE mvc_board SET bStep = bStep + 1 WHERE bGroup=? AND bStep > ?";
			// 같은 그룹이고 현재의 step보다 값이 큰 전체 bStep칼럼의 값을 1씩 증가
			
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
	
	
	
	
	// 게시글 조회수증가 로직 //
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
