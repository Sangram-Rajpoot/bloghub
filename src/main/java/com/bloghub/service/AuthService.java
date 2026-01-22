package com.bloghub.service;

import com.bloghub.dto.AuthResponseDto;
import com.bloghub.dto.RegisterRequestDto;
import com.bloghub.entity.Author;
import com.bloghub.exception.ResourceAlreadyExitsException;
import com.bloghub.exception.ResourceNotFoundException;
import com.bloghub.repository.AuthorRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//indicates that this class is a service component in the Spring framework and will contain business logic related to authentication.
public class AuthService {

    AuthorRepository authRepo;//This is a repository interface that provides CRUD operations for Author entities.

    @Autowired
//This annotation is used for automatic dependency injection. It tells Spring to inject the AuthorRepository bean into this class.
    public AuthService(AuthorRepository authRepo) {
        this.authRepo = authRepo;
    }

    @Transactional
//This annotation indicates that the method should be executed within a transaction context.so that if any part of the method fails, all changes made during the method execution can be rolled back to maintain data integrity.
    public AuthResponseDto register(RegisterRequestDto request) {
        //Step1: Check if email already Taken
        if (authRepo.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExitsException("Email already registered");
        }
        //Step2: create new author entity and set its properties
        Author author = new Author();
        author.setName(request.getName());
        author.setEmail(request.getEmail());
        author.setPassword(request.getPassword());
        author.setAbout(request.getAbout());

        //Step3: save author to database
        Author savedAuthor = authRepo.save(author);

        //Step4: return back to response
        return new AuthResponseDto(//Creating and returning a new AuthResponseDto object with the saved author's details and a success message.
                savedAuthor.getId(),
                savedAuthor.getName(),
                savedAuthor.getEmail(),
                savedAuthor.getRole(),
                "Registered successfully"
        );

    }

    public AuthResponseDto login(RegisterRequestDto request, HttpSession session) {
        Author author = authRepo.findByEmail(request.getEmail())//This line attempts to find an Author entity in the database that matches the provided email address.
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));//If no matching author is found, it throws a ResourceAlreadyExitsException with the message "Invalid email or password".


        if (!author.getPassword().equals(request.getPassword())) {//This line checks if the password of the found author matches the provided password.
            throw new ResourceNotFoundException("Invalid email or password");
        }

        session.setAttribute("user", author.getId());//Storing the author's ID in the HTTP session to keep track of the logged-in user.
        session.setAttribute("userRole", author.getRole());//Storing the author's role in the HTTP session.
        session.setAttribute("userName", author.getName());//Storing the author's name in the HTTP session.
        session.setAttribute("userEmail", author.getEmail());//Storing the author's email in the HTTP session.


        return new AuthResponseDto(
                author.getId(),
                author.getName(),
                author.getEmail(),
                author.getRole(),
                "Login Successful"
        );
    }
}
