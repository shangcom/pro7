package Practice;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		System.out.println("MemberServlet 초기화됨");
	}

	public void destroy() {
		System.out.println("MemberServlet 소멸됨");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();

		String command = request.getParameter("command");
		System.out.println("커맨드: " + command);

		MemberDAO dao = new MemberDAO();
		if (command != null && command.equals("addMember")) {
			String _id = request.getParameter("id");
			String _pwd = request.getParameter("pwd");
			String _name = request.getParameter("name");
			String _email = request.getParameter("email");

			System.out.println("가입정보" + _id + _pwd + _name + _email);
			MemberVO vo = new MemberVO();
			vo.setId(_id);
			vo.setPwd(_pwd);
			vo.setName(_name);
			vo.setEmail(_email);

			dao.addMember(vo);
		} else if (command != null && command.equals("delMember")) {
			String id = request.getParameter("id");
			dao.delMember(id);
		}
		
		List<MemberVO> list = dao.listMembers();
		System.out.println(list);

		pw.write("<!doctype html>\r\n" + "<html>\r\n" + "\r\n" + "<head>\r\n" + "<meta charset=\"utf-8\">\r\n"
				+ "<title></title>\r\n" + "<style>\r\n" + "table, tr, th, td {\r\n" + "border:solid 2px black;\r\n"
				+ "border-collapse: collapse;\r\n" + "padding: 8px;\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n"
				+ "\r\n" + "<body>\r\n" + "\r\n" + "<table>\r\n" + "\r\n"
				+ "<tr><td>아이디</td><td>비밀번호</td><td>이름</td><td>이메일</td><td>가입일</td><td>삭제</td></tr>\r\n");

		for (int i = 0; i < list.size(); i++) {
			String id = list.get(i).getId();
			String pwd = list.get(i).getPwd();
			String name = list.get(i).getName();
			String email = list.get(i).getEmail();
			Date joinDate = list.get(i).getJoinDate();
			pw.write("<tr><td>" + id + "</td><td>"
			+ pwd + "</td><td>"
			+ name + "</td><td>"
			+ email + "</td><td>"
			+ joinDate + "</td><td>"
			+ "<a href='/pro7/member?command=delMember&id=" + id + "'>삭제</a></tr>\r\n");
		}
		pw.write("</table>\r\n" + "</body>\r\n" + "</html>");
		pw.print("<a href='/pro7/memberForm.html'>새 회원 등록하기</a>");

	}

}
