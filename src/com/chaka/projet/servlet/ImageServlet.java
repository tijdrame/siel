package com.chaka.projet.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.chaka.projet.service.utils.ServiceUtils;

public class ImageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5705303558931765445L;

	private ServletOutputStream pw = null;
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	
		String nomImage = request.getParameter("name");
		
		if (StringUtils.isBlank(nomImage))
			return;
		
		String fileName = ServiceUtils.chargerParametre("uploadImage")+nomImage;
		
		pw = response.getOutputStream();
		File file = new File(fileName);
		
		if (StringUtils.isNotBlank(getServletContext().getMimeType(fileName)))
		{
			response.setContentType(getServletContext().getMimeType(fileName));
		}
		
		response.setContentLength((int)file.length());
		
		FileInputStream fis = new FileInputStream(file);
		int count;
		
		while ((count = fis.read()) != -1)
		{
			pw.write(count);
		}
		
		fis.close();
		

		pw.close();
		pw = null;
		response.flushBuffer();
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}

}
