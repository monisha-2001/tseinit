package com.example.Jpademo.com.zipformat;


import java.io.IOException;

import com.example.Jpademo.com.model.Detail;

import jakarta.servlet.http.HttpServletResponse;

public interface ProjectGenerationService {

    void generateProject(Detail details, HttpServletResponse response) throws IOException;
}
