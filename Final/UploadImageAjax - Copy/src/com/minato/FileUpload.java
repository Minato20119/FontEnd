package com.minato;

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

@WebServlet("/fileUpload")
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FileUpload() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Done");
		String fileUpload = request.getParameter("minato");
		System.out.println("file: " + fileUpload);
		
		// tokenize the data
		String imageString = fileUpload.substring(fileUpload.indexOf(",") + 1, fileUpload.length());

		// create a buffered image
		BufferedImage image = null;
		byte[] imageByte;

		BASE64Decoder decoder = new BASE64Decoder();
		imageByte = decoder.decodeBuffer(imageString);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		image = ImageIO.read(bis);
		bis.close();

		// write the image to a file
		
		String filePath = "C:\\Users\\Minato\\eclipse-workspace\\UploadImageAjax\\WebContent\\WEB-INF\\upload\\";
		
		File outputfile = new File(filePath + "image.jpg");
		ImageIO.write(image, "jpg", outputfile);
		
	}

}
