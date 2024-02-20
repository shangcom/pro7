package Practice;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class MemberDAO {

	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;

	public MemberDAO() {
		System.out.println("MemberDAO 객체 생성");
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			System.out.println("MemberDAO 객체에서 DB연결 관련 에러");
		}
	}
	
	public List<MemberVO> listMembers() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		try {

			
			con=dataFactory.getConnection();

			String selectQuery = "select * from T_MEMBER";
			System.out.println("실행한 sql: " + selectQuery);

			pstmt = con.prepareStatement(selectQuery);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate");

				MemberVO vo = new MemberVO();
				vo.setId(id);
				vo.setPwd(pwd);
				vo.setName(name);
				vo.setEmail(email);
				vo.setJoinDate(joinDate);

				list.add(vo);

			}
			rs.close();
			pstmt.close();
			con.close();

		} catch (Exception e) {
			System.out.println("연결 에러");
		}
		return list;
	}

	
	public void addMember(MemberVO memberVO) {
		try {
			con=dataFactory.getConnection();
			String id = memberVO.getId();
			String pwd = memberVO.getPwd();
			String name = memberVO.getName();
			String email = memberVO.getEmail();
			
			String addQuery = "insert into t_member(id, pwd, name, email) values(?,?,?,?)";
			System.out.println("회원 추가 sql문: " + addQuery);
			pstmt = con.prepareStatement(addQuery);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, email);

			pstmt.executeUpdate();
			pstmt.close();
			
		} catch (Exception e) {
			System.out.println("회원 추가시 에러");
		}
	}
	
	public void delMember(String id) {
		try {
			con = dataFactory.getConnection();
			
			String delQuery = "delete from t_member" + " where id=?";
			System.out.println("회원 삭제 sql문: " + delQuery);
			pstmt = con.prepareStatement(delQuery);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			System.out.println("회원 삭제시 에러");
		}
	}

}
