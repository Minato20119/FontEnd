package com.minato.springboot.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.minato.springboot.config.Configure;

import Decoder.BASE64Decoder;

@Controller
public class FileUpload extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUpload.class);

	@RequestMapping(value = "fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public static String getImage(HttpServletRequest request) throws IOException {

		String fileUpload1 = request.getParameter("minato1");
//		LOGGER.info("image: " + fileUpload1);
		LOGGER.info("Done image 0");

		if (fileUpload1 != null) {
			String imageString = fileUpload1.substring(fileUpload1.indexOf(",") + 1, fileUpload1.length());

			// Get Buffered Image
			BufferedImage image = bufferedImage(imageString);

			if (image != null) {

				String formats = fileUpload1.substring(fileUpload1.indexOf("/") + 1, fileUpload1.indexOf(";"));

				writeImage(image, ".", formats);
			}
		}

		String fileUpload2 = request.getParameter("minato2");
//		LOGGER.info("image1: " + fileUpload2);
		LOGGER.info("Done image 1");

		if (fileUpload2 != null) {
			String imageString = fileUpload2.substring(fileUpload2.indexOf(",") + 1, fileUpload2.length());

			// Get Buffered Image
			BufferedImage image = bufferedImage(imageString);

			if (image != null) {

				String formats = fileUpload2.substring(fileUpload2.indexOf("/") + 1, fileUpload2.indexOf(";"));

				writeImage(image, "1.", formats);
			}
		}

		String fileUpload3 = request.getParameter("minato3");
//		LOGGER.info("image2: " + fileUpload3);
		LOGGER.info("Done image 2");

		if (fileUpload3 != null) {
			String imageString = fileUpload3.substring(fileUpload3.indexOf(",") + 1, fileUpload3.length());

			// Get Buffered Image
			BufferedImage image = bufferedImage(imageString);

			if (image != null) {

				String formats = fileUpload3.substring(fileUpload3.indexOf("/") + 1, fileUpload3.indexOf(";"));

				writeImage(image, "2.", formats);
			}
		}

		return " upload image!";
	}

	// create a buffered image
	private static BufferedImage bufferedImage(String imageString) throws IOException {

		BufferedImage image = null;
		
		if (imageString != null) {

			byte[] imageByte;

			BASE64Decoder decoder = new BASE64Decoder();
			imageByte = decoder.decodeBuffer(imageString);
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);
			bis.close();
		}
		
		return image;
	}

	// write the image to a file
	private static void writeImage(BufferedImage image, String nameImage, String formatsImage) throws IOException {

		if (image != null) {

			File outputfile = new File(Configure.PATH + "image" + nameImage + formatsImage);
			ImageIO.write(image, formatsImage, outputfile);

		} else {

			LOGGER.error("Not found input file image!");
		}

	}
}
