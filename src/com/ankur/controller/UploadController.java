package com.ankur.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.ankur.Dao.ImageUploadDao;
import com.ankur.Model.FileUpload;

@Controller
public class UploadController implements HandlerExceptionResolver {

	@Autowired
	private ImageUploadDao imageUploadDao;

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView displayForm() {

		ModelAndView model = new ModelAndView("uploadImage");
		return model;
	}

	@RequestMapping(value = "/savefiles", method = RequestMethod.POST)
	public String saveFile(@ModelAttribute("uploadFile") FileUpload fileUpload, Model map)
			throws IllegalStateException, IOException {

		System.out.println("I am here in the code");
		List<MultipartFile> files = fileUpload.getFiles();

		System.out.println("Number o files received :" + files.size());

		List<String> fileNames = new ArrayList<String>();

		if (files != null && files.size() > 0) {
			for (MultipartFile multipartFile : files) {

				String fileName = multipartFile.getOriginalFilename();

				System.out.println("Name of file is :" + fileName);
				byte[] data = multipartFile.getBytes();

				FileUpload upload = new FileUpload();
				upload.setFileName(fileName);
				upload.setFileData(data);
				// if (!"".equalsIgnoreCase(fileName)) {
				// // Handle file content - multipartFile.getInputStream()
				// multipartFile.transferTo(new File(saveDirectory + fileName));
				// fileNames.add(fileName);
				// }

				System.out.println("Now images will be saved");
				imageUploadDao.save(upload);
				System.out.println("Image saved in the DB");
				fileNames.add(fileName);
			}
		}

		map.addAttribute("files", fileNames);
		return "uploadfilesuccess";
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (exception instanceof MaxUploadSizeExceededException) {
			model.put("errors", exception.getMessage());
		} else {
			model.put("errors", "Unexpected error: " + exception.getMessage());
		}
		model.put("uploadFile", new FileUpload());
		return new ModelAndView("/uploadImage", model);
	}

}
