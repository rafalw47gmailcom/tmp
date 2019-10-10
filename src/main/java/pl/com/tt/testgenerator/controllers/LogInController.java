package pl.com.tt.testgenerator.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.com.tt.testgenerator.dto.UserFormDto;
import pl.com.tt.testgenerator.entieties.Users;
import pl.com.tt.testgenerator.repositories.UsersRepository;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class LogInController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/registration")
    public String registration(@RequestBody UserFormDto userForm) {
        Users user = new Users();
        BeanUtils.copyProperties(userForm, user);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        usersRepository.save(user);

        // securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @GetMapping("/finduser/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) throws Exception {
        return ok(usersRepository.findById(id).orElseThrow(() -> new Exception("not found")));
    }
}
