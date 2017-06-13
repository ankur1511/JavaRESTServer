package com.ankur.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UploadController implements HandlerExceptionResolver {

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView crunchifyDisplayForm() {

		ModelAndView model = new ModelAndView("uploadImage");
		return model;
	}

	@RequestMapping(value = "/savefiles", method = RequestMethod.POST)
	public String crunchifySave(@ModelAttribute("uploadForm") FileUpload uploadForm, Model map)
			throws IllegalStateException, IOException {

		String saveDirectory = "c:/Spring/";

		List<MultipartFile> files = uploadForm.getFiles();

		List<String> fileNames = new ArrayList<String>();

		if (null != files && files.size() > 0) {
			for (MultipartFile multipartFile : files) {

				String fileName = multipartFile.getOriginalFilename();
				if (!"".equalsIgnoreCase(fileName)) {
					// Handle file content - multipartFile.getInputStream()
					multipartFile.transferTo(new File(saveDirectory + fileName));
					fileNames.add(fileName);
				}
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
