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
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String fileUpload1 = request.getParameter("minato1");
		System.out.println("minato1: " + fileUpload1);

		if (fileUpload1 != null) {
			String imageString = fileUpload1.substring(fileUpload1.indexOf(",") + 1, fileUpload1.length());

			// Get Buffered Image
			BufferedImage image = bufferedImage(imageString);
			writeImage(image, "1");
		}

		String fileUpload2 = request.getParameter("minato2");
		System.out.println("minato2: " + fileUpload2);

		if (fileUpload2 != null) {
			String imageString = fileUpload2.substring(fileUpload2.indexOf(",") + 1, fileUpload2.length());
			// Get Buffered Image
			BufferedImage image = bufferedImage(imageString);
			writeImage(image, "2");
		}

		String fileUpload3 = request.getParameter("minato3");
		System.out.println("minato3: " + fileUpload3);

		if (fileUpload3 != null) {
			String imageString = fileUpload3.substring(fileUpload3.indexOf(",") + 1, fileUpload3.length());
			// Get Buffered Image
			BufferedImage image = bufferedImage(imageString);
			writeImage(image, "3");
		}
	}

	// create a buffered image
	private static BufferedImage bufferedImage(String imageString) throws IOException {

		BufferedImage image;

		byte[] imageByte;

		BASE64Decoder decoder = new BASE64Decoder();
		imageByte = decoder.decodeBuffer(imageString);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		image = ImageIO.read(bis);
		bis.close();

		return image;
	}

	// write the image to a file
	private static void writeImage(BufferedImage image, String nameImage) throws IOException {
		String filePath = "C:\\Users\\Minato\\eclipse-workspace\\UploadImageAjax\\WebContent\\WEB-INF\\upload\\";

		File outputfile = new File(filePath + "image" + nameImage + ".jpg");
		ImageIO.write(image, "jpg", outputfile);
	}

}
