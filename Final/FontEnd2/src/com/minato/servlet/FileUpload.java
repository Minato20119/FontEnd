package com.minato.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;

/**
 * Servlet implementation class FileUpload
 */
@WebServlet("/fileUpload")
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUpload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Done");
		String fileUpload = request.getParameter("minato");
		System.out.println("file: " + fileUpload);
		
		String base64Image = "";
		BufferedImage image = null;

		if (fileUpload != null) {
			base64Image = fileUpload.substring(fileUpload.indexOf(",") + 1, fileUpload.length());

			// create a buffered image
			byte[] imageByte;

			BASE64Decoder decoder = new BASE64Decoder();
			imageByte = decoder.decodeBuffer(base64Image);
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);
			bis.close();
		}

		// write the image to a file
		
		String filePath = "C:\\Users\\Minato\\eclipse-workspace\\FontEnd2\\WebContent\\WEB-INF\\upload\\";
		
		File outputfile = new File(filePath + "image.jpg");
		ImageIO.write(image, "jpg", outputfile);
		
	}

}
