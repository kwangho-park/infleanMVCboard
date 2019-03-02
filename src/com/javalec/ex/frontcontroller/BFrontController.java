package com.javalec.ex.frontcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javalec.ex.command.BCommand;
import com.javalec.ex.command.BContentCommand;
import com.javalec.ex.command.BDeleteCommand;
import com.javalec.ex.command.BListCommand;
import com.javalec.ex.command.BModifyCommand;
import com.javalec.ex.command.BReplyCommand;
import com.javalec.ex.command.BReplyViewCommand;
import com.javalec.ex.command.BWriteCommand;

/**
 * Servlet implementation class BFrontController
 */

// [문의] 확장자가 do 인 이유는???
// 보안의 이유로 추정됨
@WebServlet("*.do")
public class BFrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BFrontController() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		System.out.println("execute init");
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("execute doGet");
		actionDo(request, response);
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("execute doPost");
		actionDo(request, response);
	}


	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("execute actionDo");
		
		// ???
		String viewPage = null;		// view page 경로를 저장 (page forward)
		BCommand command = null;	// model instance 저장하는 참조변수
				
		
		request.setCharacterEncoding("UTF-8");
		
		// 분배를위한 파일경로 찾기 //
		
		String uri = request.getRequestURI();				// context path + 요청 파일경로 반환 
		String conPath = request.getContextPath();			// context path 반환
	
		String com = uri.substring(conPath.length());		// 요청 파일 path 반환
		
		
		
		// 사용자가 입력한 URL의 경로를 분배하고 실행하는 로직 // 	
		if(com.equals("/write_view.do")) {
			viewPage = "write_view.jsp";
			
		}else if(com.equals("/write.do")) {
			command = new BWriteCommand();
			command.execute(request, response);		// model instance 실행
			viewPage = "list.jsp";
			
		}else if(com.equals("/list.do")) {
			command = new BListCommand();
			command.execute(request, response);
			viewPage = "list.jsp";
			
		}else if(com.equals("/content_view.do")) {
			command = new BContentCommand();
			command.execute(request, response);
			viewPage = "content_view.jsp";

		}else if(com.equals("/modify.do")) {
			command = new BModifyCommand();
			command.execute(request, response);
			viewPage = "list.do";
			
		}else if(com.equals("/delete.do")) {
			command = new BDeleteCommand();
			command.execute(request, response);
			viewPage ="list.do";
			
		}else if(com.equals("/reply_view.do")) {
			command = new BReplyViewCommand();
			command.execute(request, response);
			viewPage = "reply_view.jsp";
			
		}else if(com.equals("/reply.do")) {
			command = new BReplyCommand();
			command.execute(request, response);
			viewPage = "list.do";
			
		}
		
		// forwarding // 
		// 분배된 결과 페이지를 출력하기위한 forwarding
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
		
	} // actionDo() END
	
	
	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		System.out.println("execute destroy");
	}
	
	
	
} // BFrontController END
